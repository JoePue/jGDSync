package de.puettner.jgdsync;

abstract class AppEnvironment {

    public static boolean isDevMode() {
        return System.getProperty("app.profiles.active").equals("DEV");
    }
}
