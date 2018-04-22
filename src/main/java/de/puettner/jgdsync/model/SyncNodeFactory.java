package de.puettner.jgdsync.model;

import de.puettner.jgdsync.gdservice.FileIgnoreFilter;
import lombok.Data;
import lombok.extern.java.Log;

import java.io.File;

@Data
@Log
public class SyncNodeFactory {

    private static SyncNodeFactory INSTANCE;
    private final File syncBaseDir;
    private final FileIgnoreFilter fileIgnoreFilter;

    private SyncNodeFactory(File syncBaseDir, FileIgnoreFilter fileIgnoreFilter) {
        this.syncBaseDir = syncBaseDir;
        this.fileIgnoreFilter = fileIgnoreFilter;
    }

    public static SyncNodeFactory getInstance(File syncBaseDir, FileIgnoreFilter fileIgnoreFilter) {
        if (INSTANCE == null) {
            INSTANCE = new SyncNodeFactory(syncBaseDir, fileIgnoreFilter);
        }
        return INSTANCE;
    }

    public SyncData construct(File file) {
        return this.construct(false, null, file);
    }

    public SyncData construct(boolean gdFileLoaded, GDFile gdFile, File file) {
        return new SyncData(null, gdFileLoaded, gdFile, file, false);
    }

    public Node<SyncData> constructRootSyncNode() {
        SyncData syncData = construct(false, null, syncBaseDir);
        syncData.setAbsoluteName("/");
        Node<SyncData> node = new Node<SyncData>(syncData, true);
        return node;
    }

    public void syncRootNoteWithRemote(Node<SyncData> rootLocal, Node<SyncData> rootRemote) {
        if (rootLocal == null || rootLocal.getData() == null) {
            throw new IllegalArgumentException("Invalid parameter node");
        }
        if (!rootLocal.isRoot()) {
            throw new IllegalStateException("node is not root");
        }
        syncNodeWithRemote(rootLocal, rootRemote);
    }

    public void syncNodeWithRemote(Node<SyncData> rootLocal, Node<SyncData> rootRemote) {
        if (rootLocal == null || rootLocal.getData() == null) {
            throw new IllegalArgumentException("Invalid parameter rootLocal");
        }
        if (rootRemote == null || rootRemote.getData() == null) {
            throw new IllegalArgumentException("Invalid parameter rootRemote");
        }
        SyncData syncData = rootLocal.getData();
        if (syncData.isInSync()) {
            throw new IllegalStateException("node already in sync");
        }
        if (syncData.isGdFileLoaded() || syncData.getGdFile() != null) {
            throw new IllegalStateException("Missing remote data");
        }
        syncData.setGdFileLoaded(true);
        syncData.setGdFile(rootRemote.getData().getGdFile());
        syncData.setInSync(true);
    }
}
