package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;
import lombok.extern.slf4j.Slf4j;

/**
 * Source {@See https://developers.google.com/drive/v2/web/quickstart/java}
 */
@Slf4j
public class DriveServiceMock extends DriveServiceBase implements DriveService {

    protected DriveServiceMock(AppConfig appConfig, Node<SyncNode> rootNode) {
        super(null, appConfig, rootNode);
    }

    @Override
    public FileList listAll() {
        return getCachedResponse2(0, null);
    }

    @Override
    public Node<SyncNode> listRootFolder() {
        FileList fileList = getCachedResponse2(0, null);
        return super.fileList2NodeList(fileList);
    }

    @Override
    public FileList listFolder(File q) {
        FileList result;
        if ((result = getFiles(0, q.getId())) != null) {
            return result;
        }
        return getCachedResponse2(0, q.getId());
    }

    private FileList getCachedResponse2(int callStackIdx, String hashCode) {
        FileList result;
        if ((result = getFiles(++callStackIdx, hashCode)) != null) {
            return result;
        }
        return null;
    }
}
