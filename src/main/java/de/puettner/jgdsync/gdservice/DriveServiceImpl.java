package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.sun.corba.se.impl.protocol.giopmsgheaders.Message;
import de.puettner.jgdsync.AppException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceImpl extends DriveServiceBase implements DriveService {

    private final boolean logReponses;

    public DriveServiceImpl(Drive drive, boolean logResponses) {
        super(drive);
        this.logReponses = logResponses;
    }

    /*    @Override
        public Drive.Children.List children(String folderId) {
            try {
                Drive.Children.List result = drive.children().list(folderId);
                if (logReponses) {
                    File file = newFile(new Object() {}.getClass().getEnclosingMethod().getName());
                    cacheReponse(result, file);
                }
                return result;
            } catch (IOException e) {
                throw new AppException(e);
            }
            return null;
        }
    */
    @Override
    public FileList listRootFiles() {
        //return list("mimeType = 'application/vnd.google-apps.folder' and 'root' in parents and trashed=false", 0);
        String q = "'root' in parents and trashed=false and (not mimeType contains 'application/vnd.google-apps' or mimeType = 'application/vnd.google-apps.folder')";
        return list(q, 0, null);
    }

    @Override
    public FileList list(File file) {
        String q = MessageFormat.format("''{0}'' in parents and trashed=false ", file.getId());
        return list(q, 0, file.getId());
    }

    private FileList list(String q, int callStackIdx, String hashCode) {
        FileList result;
        try {
            // application/vnd.google-apps.folder      mimeType = 'application/vnd.google-apps.folder'
            Drive.Files.List list = drive.files().list().setQ(q);
            result = list.execute();
            if (logReponses) {
                cacheReponse(result, ++callStackIdx, hashCode);
            }
            return result;
        } catch (IOException e) {
            throw new AppException(e);
        }
    }


}
