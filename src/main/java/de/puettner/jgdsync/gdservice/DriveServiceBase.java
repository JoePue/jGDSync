package de.puettner.jgdsync.gdservice;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.DriveFileUtil;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.GDFile;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static de.puettner.jgdsync.AppConstants.CACHE_DIR;

@Log
public class DriveServiceBase {

    protected final Node<SyncNode> rootNode;
    protected final Drive drive;
    private JacksonFactory factory = JacksonFactory.getDefaultInstance();
    private AppConfig appConfig;

    protected DriveServiceBase(Drive drive, AppConfig appConfig, Node<SyncNode> rootNode) {
        this.appConfig = appConfig;
        this.drive = drive;
        this.rootNode = rootNode;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    protected FileList getFileList(int callStackIdx, int hashCode) {
        log.fine("getFileList");
        File file = createCacheFile(++callStackIdx, hashCode);
        if (file.exists()) {
            try {
                String content = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
                log.finer("Using Cache for " + file.getName());
                return factory.createJsonParser(content).parse(FileList.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected java.io.File createCacheFile(int idx, int hashCode) {
        String methodName = new Throwable().getStackTrace()[++idx].getMethodName();
        return new java.io.File(CACHE_DIR + File.separator + methodName + "_" + hashCode + ".json");
    }

    protected <T extends com.google.api.client.json.GenericJson> T cacheReponse(T result, int callStackIdx, int hashCode) {
        log.fine(new Object() {}.getClass().getEnclosingMethod().getName());
        try {
            FileUtils.write((createCacheFile(++callStackIdx, hashCode)), factory.toPrettyString(result), Charset.forName("UTF-8"), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Node<SyncNode> fileList2NodeList(FileList fileList) {
        Node<SyncNode> tempNode = new Node<>(null, true);
        return this.fileList2NodeList(fileList, tempNode);
    }


    public Node<SyncNode> fileList2NodeList(FileList fileList, Node<SyncNode> parentNode) {

        for (com.google.api.services.drive.model.File file : fileList.getFiles()) {
            if (DriveFileUtil.isFolder(file)) {
                Node<SyncNode> node = new Node<>(new SyncNode(false, new GDFile(file), null), true);
                parentNode.addChild(node);
            } else {
                parentNode.addChild(new SyncNode(false, new GDFile(file), null));
            }
        }
        return parentNode;
    }
}
