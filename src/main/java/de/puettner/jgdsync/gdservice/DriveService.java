package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;

public interface DriveService {

    FileList listAllFoldersAndFiles();

    FileList listRootFoldersAndFiles();

    FileList listFoldersAndFile(File q);

    void setAppConfig(AppConfig appConfig);
}
