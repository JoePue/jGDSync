package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.util.List;

import static java.lang.System.out;

public interface DriveService {

    FileList listAllFoldersAndFiles();

    FileList listRootFoldersAndFiles();

    FileList listFoldersAndFile(File q);

    default void printFile(File file, String intend) {
        out.printf("%-60s%-35s %-30s\t\t %s", (intend + file.getId()), file.getName(), file.getModifiedTime(), file.getMimeType(), file
                .getParents());
        List<String> parents = file.getParents();
        if (parents != null && parents.size() > 0) {
            out.printf("\t\tparents: ");
            for(String parent : parents) {
                out.printf("%s ", parent); // parent ? "*" : "")
            }
        }

        out.println();
    }
}
