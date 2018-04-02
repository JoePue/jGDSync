package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.command.LsCommand;
import lombok.extern.slf4j.Slf4j;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java C:\Users\joerg.puettner\.credentials
 */
@Slf4j
public class JGDSync {

    DriveService service;

    public JGDSync(DriveService service) {
        this.service = service;
    }

    public void processCmdOptions(String[] args) {
        log.info("processCmdOptions");
        new LsCommand(service).process();
    }



}