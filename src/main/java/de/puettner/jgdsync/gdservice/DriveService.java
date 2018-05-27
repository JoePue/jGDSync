package de.puettner.jgdsync.gdservice;

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpResponseException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import de.puettner.jgdsync.exception.AppUnexpectedException;
import de.puettner.jgdsync.model.GDErrorResponse;
import de.puettner.jgdsync.model.GDTokenResponseError;
import lombok.extern.java.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;

import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.println;
import static java.text.MessageFormat.format;

@Log
public class DriveService {

    private final boolean cacheResponses;
    private final CacheService cacheService = new CacheService();
    protected final Drive drive;

    public DriveService(Drive drive, boolean cacheResponses) {
        this.drive = drive;
        this.cacheResponses = cacheResponses;
    }

    public FileList list(String q, int callStackIdx) {
        FileList fileList = null;
        try {
            Drive.Files.List list = drive.files().list().setQ(q).setFields("*");
            fileList = list.execute();
            if (cacheResponses) {
                cacheService.cacheResponse(fileList, ++callStackIdx, q.hashCode());
            }
            return fileList;
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

    public void download(String fileId, java.io.File lclFile) {
        try {
            OutputStream outStream = new FileOutputStream(lclFile);
            drive.files().get(fileId).executeMediaAndDownloadTo(outStream);
            println("Download completed. Go to: " + lclFile.getAbsolutePath());
        } catch (Exception e) {
            handleException(e);
        }
    }

    private void handleException(Exception e) {
        log.log(Level.SEVERE, e.getMessage(), e);
        if (e instanceof TokenResponseException) {
            String message = e.getMessage();
            GDTokenResponseError response = JacksonFactoryUtil.parseGDTokenResponseException(((TokenResponseException) e).getContent());
            if (response != null && response.getError() != null) {
                message = format("{0} ({1})", response.getDescription(), response.getError());
            }
            throw new AppUnexpectedException(message, e);
        } else if (e instanceof HttpResponseException) {
            String message = e.getMessage();
            GDErrorResponse response = JacksonFactoryUtil.parseGDErrorResponse(((HttpResponseException) e).getContent());
            if (response != null && response.getError() != null && response.getError().getMessage() != null) {
                message = response.getError().getMessage();
            }
            throw new AppUnexpectedException(message, e);
        } else if (e instanceof IOException) {
            throw new AppUnexpectedException(e);
        } else if (e instanceof GoogleJsonResponseException) {
            GoogleJsonResponseException i = (GoogleJsonResponseException) e;
            if (i.getStatusCode() == 404) {
                return;
            }
            throw new AppUnexpectedException(e);
        }
    }
}
