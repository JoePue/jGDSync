package de.puettner.jgdsync.gdservice;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.model.FileList;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;

import static de.puettner.jgdsync.AppConstants.CACHE_DIR;

@Log
public class CacheService {

    public static final String UTF_8 = "UTF-8";
    private JacksonFactory factory = JacksonFactory.getDefaultInstance();

    protected FileList getCachedFileList(int callStackIdx, int hashCode) {
        log.fine("getCachedFileList");
        File file = createCacheFile(++callStackIdx, hashCode);
        if (file.exists()) {
            try {
                String content = FileUtils.readFileToString(file, Charset.forName(UTF_8));
                log.finer("Using Cache for " + file.getName());
                return factory.createJsonParser(content).parse(FileList.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected com.google.api.services.drive.model.File getCachedFile(int callStackIdx, int hashCode) {
        log.fine("getCachedFile");
        File file = createCacheFile(++callStackIdx, hashCode);
        if (file.exists()) {
            try {
                String content = FileUtils.readFileToString(file, Charset.forName(UTF_8));
                log.finer("Using Cache for " + file.getName());
                return factory.createJsonParser(content).parse(com.google.api.services.drive.model.File.class);
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

    protected <T extends com.google.api.client.json.GenericJson> T cacheResponse(T result, int callStackIdx, int hashCode) {
        log.fine(new Object() {}.getClass().getEnclosingMethod().getName());
        try {
            FileUtils.write((createCacheFile(++callStackIdx, hashCode)), factory.toPrettyString(result), Charset.forName(UTF_8), false);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }
}
