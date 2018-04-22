package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.DriveService;

public class ConfigUpdateCommand implements Command {

    private final DriveService service;
    private AppConfigBuilder builder = new AppConfigBuilder();
    private AppConfig appConfig;

    public ConfigUpdateCommand(DriveService service) {
        this.service = service;
    }

    public CommandResult execute(CommandArgs args) {
        this.appConfig = builder.build();
        if (AppConfigBuilder.validate(appConfig)) {
            service.setAppConfig(appConfig);
        }
        return null;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }
}
