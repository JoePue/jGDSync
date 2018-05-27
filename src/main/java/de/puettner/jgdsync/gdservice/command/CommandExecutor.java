package de.puettner.jgdsync.gdservice.command;

import com.google.common.collect.Lists;
import de.puettner.jgdsync.gdservice.SyncService;
import de.puettner.jgdsync.gdservice.SyncServiceBuilder;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.error;
import static java.text.MessageFormat.format;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 */
@Log
public class CommandExecutor {

    private final ConsolePrinter consolePrinter;
    private final List<Command> commandListWithoutInitCheck = new ArrayList<>();
    private final List<Command> commandListWithInitCheck = new ArrayList<>();
    private InitCheckCommand initCheckCommand;
    private SyncService service;
    private AppConfig appConfig;
    private boolean isAppConfigurationCorrect = false;
    private HelpCommand helpCommand;

    public CommandExecutor() {
        consolePrinter = new ConsolePrinter();
    }

    public void init() {
        appConfig = AppConfigBuilder.build();
        if (!AppConfigBuilder.validate(appConfig)) {
            error("App configuration is invalid");
        }

        initCommandListWithInitCheck();
        initStandaloneCommands();
    }

    /**
     * Befehle die eine initialisierte Anwenundung benötigen
     */
    private void initCommandListWithInitCheck() {
        this.service = SyncServiceBuilder.build(appConfig);

        this.commandListWithInitCheck.add(new DiffCommand(consolePrinter, service, appConfig));
    }

    /**
     * Befehle die keine initialisierte Anwenundung benötigen.
     */
    private void initStandaloneCommands() {
        ArrayList<Command> commandList = Lists.newArrayList(this.commandListWithInitCheck);

        this.initCheckCommand = new InitCheckCommand(consolePrinter);
        this.helpCommand = new HelpCommand(consolePrinter, commandList);

        this.commandListWithoutInitCheck.add(new LsCommand(consolePrinter, service));
        this.commandListWithoutInitCheck.add(helpCommand);
        this.commandListWithoutInitCheck.add(new InitCommand(consolePrinter));
        this.commandListWithoutInitCheck.add(new InitCheckCommand(consolePrinter));
        this.commandListWithoutInitCheck.add(new TestDebugLogsCommand());
        this.commandListWithoutInitCheck.add(new DownloadCommand(consolePrinter, service));
        commandList.addAll(this.commandListWithoutInitCheck);
    }

    public void processCmdOptions(String[] args) {
        if (args.length < 1) {
            error(format("Missing command. Use {0}", helpCommand.getCommandName()));
            return;
        }
        final CommandArgs commandArguments = new CommandArgs(args);
        CommandResult cmdResult = null;

        for (Command command : commandListWithoutInitCheck) {
            if (command.isExecutable(commandArguments)) {
                command.execute(commandArguments);
            }
        }

        cmdResult = initCheckCommand.execute(null);
        if (cmdResult.isProcessed() && cmdResult.isSuccessful()) {
            for (Command command : commandListWithInitCheck) {
                if (command.isExecutable(commandArguments)) {
                    cmdResult = command.execute(commandArguments);
                }
            }
            if (cmdResult == null || !cmdResult.isProcessed()) {
                error("Unknown command ");
            }
        }
    }
}
