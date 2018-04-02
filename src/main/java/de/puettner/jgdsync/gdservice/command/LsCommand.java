package de.puettner.jgdsync.gdservice.command;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.DriveFileUtil;
import de.puettner.jgdsync.gdservice.DriveService;

import java.util.List;

public class LsCommand implements Command {

    private final DriveService service;

    public LsCommand(DriveService service) {
        this.service = service;
    }

    public void process() {
        this.ls();
    }


    public void ls() {
        String intendFolder = "| "; // |
        String intendFile = "|- "; // |-
        FileList result = service.listRootFoldersAndFiles();
        result.getFiles().stream().filter(file -> DriveFileUtil.isFolder(file)).forEach(file -> {
            printFolder(file, intendFolder);
        });
        result.getFiles().stream().filter(file -> !DriveFileUtil.isFolder(file)).forEach(file -> {
            service.printFile(file, intendFile);
        });
    }

    private void printFolder(File folder, String intend) {
        if (!DriveFileUtil.isFolder(folder)) {
            throw new IllegalStateException("Impossible");
        }
        service.printFile(folder, intend.substring(0, intend.length() - 2) + "+ ");
        String intendFolder = intend + "| "; // |
        String intendFiles = intend + "|- "; // |-
        FileList result = service.listFoldersAndFile(folder);
        List<File> files = result.getFiles();
        if (files != null && files.size() > 0) {
            // Print Folders first
            for (File file : files) {
                if (DriveFileUtil.isFolder(file)) {
                    printFolder(file, intendFolder);
                }
            }
            for (File file : files) {
                if (!DriveFileUtil.isFolder(file)) {
                    service.printFile(file, intendFiles);
                }
            }
        }
    }
}
