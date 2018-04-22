package de.puettner.jgdsync.gdservice;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class DriveService {

    private final boolean cacheResponses;
    private final CacheService cacheService = new CacheService();
    protected final Drive drive;

    public DriveService(Drive drive, boolean cacheResponses) {
        this.drive = drive;
        this.cacheResponses = cacheResponses;
    }

    public FileList list(String q, int callStackIdx, int hashCode) {
        FileList fileList;
        try {
            Drive.Files.List list = drive.files().list().setQ(q).setFields("*");
            fileList = list.execute();
            if (cacheResponses) {
                cacheService.cacheResponse(fileList, ++callStackIdx, hashCode);
            }
            return fileList;
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    public File get(String fileId, int callStackIdx) {
        File file;
        try {
            Drive.Files.Get get = drive.files().get(fileId).setFields("*");
            file = get.execute();
            if (cacheResponses) {
                cacheService.cacheResponse(file, ++callStackIdx, fileId.hashCode());
            }
            return file;
        } catch (GoogleJsonResponseException e) {
            if (e.getStatusCode() == 404) {
                return null;
            }
            throw new AppException(e);
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
