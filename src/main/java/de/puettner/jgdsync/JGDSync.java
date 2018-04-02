package de.puettner.jgdsync;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.DriveService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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
        ls();
    }


    public void ls() {
        String intendFolder = "+ ";
        String intendFile = "- ";
        FileList result = service.listRootFiles();
        for(File file : result.getFiles()) {
            if (DriveFileUtil.isFolder(file)) {
                printFolder(file, intendFolder);
            }
        }
        for(File file : result.getFiles()) {
            if (!DriveFileUtil.isFolder(file)) {
                service.printFile(file, intendFile);
            }
        }
    }

    private void printFolder(File folder, String intend) {
        String intendFolder = intend + "+ ";
        String intendFile = intend + "- ";

        if (DriveFileUtil.isFolder(folder)) {
            service.printFile(folder, intend);
            FileList result = service.list(folder);
            List<File> files = result.getFiles();
            if (files != null && files.size() > 0) {
                for (File file : files) {
                    if (DriveFileUtil.isFolder(file)) {
//                        service.printFile(file, intendFolder);
                        printFolder(file, intendFolder);
                    }
                }
                for (File file : files) {
                    if (!DriveFileUtil.isFolder(file)) {
                        service.printFile(file, intendFile);
                    }
                }
            }
        } else {
            service.printFile(folder, intendFile);
        }
    }

}