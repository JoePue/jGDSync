package de.puettner.jgdsync.gdservice;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.AppException;
import de.puettner.jgdsync.model.GDErrorResponse;
import lombok.extern.java.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

@Log
public class DriveService {

    protected final Drive drive;
    private final boolean cacheResponses;
    private final CacheService cacheService = new CacheService();

    public DriveService(Drive drive, boolean cacheResponses) {
        this.drive = drive;
        this.cacheResponses = cacheResponses;
    }

    public FileList list(String q, int callStackIdx, int hashCode) {
        FileList fileList = null;
        try {
            Drive.Files.List list = drive.files().list().setQ(q).setFields("*");
            fileList = list.execute();
            if (cacheResponses) {
                cacheService.cacheResponse(fileList, ++callStackIdx, hashCode);
            }
        } catch (IOException e) {
            handleException(e);
        }
        return fileList;
    }

    public File get(String fileId, int callStackIdx) {
        File file = null;
        try {
            Drive.Files.Get get = drive.files().get(fileId).setFields("*");
            file = get.execute();
            if (cacheResponses) {
                cacheService.cacheResponse(file, ++callStackIdx, fileId.hashCode());
            }
        } catch (Exception e) {
            handleException(e);
        }
        return file;
    }

    private void handleException(Exception e) {
        log.log(Level.SEVERE, e.getMessage(), e);
        if (e instanceof HttpResponseException) {
            String message = e.getMessage();
            GDErrorResponse response = JacksonFactoryUtil.parseGDErrorResponse(((HttpResponseException) e).getContent());
            if (response != null && response.getError() != null && response.getError().getMessage() != null) {
                message = response.getError().getMessage();
            }
            throw new AppException(message, e);
        } else if (e instanceof IOException) {
            throw new AppException(e);
        } else if (e instanceof GoogleJsonResponseException) {
            GoogleJsonResponseException i = (GoogleJsonResponseException) e;
            if (i.getStatusCode() == 404) {
                return;
            }
            throw new AppException(e);
        }
    }
}
