package de.puettner.jgdsync.gdservice;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.command.AppConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import static de.puettner.jgdsync.AppConstants.CACHE_DIR;

@Slf4j
public class DriveServiceBase {

    private JacksonFactory factory = JacksonFactory.getDefaultInstance();

    protected final Drive drive;
    private AppConfig appConfig;

    protected DriveServiceBase(Drive drive, AppConfig appConfig){
        this.appConfig = appConfig;
        this.drive = drive;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    protected FileList getFiles(int callStackIdx, String hashCode) {
        log.trace("getFiles");
        try {
            String content = FileUtils.readFileToString(newFile(++callStackIdx, hashCode), Charset.forName("UTF-8"));
            return factory.createJsonParser(content).parse(FileList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected  <T extends com.google.api.client.json.GenericJson> T cacheReponse(T result, int callStackIdx, String hashCode) {
        log.debug(new Object(){}.getClass().getEnclosingMethod().getName());
        try {
            FileUtils.write((newFile(++callStackIdx, hashCode)), factory.toPrettyString(result), Charset.forName("UTF-8"), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected java.io.File newFile(int idx, String hashCode) {
        String methodName = new Throwable().getStackTrace()[++idx].getMethodName();
        return  new java.io.File(CACHE_DIR + File.separator + methodName + (hashCode != null ? "_" + hashCode : "") + ".json");
    }

}
