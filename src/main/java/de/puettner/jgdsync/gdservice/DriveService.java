package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Property;

import java.util.List;

import static java.lang.System.out;

public interface DriveService {

    FileList list();

    default void printFiles(File file) {
        List<Property> props = file.getProperties();
        String propString = null;
        if (props != null) {
            propString = "";
            for (Property p : props) {
                propString += p.getValue() + " " + p.getKind() + " " + p.getKey();
            }
        }
        out.printf("%-50s  %-30s\t\tkind: %s\t\tmimetype: %s", file.getId(), file.getTitle(), file.getKind(), file.getMimeType());

        if (propString != null) {
            out.printf("\n\t[Properties] %s", propString);
        }
        out.println();
    }
}
