package de.puettner.jgdsync.gdservice.command;

public interface Command {

    String PROGRAMM_NAME = "jgdsync";
    String LS = "ls";
    String TESTDEBUGLOGS = "testdebuglogs";
    String INITCHECK = "initcheck";
    String INITSYNC = "syncinit";
    String CONFIGUPDATE = "configupdate";
    String DIFF = "diff";
    String DOWNLOAD = "download";

    CommandResult execute(CommandArgs arguments);

    String getCommandName();

    String getHelpInfo();

    String getUsageInfo();
}
