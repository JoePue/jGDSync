package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.Main;
import lombok.extern.slf4j.Slf4j;

import java.util.logging.Logger;

@Slf4j
public class DebugLogsCommand implements Command {

    public DebugLogsCommand() {
    }

    public boolean execute() {
        log.error("error");
        log.warn("warn");
        log.info("info");
        log.debug("debug");
        log.trace("trace");
        System.out.printf("isDebugEnabled: %s, isErrorEnabled: %s, isInfoEnabled: %s, isTraceEnabled: %s, isWarnEnabled: %s %n", log
                .isDebugEnabled(), log.isErrorEnabled(), log.isInfoEnabled(), log.isTraceEnabled(), log.isWarnEnabled());

        Logger logger = Logger.getLogger(Main.class.getSimpleName());
        logger.severe("severe");
        logger.warning("warning");
        logger.info("info");
        logger.fine("fine");
        logger.finer("finer");
        logger.finest("finest");
        return true;
    }
}
