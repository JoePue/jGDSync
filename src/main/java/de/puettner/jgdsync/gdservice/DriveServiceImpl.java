package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
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

    private final boolean cacheResponses;

    public DriveServiceImpl(Drive drive, boolean logResponses, AppConfig appConfig, Node<SyncNode> rootNode) {
        super(drive, appConfig, rootNode);
        this.cacheResponses = logResponses;
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
        FileList fileList = getCachedResponse(0, q.hashCode());
        if (fileList == null) {
            fileList = list(q, 0, q.hashCode());
        }
        return fileList;
    }

    private FileList getCachedResponse(int callStackIdx, int hashCode) {
        FileList fileList;
        if ((fileList = getFileList(++callStackIdx, hashCode)) != null) {
            return fileList;
        }
        return null;
    }

    private FileList list(String q, int callStackIdx, int hashCode) {
        FileList fileList;
        try {
            Drive.Files.List list = drive.files().list().setQ(q).setFields("*");
            fileList = list.execute();
            if (cacheResponses) {
                cacheReponse(fileList, ++callStackIdx, hashCode);
            }
            return fileList;
        } catch (IOException e) {
            throw new AppException(e);
        }
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

        FileList fileList = getCachedResponse(0, q.hashCode());
        if (fileList == null) {
            fileList = list(q, 0, q.hashCode());
        }
        return super.fileList2NodeList(fileList);
    }

    @Override
    public Node<SyncNode> findFolderByName(String foldername) {
        String q = MessageFormat.format("name=''{0}'' and trashed=false and mimeType=''" + FOLDER_MIME_TYPE + "''", foldername);
        FileList fileList = getCachedResponse(0, q.hashCode());
        if (fileList == null) {
            fileList = list(q, 0, q.hashCode());
        }
        return super.fileList2NodeList(fileList);
    }

    /**
     * Lists all folders and files within a provided folder.
     *
     * @param node
     * @return
     */
    @Override
    public Node<SyncNode> listFolderByNode(Node<SyncNode> node, boolean recursive) {
        String q = MessageFormat.format("''{0}'' in parents and trashed=false ", node.getData().getGdFile().getId());
        if (!node.getData().getGdFile().isFolder()) {
            throw new IllegalArgumentException("Wrong Mimetype");
        }
        FileList fileList = getCachedResponse(0, q.hashCode());
        if (fileList == null) {
            fileList = list(q, 0, q.hashCode());
        }
        Node<SyncNode> nodes = super.fileList2NodeList(fileList, node);
        if (recursive) {
            nodes.getChildren().stream().filter(subNode -> subNode.getData().getGdFile().isFolder()).forEach(subNode -> this
                    .listFolderByNode(subNode, true));
        }
        return nodes;
    }

}
