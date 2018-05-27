package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppConstants;
import lombok.extern.java.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;

import static de.puettner.jgdsync.AppConstants.CACHE_DIR;

@Log
public class CacheService {

    protected FileList getCachedResponse(int callStackIdx, int hashCode) {
        log.fine("getCachedResponse");
        File file = createCacheFile(++callStackIdx, hashCode);
        if (file.exists()) {
            try {
                String content = FileUtils.readFileToString(file, Charset.forName(AppConstants.UTF_8));
                log.finer("Using Cache for " + file.getName());
                return JacksonFactoryUtil.parseFileList(content);
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

    protected com.google.api.services.drive.model.File getCachedFile(int callStackIdx, int hashCode) {
        log.fine("getCachedFile");
        File file = createCacheFile(++callStackIdx, hashCode);
        if (file.exists()) {
            try {
                String content = FileUtils.readFileToString(file, Charset.forName(AppConstants.UTF_8));
                log.finer("Using Cache for " + file.getName());
                return JacksonFactoryUtil.parseFile(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    protected <T extends com.google.api.client.json.GenericJson> T cacheResponse(T result, int callStackIdx, int hashCode) {
        log.fine(new Object() {}.getClass().getEnclosingMethod().getName());
        try {
            FileUtils.write((createCacheFile(++callStackIdx, hashCode)), JacksonFactoryUtil.toPrettyString(result), Charset.forName
                    (AppConstants.UTF_8), false);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }
}
