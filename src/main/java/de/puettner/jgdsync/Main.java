package de.puettner.jgdsync;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.DriveServiceBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

/**
 * https://developers.google.com/drive/v2/web/quickstart/java
 * C:\Users\joerg.puettner\.credentials
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        printLogStatusInfo();
        DriveService gdService = DriveServiceBuilder.build(true, true);
        // Drive.Apps apps = drive.apps();
        new JGDSync(gdService).processCmdOptions(args);
    }

    private static void printLogStatusInfo() {
        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
        log.trace("trace");
        System.out.printf("isDebugEnabled: %s, isErrorEnabled: %s, isInfoEnabled: %s, isTraceEnabled: %s, isWarnEnabled: %s %n", log.isDebugEnabled(), log.isErrorEnabled(), log.isInfoEnabled(), log.isTraceEnabled(), log.isWarnEnabled());

        Logger logger = Logger.getLogger(Main.class.getSimpleName());
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
    }

}