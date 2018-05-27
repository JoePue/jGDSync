package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.DriveFileUtil;
import de.puettner.jgdsync.model.GDFile;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import de.puettner.jgdsync.model.SyncNodeFactory;

public class File2NodeUtil {

    public static Node<SyncData> fileList2NodeList(SyncNodeFactory syncNodeFactory, FileList fileList) {
        if (fileList == null) {
            return null;
        }
        Node<SyncData> tempNode = new Node<>(null, true);
        return fileList2NodeList(syncNodeFactory, fileList, tempNode);
    }

    public static Node<SyncData> fileList2NodeList(SyncNodeFactory syncNodeFactory, FileList fileList, Node<SyncData> parentNode) {
        if (fileList == null) {
            return null;
        }
        for (File file : fileList.getFiles()) {
            if (DriveFileUtil.isFolder(file)) {
                Node<SyncData> node = new Node<>(syncNodeFactory.construct(false, file2GDFile(file), null), true);
                parentNode.addChild(node);
            } else {
                parentNode.addChild(syncNodeFactory.construct(false, file2GDFile(file), null));
            }
        }
        return parentNode;
    }

    public static GDFile file2GDFile(File file) {
        return new GDFile(file);
    }

    public static Node<SyncData> file2Node(SyncNodeFactory syncNodeFactory, File file) {
        if (file == null) {
            return null;
        }
        return new Node<>(syncNodeFactory.construct(true, file2GDFile(file), null), DriveFileUtil.isFolder(file));
    }
}
