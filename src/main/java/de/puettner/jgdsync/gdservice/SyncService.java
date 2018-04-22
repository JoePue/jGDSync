package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;

public interface SyncService {

    FileList listAll();

    Node<SyncData> listRootFolder();

    void setAppConfig(AppConfig appConfig);

    Node<SyncData> findFolderByName(String foldername);

    Node<SyncData> findFolderById(String foldername);

    Node<SyncData> createRootSyncNode();

    Node<SyncData> listFolderByNode(Node<SyncData> node, boolean recursive);

    void syncNode(Node<SyncData> node);

    void syncNodeChildren(Node<SyncData> node);
}
