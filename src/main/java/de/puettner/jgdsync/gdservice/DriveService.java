package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;

public interface DriveService {

    FileList listAll();

    Node<SyncNode> listRootFolder();

    void setAppConfig(AppConfig appConfig);

    Node<SyncNode> findFolderByName(String foldername);

    Node<SyncNode> listFolderByNode(Node<SyncNode> node, boolean recursive);
}
