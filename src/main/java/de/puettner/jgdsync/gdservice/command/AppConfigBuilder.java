package de.puettner.jgdsync.gdservice.command;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import java.io.File;
import java.util.Collections;

import static de.puettner.jgdsync.AppConstants.CONFIG_FILE;
import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

@Slf4j
public class AppConfigBuilder {

    public static final String GD_FOLDER = "gd.folder";
    public static final String LCL_FOLDER_IGNORE = "lcl.folder.ignore";

    /**
     * @return Returns null if config file could not load.
     */
    public static AppConfig build() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>
                (PropertiesConfiguration.class).configure(params.properties().setFileName(CONFIG_FILE.toString()));
        AppConfig appConfig = new AppConfig();
        try {
            Configuration config = builder.getConfiguration();
            appConfig.setGdFolder(config.getString(GD_FOLDER, null));
            appConfig.setLclFolder(new File("."));
            appConfig.setLclIgnoreFolders(config.getList(String.class, LCL_FOLDER_IGNORE, Collections.EMPTY_LIST));
        } catch (org.apache.commons.configuration2.ex.ConfigurationException e) {
            log.error(e.getMessage(), e);
        }
        return appConfig;
    }

    public static boolean validate(AppConfig appConfig) {
        if (appConfig == null) {
            log.error(out("Invalid config"));
            return false;
        }
        if (appConfig.getGdFolder() == null || appConfig.getGdFolder().isEmpty()) {
            log.error(out("Invalid property " + GD_FOLDER));
            return false;
        }
        if (appConfig.getLclIgnoreFolders() == null) {
            log.error(out("Invalid property" + LCL_FOLDER_IGNORE));
            return false;
        }
        return true;
    }
}
