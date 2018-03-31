package de.puettner.jgdsync;

import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;

import java.io.IOException;
import java.util.List;

import static de.puettner.jgdsync.AppDriveService.getDriveService;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java
 * C:\Users\joerg.puettner\.credentials
 */
public class JGDSync {

    public static void main(String[] args) throws IOException {
        // Build a new authorized API client service.
        Drive service = getDriveService();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list().execute();
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
                System.out.printf("%s (%s)\n", file.getTitle(), file.getId());
            }
        }
    }

}