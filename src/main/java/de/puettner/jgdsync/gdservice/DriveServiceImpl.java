package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
import de.puettner.jgdsync.DriveFileUtil;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;
import lombok.extern.java.Log;

import java.io.IOException;
import java.text.MessageFormat;

import static de.puettner.jgdsync.DriveFileUtil.FOLDER_MIME_TYPE;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Log
public class DriveServiceImpl extends DriveServiceBase implements DriveService {

    private final boolean logResponses;

    public DriveServiceImpl(Drive drive, boolean logResponses, AppConfig appConfig, Node<SyncNode> rootNode) {
        super(drive, appConfig, rootNode);
        this.logResponses = logResponses;
    }

    /**
     * Lists all folders and files.
     *
     * @return
     */
    @Override
    public FileList listAll() {
        String q = "trashed=false and (not mimeType contains 'application/vnd.google-apps' " +
                "or mimeType = '" + FOLDER_MIME_TYPE + "')";
        FileList cacheResult = getCachedResponse(0, null);
        if (cacheResult != null) {
            return cacheResult;
        }
        return list(q, 0, null);
    }

    /**
     * Lists all folders and files within the root folder.
     *
     * @return
     */
    @Override
    public Node<SyncNode> listRootFolder() {
        if (rootNode.getData().isGdFileLoaded()) {
            return rootNode;
        }
        String q = "'root' in parents and trashed=false and (not mimeType contains 'application/vnd.google-apps' " +
                "or mimeType = '" + FOLDER_MIME_TYPE + "')";

        FileList fileList = getCachedResponse(0, null);
        if (fileList == null) {
            fileList = list(q, 0, null);
        }
        return super.fileList2NodeList(fileList);
    }

    /**
     * Lists all folders and files within a provided folder.
     *
     * @param folder
     * @return
     */
    @Override
    public FileList listFolder(File folder) {
        String q = MessageFormat.format("''{0}'' in parents and trashed=false ", folder.getId());
        if (!DriveFileUtil.isFolder(folder)) {
            throw new IllegalArgumentException("Wrong Mimetype");
        }
        FileList cacheResult = getCachedResponse(0, folder.getId());
        if (cacheResult != null) {
            return cacheResult;
        }
        return list(q, 0, folder.getId());
    }

    private FileList getCachedResponse(int callStackIdx, String hashCode) {
        FileList result;
        if ((result = getFileList(++callStackIdx, hashCode)) != null) {
            return result;
        }
        return null;
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
