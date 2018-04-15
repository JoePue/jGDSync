package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.slf4j.Slf4j;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 */
@Slf4j
public class CommandExecutor {

    private final DriveService service;
    private final GdLsCommand lsCommand;
    private final InitCheckCommand initCheckCommand = new InitCheckCommand();
    private final DebugLogsCommand debugLogsCommand = new DebugLogsCommand();
    private final ConfigUpdateCommand configUpdateCommand;
    private final ConsolePrinter consolePrinter;

    public CommandExecutor(DriveService service) {
        this.service = service;
        consolePrinter = new ConsolePrinter();
        lsCommand = new GdLsCommand(consolePrinter, service);
        configUpdateCommand = new ConfigUpdateCommand(service);
    }

    public void processCmdOptions(String[] args) {
        boolean commandIdentified;
        boolean isAppInitialized = initCheckCommand.execute();

        for (String cmd : args) {
            commandIdentified = false;
            log.info("processCmdOptions");
            if (Command.INITCHECK.equalsIgnoreCase(cmd)) {
                isAppInitialized = initCheckCommand.execute();
                commandIdentified = true;
            }
            if (isAppInitialized) {
                if (Command.LS.equalsIgnoreCase(cmd)) {
                    lsCommand.execute();
                    commandIdentified = true;
                }
                if (Command.TESTDEBUGLOGS.equalsIgnoreCase(cmd)) {
                    debugLogsCommand.execute();
                    commandIdentified = true;
                }
                if (Command.CONFIGUPDATE.equalsIgnoreCase(cmd)) {
                    configUpdateCommand.execute();
                    service.setAppConfig(configUpdateCommand.getAppConfig());
                    commandIdentified = true;
                }
                if (!commandIdentified) {
                    out("Unknown command");
                }
            }
        }
    }
}
