package de.puettner.jgdsync.gdservice;

import com.google.api.client.json.GenericJson;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
import de.puettner.jgdsync.model.GDErrorResponse;

import java.io.IOException;

public abstract class JacksonFactoryUtil {

    private static final JacksonFactory factory = JacksonFactory.getDefaultInstance();

    public static FileList parseFileList(String content) throws IOException {
        return factory.createJsonParser(content).parse(FileList.class);
    }

    public static File parseFile(String content) throws IOException {
        return factory.createJsonParser(content).parse(File.class);
    }

    public static <T extends GenericJson> CharSequence toPrettyString(T result) throws IOException {
        return factory.toPrettyString(result);
    }

    public static GDErrorResponse parseGDErrorResponse(String content) {
        try {
            return factory.createJsonParser(content).parse(GDErrorResponse.class);
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
