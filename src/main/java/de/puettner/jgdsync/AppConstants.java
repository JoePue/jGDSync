package de.puettner.jgdsync;

import java.io.File;

public class AppConstants {
    public static final String CONFIG_DIR_NAME = ".jgdsync";
    public static final File CONFIG_DIR = new File(CONFIG_DIR_NAME);
    public static final File CONFIG_FILE = new File(CONFIG_DIR_NAME + "/application.properties");
    public static final File CACHE_DIR = new File(CONFIG_DIR_NAME + "/cache");
    public static final File DOWNLOAD_DIR = new File(CONFIG_DIR_NAME + "/download/");
    public static final String UTF_8 = "UTF-8";
}
