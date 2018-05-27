package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.SyncService;
import de.puettner.jgdsync.gdservice.SyncServiceBuilder;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

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

    public CommandExecutor() {
        consolePrinter = new ConsolePrinter();
    }

    public void init() {
        appConfig = AppConfigBuilder.build();
        if (!AppConfigBuilder.validate(appConfig)) {
            out("App configuration is invalid");
        }

        initCommandList();
        initCheck();
    }

    private void initCommandList() {
        this.initCheckCommand = new InitCheckCommand();
        this.service = SyncServiceBuilder.build(appConfig);
        this.commandList.add(new LsCommand(consolePrinter, service));
        this.commandList.add(new DiffCommand(consolePrinter, service, appConfig));
        this.commandList.add(new TestDebugLogsCommand());
        this.commandList.add(new ConfigUpdateCommand(service));

    }

    private void initCheck() {
        CommandResult cmdResult = initCheckCommand.execute(null);
        if (cmdResult.isProcessed() && cmdResult.isSuccessful()) {
            this.isAppConfigurationCorrect = true;
        }
    }

    public void processCmdOptions(String[] args) {
        final CommandArgs commandArguments = new CommandArgs(args);
        CommandResult cmdResult = initCheckCommand.execute(commandArguments);

        for (String cmd : args) {
            log.info("processCmdOptions");
            if (Command.INITCHECK.equalsIgnoreCase(cmd)) {
                cmdResult = initCheckCommand.execute(commandArguments);
                if (cmdResult.isProcessed() && cmdResult.isSuccessful()) {
                    isAppConfigurationCorrect = true;
                    cmdResult = null;
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
                out("Unknown command");
            }
        }
    }
}
