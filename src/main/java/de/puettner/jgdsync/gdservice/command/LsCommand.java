package de.puettner.jgdsync.gdservice.command;

import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;

public class LsCommand implements Command {

    private final ConsolePrinter consolePrinter;
    private final DriveService service;

    public LsCommand(ConsolePrinter consolePrinter, DriveService service) {
        this.consolePrinter = consolePrinter;
        this.service = service;
    }

    public boolean execute() {
        FileList result = service.listRootFoldersAndFiles();

//        result.getFiles().stream().filter(file -> DriveFileUtil.isFolder(file)).forEach(file -> {
//            printFolder(file, intendFolder);
//        });
//        result.getFiles().stream().filter(file -> !DriveFileUtil.isFolder(file)).forEach(file -> {
//            printFile(file, intendFile);
//        });

        consolePrinter.ls(result);

        return true;
    }
}
