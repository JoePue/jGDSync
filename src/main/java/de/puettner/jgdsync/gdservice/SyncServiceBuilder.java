package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import de.puettner.jgdsync.model.SyncNodeFactory;

import java.io.File;


public class SyncServiceBuilder {

    private SyncServiceBuilder() {
    }

    public static SyncService build(AppConfig appConfig) {

        FileIgnoreFilter fileIgnoreFilter = new FileIgnoreFilter(appConfig.getLclFolder(), appConfig.getLclIgnoreFolders());

        SyncData ROOT_SYNC_NODE = SyncNodeFactory.getInstance(appConfig.getLclFolder(), fileIgnoreFilter).construct(new File("."));
        final Node<SyncData> rootNode = new Node<>(ROOT_SYNC_NODE, true);

        LocalFileService lclFileSrv = new LocalFileService();

        DriveService driveService = new DriveService(DriveBuilder.build(), appConfig.isCacheResponses());
        return new SyncService(appConfig, rootNode, driveService, fileIgnoreFilter, lclFileSrv);
    }
}
