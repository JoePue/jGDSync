package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;

public interface DriveService {

    FileList listAll();

    Node<SyncNode> listRootFolder();

    FileList listFolder(File q);

    void setAppConfig(AppConfig appConfig);

    FileList findFolderByName(String foldername);

    Node<SyncNode> listFolderByName(String firstArgument);
}
