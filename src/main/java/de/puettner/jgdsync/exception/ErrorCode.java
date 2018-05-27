package de.puettner.jgdsync.exception;

public enum ErrorCode {

    REMOTE_FILE_NOT_FOUND(1, "Remote File not found"),
    LOCAL_FILE_ALREADY_EXISTS(2, "Local File already exists"),
    LOCAL_FILE_IS_A_DIR(3, "Local file is a directory"),
    REMOTE_FILE_CANT_BE_DOWNLOAD(4, "Remote file cannot be download"), INVALID_FILENAME(5, "Invalid filename: {0}"), INVALID_GDFILEPATH
            (6, "Invalid Google Drive file path");

    final int code;
    public final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
