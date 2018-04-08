package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceMock extends DriveServiceBase implements DriveService {

    protected DriveServiceMock(AppConfig appConfig) {
        super(null, appConfig);
    }

    @Override
    public FileList listFoldersAndFile(File q) {
        FileList result;
        if ((result = getFiles(0, q.getId())) != null) {
            return result;
        }
        return getCachedResponse2(0, q.getId());
    }

    @Override
    public FileList listAllFoldersAndFiles() {
        return getCachedResponse2(0, null);
    }

    @Override
    public FileList listRootFoldersAndFiles() {
        return getCachedResponse2(0, null);
    }

    private FileList getCachedResponse2(int callStackIdx, String hashCode) {
        FileList result;
        if ((result = getFiles(++callStackIdx, hashCode)) != null) {
            return result;
        }
        return null;
    }
}
