package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.exception.ErrorCode;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.GDFile;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import de.puettner.jgdsync.model.SyncNodeFactory;
import lombok.extern.java.Log;

import java.io.File;
import java.util.Arrays;

import static de.puettner.jgdsync.DriveFileUtil.FOLDER_MIME_TYPE;
import static de.puettner.jgdsync.exception.UserInputException.constuctAppISException;
import static de.puettner.jgdsync.exception.UserInputException.throwAppISException;
import static de.puettner.jgdsync.gdservice.DriveQueryBuilder.getQueryToListFolderByParentId;
import static de.puettner.jgdsync.gdservice.DriveQueryBuilder.getQueryToListRootFolder;
import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.println;
import static java.text.MessageFormat.format;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Log
public class SyncService {

    protected final Node<SyncData> rootNode;
    private final CacheService cacheService = new CacheService();
    private final DriveService driveService;
    private final FileIgnoreFilter fileIgnoreFilter;
    private final SyncNodeFactory syncNodeFactory;
    private AppConfig appConfig;
    private final LocalFileService lclFileSrv;

    public SyncService(AppConfig appConfig, Node<SyncData> rootNode, DriveService driveService, FileIgnoreFilter fileIgnoreFilter,
                       LocalFileService lclFileSrv) {
        this.appConfig = appConfig;
        this.rootNode = rootNode;
        this.driveService = driveService;
        this.fileIgnoreFilter = fileIgnoreFilter;
        this.syncNodeFactory = SyncNodeFactory.getInstance(appConfig.getLclFolder(), this.fileIgnoreFilter);
        this.lclFileSrv = lclFileSrv;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }


    public Node<SyncData> findFolderByName(String foldername) {
        return findFolder(DriveQueryBuilder.buildFindFolderQuery(foldername, null, FOLDER_MIME_TYPE));
    }

    /**
     * Finds a folder of file by its id.
     *
     * @param gdId google drive file id
     * @return null if not found otherwise an object
     */

    public Node<SyncData> findFileById(String gdId) {
        com.google.api.services.drive.model.File file = cacheService.getCachedFile(0, gdId.hashCode());
        if (file == null) {
            file = driveService.get(gdId, 0);
        }
        return File2NodeUtil.file2Node(syncNodeFactory, file);
    }

    /**
     * @return null if not found otherwise a node
     */

    public Node<SyncData> createRootSyncNode() {
        Node<SyncData> rootRemote = this.findFileById(appConfig.getGdFolderId());
        if (rootRemote != null) {
            Node<SyncData> rootLocal = syncNodeFactory.constructRootSyncNode();
            syncNodeFactory.syncRootNoteWithRemote(rootLocal, rootRemote);
            rootRemote = rootLocal;
        }
        return rootRemote;
    }


    private FileList listFiles(String query) {
        FileList fileList = cacheService.getCachedResponse(0, query.hashCode());
        if (fileList == null) {
            fileList = driveService.list(query, 0);
        }
        return fileList;
    }

    /**
     * Lists content of a folder.
     *
     * @return
     */
    public FileList listFolderById(String gdId) {
        return listFiles(getQueryToListFolderByParentId(gdId));
    }

    /**
     * Lists content of a folder.
     *
     * @return
     */
    public Node<SyncData> listFolderForSync(String gdId) {
        return File2NodeUtil.fileList2NodeList(syncNodeFactory, listFolderById(gdId));
    }

    /**
     * Lists all folders and files within the root folder.
     *
     * @return
     */
    public Node<SyncData> listRootFolderForSync() {
        if (rootNode.getData().isGdFileLoaded()) {
            return rootNode;
        }
        FileList fileList = listFiles(getQueryToListRootFolder());
        return File2NodeUtil.fileList2NodeList(syncNodeFactory, fileList);
    }

    /**
     * Lists all folders and files within a provided folder.
     *
     * @param node
     * @return
     */

    public Node<SyncData> listFolderByNode(Node<SyncData> node, boolean recursive) {
        String q = format("''{0}'' in parents and trashed=false ", node.getData().getGdFile().getId());
        if (!node.getData().getGdFile().isFolder()) {
            throw new IllegalArgumentException("Wrong Mimetype");
        }
        FileList fileList = listFiles(q);
        Node<SyncData> nodes = File2NodeUtil.fileList2NodeList(syncNodeFactory, fileList, node);
        if (recursive) {
            nodes.getChildren().stream().filter(subNode -> subNode.getData().getGdFile().isFolder()).forEach(subNode -> this
                    .listFolderByNode(subNode, true));
        }
        return nodes;
    }


    public void syncNodeChildren(Node<SyncData> node) {
        SyncData syncData = node.getData();
        if (!syncData.isInSync()) {
            throw new IllegalStateException("node is not in sync " + node);
        }
        fetchLocalData(node);
        // fetch remote data
        Node<SyncData> result = this.listFolderByNode(node, false);
        System.out.println(result);
    }

    /**
     *
     * @param gdFileId
     * @param localFileStr
     * @param overwrite
     * @return
     */
    public File downloadFileById(String gdFileId, String localFileStr, boolean overwrite) {
        log.info(format("downloadFileById() gdFileId={0}", gdFileId));
        Node<SyncData> node = findFileById(gdFileId);
        if (node == null) {
            throwAppISException(null, ErrorCode.REMOTE_FILE_NOT_FOUND);
        }
        GDFile gdFile = node.getData().getGdFile();

        if (gdFile.canDownload()) {
            File localFile;
            if (localFileStr == null) {
                localFile = lclFileSrv.createDownloadFile(gdFile.getName());
            } else {
                localFile =  lclFileSrv.createFileByString(localFileStr);
            }
            if (!overwrite && localFile.exists()) {
                throwAppISException(null, ErrorCode.LOCAL_FILE_ALREADY_EXISTS);
            }
            if (localFile.isDirectory()) {
                throwAppISException(null, ErrorCode.LOCAL_FILE_IS_A_DIR);
            }
            driveService.download(gdFileId, localFile);
            return localFile;
        }
        log.warning(println(ErrorCode.REMOTE_FILE_CANT_BE_DOWNLOAD.message));
        throw constuctAppISException(null, ErrorCode.REMOTE_FILE_CANT_BE_DOWNLOAD);
    }

    private Node<SyncData> findFolder(String q) {
        FileList fileList = listFiles(q);
        return File2NodeUtil.fileList2NodeList(syncNodeFactory, fileList);
    }

    private void fetchLocalData(Node<SyncData> node) {
        java.io.File[] files = node.getData().getFile().listFiles(fileIgnoreFilter);
        Arrays.stream(files).forEach(file -> node.addChild(syncNodeFactory.construct(file)));
    }

    public void findFileByPath(String gdFilepath) {
        String[] pathSegments = SyncServiceInputParameterValidator.validateGDFilePath(gdFilepath);
        FileList result;
        for (String segment : pathSegments) {
            result = this.listFolderById(segment);
        }
    }
}
