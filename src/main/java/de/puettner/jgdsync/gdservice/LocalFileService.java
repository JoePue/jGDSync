package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.exception.ErrorCode;

import java.io.File;

import static de.puettner.jgdsync.exception.UserInputException.constuctAppISException;

public class LocalFileService {

    public File createFileByString(String filename) {
        try {
            return new File(filename);
        } catch (Exception e) {
            throw constuctAppISException(null, ErrorCode.INVALID_FILENAME, filename);
        }
    }

    public File createDownloadFile(String filename) {
        return createFileByString(filename);
    }
}
