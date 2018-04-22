package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;


public class DriveServiceBuilder {

    private DriveServiceBuilder() {
    }

    public static DriveService build(boolean useMock, boolean logResponses, AppConfig appConfig, Node<SyncNode> rootNode) {
        return new DriveServiceImpl(DriveBuilder.build(), logResponses, appConfig, rootNode);
    }
}
