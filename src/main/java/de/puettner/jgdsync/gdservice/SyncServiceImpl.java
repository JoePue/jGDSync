package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.GDFile;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import de.puettner.jgdsync.model.SyncNodeFactory;
import lombok.extern.java.Log;

import java.io.File;
import java.util.Arrays;

import static de.puettner.jgdsync.DriveFileUtil.FOLDER_MIME_TYPE;
import static de.puettner.jgdsync.gdservice.DriveQueryBuilder.QUERY_ALL;
import static de.puettner.jgdsync.gdservice.DriveQueryBuilder.QUERY_ROOT_FOLDER;
import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.error;
import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.println;
import static java.text.MessageFormat.format;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Log
public class SyncServiceImpl implements SyncService {

    protected final Node<SyncData> rootNode;
    private final CacheService cacheService = new CacheService();
    private final DriveService driveService;
    private final FileIgnoreFilter fileIgnoreFilter;
    private final SyncNodeFactory syncNodeFactory;
    private AppConfig appConfig;

    public SyncServiceImpl(AppConfig appConfig, Node<SyncData> rootNode, DriveService driveService, FileIgnoreFilter fileIgnoreFilter) {
        this.appConfig = appConfig;
        this.rootNode = rootNode;
        this.driveService = driveService;
        this.fileIgnoreFilter = fileIgnoreFilter;
        this.syncNodeFactory = SyncNodeFactory.getInstance(appConfig.getLclFolder(), this.fileIgnoreFilter);
    }

    /**
     * Lists all folders and files.
     *
     * @return
     */
    @Override
    public FileList listAll() {
        FileList fileList = cacheService.getCachedFileList(0, QUERY_ALL.hashCode());
        if (fileList == null) {
            fileList = driveService.list(QUERY_ALL, 0, QUERY_ALL.hashCode());
        }
        return fileList;
    }

    /**
     * Lists all folders and files within the root folder.
     *
     * @return
     */
    @Override
    public Node<SyncData> listRootFolder() {
        if (rootNode.getData().isGdFileLoaded()) {
            return rootNode;
        }

        FileList fileList = cacheService.getCachedFileList(0, QUERY_ROOT_FOLDER.hashCode());
        if (fileList == null) {
            fileList = driveService.list(QUERY_ROOT_FOLDER, 0, QUERY_ROOT_FOLDER.hashCode());
        }
        return File2NodeUtil.fileList2NodeList(syncNodeFactory, fileList);
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public Node<SyncData> findFolderByName(String foldername) {
        return findFolder(DriveQueryBuilder.buildFindFolderQuery(foldername, null, FOLDER_MIME_TYPE));
    }

    /**
     * Finds a folder by its id;
     *
     * @param folderId
     * @return null if not found otherwise a node
     */
    @Override
    public Node<SyncData> findFolderById(String folderId) {
        com.google.api.services.drive.model.File file = cacheService.getCachedFile(0, folderId.hashCode());
        if (file == null) {
            file = driveService.get(folderId, 0);
        }
        return File2NodeUtil.file2Node(syncNodeFactory, file);
    }

    /**
     * @return null if not found otherwise a node
     */
    @Override
    public Node<SyncData> createRootSyncNode() {
        Node<SyncData> rootRemote = this.findFolderById(appConfig.getGdFolderId());
        if (rootRemote != null) {
            Node<SyncData> rootLocal = syncNodeFactory.constructRootSyncNode();
            syncNodeFactory.syncRootNoteWithRemote(rootLocal, rootRemote);
            rootRemote = rootLocal;
        }
        return rootRemote;
    }

    /**
     * Lists all folders and files within a provided folder.
     *
     * @param node
     * @return
     */
    @Override
    public Node<SyncData> listFolderByNode(Node<SyncData> node, boolean recursive) {
        String q = format("''{0}'' in parents and trashed=false ", node.getData().getGdFile().getId());
        if (!node.getData().getGdFile().isFolder()) {
            throw new IllegalArgumentException("Wrong Mimetype");
        }
        FileList fileList = cacheService.getCachedFileList(0, q.hashCode());
        if (fileList == null) {
            fileList = driveService.list(q, 0, q.hashCode());
        }
        Node<SyncData> nodes = File2NodeUtil.fileList2NodeList(syncNodeFactory, fileList, node);
        if (recursive) {
            nodes.getChildren().stream().filter(subNode -> subNode.getData().getGdFile().isFolder()).forEach(subNode -> this
                    .listFolderByNode(subNode, true));
        }
        return nodes;
    }

    @Override
    public void syncNode(Node<SyncData> node) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
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

    private File createDownloadFile(String filename) {
        return new File(filename);
    }

    private Node<SyncData> findFolder(String q) {
        FileList fileList = cacheService.getCachedFileList(0, q.hashCode());
        if (fileList == null) {
            fileList = driveService.list(q, 0, q.hashCode());
        }
        return File2NodeUtil.fileList2NodeList(syncNodeFactory, fileList);
    }

    private void fetchLocalData(Node<SyncData> node) {
        java.io.File[] files = node.getData().getFile().listFiles(fileIgnoreFilter);
        Arrays.stream(files).forEach(file -> node.addChild(syncNodeFactory.construct(file)));
    }

}
