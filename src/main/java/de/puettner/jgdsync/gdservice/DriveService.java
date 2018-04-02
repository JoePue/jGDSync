package de.puettner.jgdsync.gdservice;

import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.util.List;

import static java.lang.System.out;

public interface DriveService {

    FileList listRootFiles();

    FileList list(File q);

    default void printFile(File file, String intend) {
//        List<Property> props = file.getProperties();
//        String propString = null;
//        if (props != null) {
//            propString = "";
//            for (Property p : props) {
//                propString += p.getValue() + " " + p.getKind() + " " + p.getKey();
//            }
//        }

        out.print(intend);
        out.printf("%-50s  %-30s\t\tkind: %s\t\t %s", file.getId(), file.getName(), file.getKind(), file.getMimeType(), file.getParents());
        List<String> parents = file.getParents();
        if (parents != null && parents.size() > 0) {
            out.printf("\t\tparents: ");
            for(String parent : parents) {
                out.printf("%s ", parent); // parent ? "*" : "")
            }
        }

//        if (propString != null) {
//            out.printf("\n\t[Properties] %s", propString);
//        }
        out.println();
    }

    /*    @Override
            public Drive.Children.List children(String folderId) {
                try {
                    Drive.Children.List result = drive.children().list(folderId);
                    if (logReponses) {
                        File file = newFile(new Object() {}.getClass().getEnclosingMethod().getName());
                        cacheReponse(result, file);
                    }
                    return result;
                } catch (IOException e) {
                    throw new AppException(e);
                }
                return null;
            }
        */
}
