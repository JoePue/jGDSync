package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.command.Command;
import de.puettner.jgdsync.gdservice.command.CommandExecutor;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.Arrays;
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
        if (System.getProperty("spring.profiles.active").contains("DEV")) {
            List<String> argList = new ArrayList(Arrays.asList(args));
            argList.add(Command.TESTDEBUGLOGS);
            argList.add(Command.LS);
            args = argList.toArray(new String[argList.size()]);
        }
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.init();
        commandExecutor.processCmdOptions(args);
    }
}
