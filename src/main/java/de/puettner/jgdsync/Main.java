package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.DriveServiceBuilder;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.gdservice.command.AppConfigBuilder;
import de.puettner.jgdsync.gdservice.command.CommandExecutor;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 * <p>
 * JavaDoc Drive API v3 https://developers.google.com/resources/api-libraries/documentation/drive/v3/java/latest/
 * <p>
 * Google API Client Libraries, JSON: https://developers.google.com/api-client-library/java/google-http-java-client/json #JacksonFactory
 * #GsonFactory #HTTP Unit Testing
 */
@Slf4j
public class Main {

    public static final SyncNode ROOT_SYNC_NODE = new SyncNode(false, null, new File("."));
    public static final Node<SyncNode> ROOT_NODE = new Node<>(ROOT_SYNC_NODE, true);

    public static final boolean useMock = true;
    public static final boolean logResponses = true;

    public static void main(String[] args) {
        AppConfig appConfig = AppConfigBuilder.build();
        if (!AppConfigBuilder.validate(appConfig)) {
            out("App configuration is invalid");
        }
        DriveService gdService = DriveServiceBuilder.build(useMock, logResponses, appConfig, ROOT_NODE);
        new CommandExecutor(gdService).processCmdOptions(args);
    }
}
