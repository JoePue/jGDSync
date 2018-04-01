package de.puettner.jgdsync.gdservice;

import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.FileList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
public class DriveServiceBase {

    private JacksonFactory factory = JacksonFactory.getDefaultInstance();

    protected final Drive drive;

    protected DriveServiceBase(Drive drive){
        this.drive = drive;
    }

    protected FileList loadCachedResponse(java.io.File file) {
        log.debug(new Object(){}.getClass().getEnclosingMethod().getName());
        try {
            String content = FileUtils.readFileToString(file, Charset.forName("UTF-8"));
            return factory.createJsonParser(content).parse(FileList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected  <T extends com.google.api.client.json.GenericJson> T cacheReponse(T result, java.io.File file) {
        log.debug(new Object(){}.getClass().getEnclosingMethod().getName());
        try {
            FileUtils.write((file), factory.toPrettyString(result), Charset.forName("UTF-8"), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected java.io.File newFile(String name) {
        return  new java.io.File(name + ".json");
    }

}