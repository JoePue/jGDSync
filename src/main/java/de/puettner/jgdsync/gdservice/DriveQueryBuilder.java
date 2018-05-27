package de.puettner.jgdsync.gdservice;

import lombok.extern.java.Log;

import static de.puettner.jgdsync.DriveFileUtil.FOLDER_MIME_TYPE;

@Log
public class DriveQueryBuilder {

    public static final String ROOT_FOLDER_NAME = "root";

    private static final String GDID_PLACEHOLDER = "__GDID__";

    private static final String QUERY_FOLDER_BY_PARENT_ID = "'" + GDID_PLACEHOLDER + "' in parents and trashed=false and (not mimeType contains " +
            "'application/vnd.google-apps' or mimeType = '" + FOLDER_MIME_TYPE + "')";

    public static final String QUERY_ALL = "trashed=false and (not mimeType contains 'application/vnd.google-apps' " +
            "or mimeType = '" + FOLDER_MIME_TYPE + "')";

    public static String getQueryToListFolderByParentId(String gdId) {
        return QUERY_FOLDER_BY_PARENT_ID.replace(GDID_PLACEHOLDER, gdId);
    }

    public static String getQueryToListRootFolder() {
        return QUERY_FOLDER_BY_PARENT_ID.replace(GDID_PLACEHOLDER, ROOT_FOLDER_NAME);
    }

    static String buildFindFolderQuery(String foldername, String folderId, String mimeType) {
        if (foldername == null && folderId == null) {
            throw new IllegalArgumentException("One parameter must not be null (foldername, folderId).");
        }
        String query = "";
        if (foldername != null) {
            query = " name='" + foldername + "'";
        }
        if (folderId != null) {
            query = "parents='" + folderId + "'";
        }
        if (mimeType != null) {
            query = "and mimeType='" + mimeType + "'";
        }
        query += " and trashed=false";

        log.info("query: " + query);
        return query;
    }
}
