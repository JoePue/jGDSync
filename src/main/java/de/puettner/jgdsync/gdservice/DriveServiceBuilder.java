package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.gdservice.command.AppConfig;


public class DriveServiceBuilder {

    private DriveServiceBuilder() {
    }

    public static DriveService build(boolean useMock, boolean logResponses, AppConfig appConfig) {
        if (useMock) {
            return new DriveServiceMock(appConfig);
        } else {
            return new DriveServiceImpl(DriveBuilder.build(), logResponses, appConfig);
        }
    }
}
