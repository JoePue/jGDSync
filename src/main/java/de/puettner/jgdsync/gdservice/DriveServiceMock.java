package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceMock extends DriveServiceBase implements DriveService {

    protected DriveServiceMock() {
        super(null);
    }

    @Override
    public FileList list() {
        File file = newFile(new Object(){}.getClass().getEnclosingMethod().getName());
        FileList result;
        if ((result = loadCachedResponse(file)) != null) {
            return result;
        }
        return null;
    }
}
