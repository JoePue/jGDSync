package de.puettner.jgdsync;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.DriveService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static java.lang.System.out;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 */
@Slf4j
public class JGDSync {

    DriveService service;

    public JGDSync(DriveService service) {
        this.service = service;
    }

    public void processCmdOptions(String[] args) {
        log.info("processCmdOptions");
        // Print the names and IDs for up to 10 files.
        //        service.children().get();

        FileList result = service.list();
        List<File> files = result.getItems();
        if (files == null || files.size() == 0) {
            out.println("No files found.");
        } else {
            out.println("Files:");
            for (File file : files) {
                service.printFiles(file);
            }
        }
    }


}