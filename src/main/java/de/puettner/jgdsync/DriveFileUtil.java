package de.puettner.jgdsync;

import com.google.api.services.drive.model.File;

public class DriveFileUtil {
    public static boolean isFolder(File file) {
        return file.getMimeType().equals("application/vnd.google-apps.folder");
    }
}
