package de.puettner.jgdsync.gdservice;

import java.io.File;
import java.io.FileFilter;
import java.util.List;

public class FileIgnoreFilter implements FileFilter {

    private final int baseDirLength;
    private final File baseDir;
    private List<String> ignoreFolderList;

    public FileIgnoreFilter(File baseDir, List<String> ignoreFolderList) {
        this.baseDir = baseDir;
        this.ignoreFolderList = ignoreFolderList;
        this.baseDirLength = baseDir.getAbsolutePath().toString().length();
    }

    @Override
    public boolean accept(File file) {
        if (!ignoreFolderList.isEmpty()) {
            String filename = getFilename(file);
            for (String folder : ignoreFolderList) {
                if (folder.startsWith("/")) {
                    if (filename.startsWith(folder)) {
                        return false;
                    }
                } else if (folder.endsWith("/")) {
                    if (filename.contains(folder)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    public String getFilename(File file) {
        return file.getAbsolutePath().substring(baseDirLength).replace('\\', '/');
    }
}
/*
class Test extends File {
    public Test(){
        super("");
    }
}
*/
