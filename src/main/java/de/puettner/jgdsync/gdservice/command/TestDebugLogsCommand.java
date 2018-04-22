package de.puettner.jgdsync.gdservice.command;

import lombok.extern.java.Log;

import java.util.logging.Level;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

@Log
public class TestDebugLogsCommand implements Command {

    public TestDebugLogsCommand() {
    }

    public CommandResult execute(CommandArgs args) {
        log.severe(log.getClass().getName() + " error");
        log.warning(log.getClass().getName() + " warn");
        log.info(out("is SEVERE enabled: %s, is WARNING enabled: %s, is INFO enabled: %s, is CONFIG enabled: %s" + ", is Fine enabled: %s" +
                " , is FINER enabled: %s , is FINEST enabled: %s , is ALL enabled: %s %n", log.isLoggable(Level.SEVERE), log.isLoggable
                (Level.WARNING), log.isLoggable(Level.INFO), log.isLoggable(Level.CONFIG), log.isLoggable(Level.FINE), log.isLoggable
                (Level.FINER), log.isLoggable(Level.FINEST), log.isLoggable(Level.ALL)));
        log.info(log.getClass().getName() + " info");
        log.fine(log.getClass().getName() + " debug");
        log.finer(log.getClass().getName() + " trace");
        return CommandResult.builder().processed(true).successful(true).build();
    }
}