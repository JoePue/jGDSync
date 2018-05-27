package de.puettner.jgdsync.gdservice.command;

import lombok.extern.java.Log;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import java.io.File;
import java.util.Collections;

import static de.puettner.jgdsync.AppConstants.CONFIG_FILE;
import static java.text.MessageFormat.format;

@Log
public class AppConfigBuilder {

    public static final String GD_FOLDER_ID = "gd.folder.id";
    public static final String LCL_FOLDER_IGNORE = "lcl.folder.ignore";

    /**
     * @return Returns null if config file could not load.
     */
    public static AppConfig build() {
        log.fine("build()");
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>
                (PropertiesConfiguration.class).configure(params.properties().setFileName(CONFIG_FILE.toString()));
        AppConfig appConfig = new AppConfig();
        try {
            Configuration config = builder.getConfiguration();
            appConfig.setGdFolderId(config.getString(GD_FOLDER_ID, null));
            appConfig.setLclFolder(new File("."));
            appConfig.setLclIgnoreFolders(config.getList(String.class, LCL_FOLDER_IGNORE, Collections.EMPTY_LIST));
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
            log.severe(e.getMessage());
        }
        return appConfig;
    }

    public static boolean validate(AppConfig appConfig) {
        if (appConfig == null) {
            log.severe(format("Invalid config"));
            return false;
        }
        if (appConfig.getGdFolderId() == null || appConfig.getGdFolderId().isEmpty()) {
            log.severe(format("Invalid property " + GD_FOLDER_ID));
            return false;
        }
        if (appConfig.getLclIgnoreFolders() == null) {
            log.severe(format("Invalid property" + LCL_FOLDER_IGNORE));
            return false;
        }
        return true;
    }
}
