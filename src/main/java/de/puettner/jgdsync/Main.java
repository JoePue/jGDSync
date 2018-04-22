package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.command.Command;
import de.puettner.jgdsync.gdservice.command.CommandExecutor;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;

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
            commandList.add(new String[]{Command.LS, "test-sync-dir"});
        } else {
            commandList.add(args);
        }

        CommandExecutor commandExecutor = new CommandExecutor();
        for (String[] command : commandList) {
            commandExecutor.init();
            commandExecutor.processCmdOptions(command);
        }
    }
}
