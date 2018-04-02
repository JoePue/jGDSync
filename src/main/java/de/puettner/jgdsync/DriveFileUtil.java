package de.puettner.jgdsync;

import com.google.api.services.drive.model.File;

public class DriveFileUtil {
    public static final String FOLDER_MIME_TYPE = "application/vnd.google-apps.folder";

    public static boolean isFolder(File file) {
        return file.getMimeType().equals(FOLDER_MIME_TYPE);
    }
}
