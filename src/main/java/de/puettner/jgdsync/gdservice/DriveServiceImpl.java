package de.puettner.jgdsync.gdservice;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceImpl extends DriveServiceBase implements DriveService {

    private final boolean logReponses;

    public DriveServiceImpl(Drive drive, boolean logReponses) {
        super(drive);
        this.logReponses = logReponses;
    }

    @Override
    public FileList list() {
        FileList result;
        try {
            Drive.Files.List list = drive.files().list();
            result = list.execute();
            if (logReponses) {
                File file = newFile(new Object() {}.getClass().getEnclosingMethod().getName());
                cacheReponse(result, file);
            }
            return result;
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
