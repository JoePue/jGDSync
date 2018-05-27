package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.exception.ErrorCode;

import static de.puettner.jgdsync.exception.UserInputException.throwAppISException;
import static de.puettner.jgdsync.gdservice.DriveQueryBuilder.ROOT_FOLDER_NAME;

public class SyncServiceInputParameterValidator {
    public static String[] validateGDFilePath(String gdFilepath) {
        if (gdFilepath == null || !gdFilepath.startsWith("/")) {
            throwAppISException(null, ErrorCode.INVALID_GDFILEPATH);
        }
        String[] pathSegments = gdFilepath.split("/");
        if (gdFilepath.equals("/")) {
            pathSegments = new String[] {ROOT_FOLDER_NAME};
        } else {
            pathSegments[0] = ROOT_FOLDER_NAME;
            for (String segment : pathSegments) {
                if (segment.isEmpty()) {
                    throwAppISException(null, ErrorCode.INVALID_GDFILEPATH);
                }
            }
        }
        return pathSegments;
    }
}
