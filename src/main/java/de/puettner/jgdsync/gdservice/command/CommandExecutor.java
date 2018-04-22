package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.SyncService;
import de.puettner.jgdsync.gdservice.SyncServiceBuilder;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 */
@Log
public class CommandExecutor {

    private final InitCheckCommand initCheckCommand = new InitCheckCommand();
    private final TestDebugLogsCommand debugLogsCommand = new TestDebugLogsCommand();
    private final ConsolePrinter consolePrinter;
    private SyncService service;
    private AppConfig appConfig;
    private boolean isAppConfigurationCorrect = false;

    public CommandExecutor() {
        log.fine("test");
        log.severe("severe");
        consolePrinter = new ConsolePrinter();
    }

    public void init() {
        appConfig = AppConfigBuilder.build();
        if (!AppConfigBuilder.validate(appConfig)) {
            out("App configuration is invalid");
        }
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
                }
            }
            if (Command.TESTDEBUGLOGS.equalsIgnoreCase(cmd)) {
                debugLogsCommand.execute(commandArguments);
            }
            if (isAppConfigurationCorrect) {
                initializedService();
                if (Command.LS.equalsIgnoreCase(cmd)) {
                    new LsCommand(consolePrinter, service).execute(commandArguments);
                }
                if (Command.DIFF.equalsIgnoreCase(cmd)) {
                    new DiffCommand(consolePrinter, service, appConfig).execute(commandArguments);
                }
                if (Command.CONFIGUPDATE.equalsIgnoreCase(cmd)) {
                    ConfigUpdateCommand configUpdateCommand = new ConfigUpdateCommand(service);
                    service.setAppConfig(configUpdateCommand.getAppConfig());
                    configUpdateCommand.execute(commandArguments);
                }
            }
            if (!cmdResult.isProcessed()) {
                out("Unknown command");
            }
        }
    }


    private SyncService initializedService() {
        if (this.service == null) {
            this.service = SyncServiceBuilder.build(appConfig);
        }
        return this.service;
    }
}
