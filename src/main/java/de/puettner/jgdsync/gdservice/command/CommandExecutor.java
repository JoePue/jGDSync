package de.puettner.jgdsync.gdservice.command;

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
    private final List<Command> commandList = new ArrayList<>();
    private InitCheckCommand initCheckCommand;
    private TestDebugLogsCommand debugLogsCommand;
    private SyncService service;
    private AppConfig appConfig;
    private boolean isAppConfigurationCorrect = false;
    private ConfigUpdateCommand configUpdateCommand;
    private HelpCommand helpCommand;

    public CommandExecutor() {
        consolePrinter = new ConsolePrinter();
    }

    public void init() {
        appConfig = AppConfigBuilder.build();
        if (!AppConfigBuilder.validate(appConfig)) {
            error("App configuration is invalid");
        }

        initCommandList();
        initCheck();
    }

    private void initCommandList() {
        this.initCheckCommand = new InitCheckCommand(consolePrinter);
        this.service = SyncServiceBuilder.build(appConfig);

        this.helpCommand = new HelpCommand(consolePrinter, this.commandList);
        this.commandList.add(new LsCommand(consolePrinter, service));
        this.commandList.add(new DiffCommand(consolePrinter, service, appConfig));
        this.commandList.add(new TestDebugLogsCommand());
        this.commandList.add(new ConfigUpdateCommand(service));
        this.commandList.add(helpCommand);
    }

    private void initCheck() {
        CommandResult cmdResult = initCheckCommand.execute(null);
        if (cmdResult.isProcessed() && cmdResult.isSuccessful()) {
            this.isAppConfigurationCorrect = true;
        }
    }

    public void processCmdOptions(String[] args) {
        if (args.length < 1) {
            error(format("Missing command. Use {0}", helpCommand.getCommandName()));
            return;
        }
        final CommandArgs commandArguments = new CommandArgs(args);
        CommandResult cmdResult = initCheckCommand.execute(commandArguments);
        if (cmdResult.isSuccessful()) {
            cmdResult = null;
            for (String cmd : args) {
                log.info("processCmdOptions");
                if (Command.INITCHECK.equalsIgnoreCase(cmd)) {
                    cmdResult = initCheckCommand.execute(commandArguments);
                    if (cmdResult.isProcessed() && cmdResult.isSuccessful()) {
                        isAppConfigurationCorrect = true;
                        cmdResult = null;
                    }
                }
            }
            if (isAppConfigurationCorrect) {
                for (Command command : commandList) {
                    if (command.isExecutable(commandArguments)) {
                        cmdResult = command.execute(commandArguments);
                    }
                }
            }
            if (cmdResult == null || !cmdResult.isProcessed()) {
                error("Unknown command ");
            }
        } else {
            error("Init check failed.");
        }
    }
}
