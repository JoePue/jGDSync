package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.DriveServiceBuilder;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.gdservice.command.AppConfigBuilder;
import de.puettner.jgdsync.gdservice.command.CommandExecutor;
import lombok.extern.slf4j.Slf4j;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java
 * C:\Users\joerg.puettner\.credentials
 *
 * JavaDoc Drive API v3
 *      https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/
 *
 * Google API Client Libraries, JSON:
 *      https://developers.google.com/api-client-library/java/google-http-java-client/json
 *      #JacksonFactory #GsonFactory
 *      #HTTP Unit Testing
 */
@Slf4j
public class Main {

    public static final boolean useMock = true;
    public static final boolean logResponses = true;

    public static void main(String[] args) {
        AppConfig appConfig = AppConfigBuilder.build();
        if (AppConfigBuilder.validate(appConfig)){
            out("App configuration is invalid");
        }
        DriveService gdService = DriveServiceBuilder.build(useMock, logResponses, appConfig);
        new CommandExecutor(gdService).processCmdOptions(args);
    }
}
