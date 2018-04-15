package de.puettner.jgdsync.gdservice.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Slf4j
public class AppConfig {

    @Nonnull
    private File lclFolder;
    @Nonnull
    private String gdFolder;
    @Nonnull
    private List<String> lclIgnoreFolders;

}
