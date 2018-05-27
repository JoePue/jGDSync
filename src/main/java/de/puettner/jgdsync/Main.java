package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.command.CommandExecutor;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.errorf;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 * <p>
 * JavaDoc Drive API v3 https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/
 * <p>
 * Google API Client Libraries, JSON: https://developers.google.com/api-client-library/java/google-http-java-client/json #JacksonFactory
 * #GsonFactory #HTTP Unit Testing
 */
@Log
public class Main {

    public static void main(String[] args) {
        log.info("main()");
        List<String[]> commandList = new ArrayList<>();
        if (System.getProperty("app.profiles.active").equals("DEV")) {
            //        commandList.add(new String[]{Command.LS, "-r", "test-sync-dir"});
            //        commandList.add(new String[]{Command.DIFF});
        }
        commandList.add(args);

        CommandExecutor commandExecutor = new CommandExecutor();
        for (String[] command : commandList) {
            commandExecutor.init();
            try {
                commandExecutor.processCmdOptions(command);
            } catch (Exception e) {
                //                if (AppEnvironment.isDevMode()) {
                //                    e.printStackTrace();
                //                }
                errorf("An error occurred at command execution. error: {0}", e.getMessage());
            }
        }
    }
}
