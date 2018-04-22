package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.SyncService;

import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;

public class ConfigUpdateCommand implements Command {

    private final SyncService service;
    private AppConfigBuilder builder = new AppConfigBuilder();
    private AppConfig appConfig;

    public ConfigUpdateCommand(SyncService service) {
        this.service = service;
    }

    public CommandResult execute(CommandArgs args) {
        this.appConfig = builder.build();
        if (AppConfigBuilder.validate(appConfig)) {
            service.setAppConfig(appConfig);
        }
        return SUCCESS;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }
}
