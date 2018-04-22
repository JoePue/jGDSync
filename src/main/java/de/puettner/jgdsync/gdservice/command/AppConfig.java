package de.puettner.jgdsync.gdservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Log
public class AppConfig {

    @Nonnull
    private File lclFolder;
    @Nonnull
    private String gdFolderId;
    @Nonnull
    private List<String> lclIgnoreFolders;

    boolean cacheResponses = true;
}
