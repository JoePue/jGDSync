package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;

import static de.puettner.jgdsync.DriveFileUtil.FOLDER_MIME_TYPE;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceImpl extends DriveServiceBase implements DriveService {

    private final boolean logResponses;

    public DriveServiceImpl(Drive drive, boolean logResponses) {
        super(drive);
        this.logResponses = logResponses;
    }

    @Override
    public FileList listAllFoldersAndFiles() {
        String q = "trashed=false and (not mimeType contains 'application/vnd.google-apps' " +
                "or mimeType = '" + FOLDER_MIME_TYPE + "')";
        return list(q, 0, null);
    }

    @Override
    public FileList listRootFoldersAndFiles() {
        String q = "'root' in parents and trashed=false and (not mimeType contains 'application/vnd.google-apps' " +
                "or mimeType = '" + FOLDER_MIME_TYPE + "')";
        return list(q, 0, null);
    }

    @Override
    public FileList listFoldersAndFile(File file) {
        String q = MessageFormat.format("''{0}'' in parents and trashed=false ", file.getId());
        return list(q, 0, file.getId());
    }

    private FileList list(String q, int callStackIdx, String hashCode) {
        FileList result;
        try {
            Drive.Files.List list = drive.files().list().setQ(q).setFields("*");
            result = list.execute();
            if (logResponses) {
                cacheReponse(result, ++callStackIdx, hashCode);
            }
            return result;
        } catch (IOException e) {
            throw new AppException(e);
        }
    }


}
