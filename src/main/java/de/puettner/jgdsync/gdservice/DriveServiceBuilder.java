package de.puettner.jgdsync.gdservice;

public class DriveServiceBuilder {

    private DriveServiceBuilder() {
    }

    public static DriveService build(boolean useMock, boolean logReponses) {
        if (useMock) {
            return new DriveServiceMock();
        } else {
            return new DriveServiceImpl(DriveBuilder.build(), true);
        }

    }
}
