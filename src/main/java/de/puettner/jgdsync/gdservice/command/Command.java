package de.puettner.jgdsync.gdservice.command;

public interface Command {

    String LS = "ls";
    String TESTDEBUGLOGS = "testdebuglogs";
    String INITCHECK = "initcheck";
    String INITSYNC = "syncinit";
    String CONFIGUPDATE = "configupdate";

    CommandResult execute(CommandArgs arguments);
}