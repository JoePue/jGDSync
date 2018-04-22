package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.DriveServiceBuilder;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;
import lombok.extern.java.Log;

import java.io.File;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 */
@Log
public class CommandExecutor {

    private static final boolean cacheResponses = true;
    private static final SyncNode ROOT_SYNC_NODE = new SyncNode(false, null, new File("."));
    private static final Node<SyncNode> ROOT_NODE = new Node<>(ROOT_SYNC_NODE, true);
    private static final boolean useCache = true;
    private final InitCheckCommand initCheckCommand = new InitCheckCommand();
    private final TestDebugLogsCommand debugLogsCommand = new TestDebugLogsCommand();
    private final ConsolePrinter consolePrinter;
    private DriveService service;
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
                    new GdLsCommand(consolePrinter, service).execute(commandArguments);
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


    private DriveService initializedService() {
        if (this.service == null) {
            this.service = DriveServiceBuilder.build(useCache, cacheResponses, appConfig, ROOT_NODE);
        }
        return this.service;
    }
}
