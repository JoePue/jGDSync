package de.puettner.jgdsync.model;

import com.google.api.services.drive.model.File;
import de.puettner.jgdsync.DriveFileUtil;

import java.util.Date;
import java.util.List;

public class GDFile {
    private File file;

    public GDFile(File file) {
        this.file = file;
    }

    public boolean isFolder() {
        return DriveFileUtil.isFolder(file);
    }

    public boolean canDownload() {
        return file.getCapabilities().getCanDownload();
    }

    public String getName() {
        return file.getName();
    }

    public Date getModifiedTime() {
        return new Date(file.getModifiedTime().getValue());
    }

    public String getId() {
        return file.getId();
    }

    public String getMimeType() {
        return file.getMimeType();
    }

    public List<String> getParents() {
        return file.getParents();
    }

    @Override
    public String toString() {
        return file.getName();
    }
}

