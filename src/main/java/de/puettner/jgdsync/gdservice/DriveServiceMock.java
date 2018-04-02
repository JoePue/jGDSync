package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceMock extends DriveServiceBase implements DriveService {

    protected DriveServiceMock() {
        super(null);
    }

    @Override
    public FileList list(File q) {
        FileList result;
        if ((result = loadCachedResponse(0, q.getId())) != null) {
            return result;
        }
        return null;
    }

    @Override
    public FileList listRootFiles() {
        FileList result;
        if ((result = loadCachedResponse(0, null)) != null) {
            return result;
        }
        return null;
    }
}
