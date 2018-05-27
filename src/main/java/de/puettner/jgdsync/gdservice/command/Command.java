package de.puettner.jgdsync.gdservice.command;

public interface Command {

    String PROGRAMM_NAME = "jgdsync";
    String LS = "ls";
    String TESTDEBUGLOGS = "testdebuglogs";
    String INIT = "init";
    String INITCHECK = "initcheck";
    String INITSYNC = "syncinit";
    String CONFIGUPDATE = "configupdate";
    String DIFF = "diff";
    String DOWNLOAD = "download";
    String HELP = "help";

    CommandResult execute(CommandArgs arguments);

    String getCommandExplanation();

    default String getUsageInfo() {
        return PROGRAMM_NAME + " " + this.getCommandName();
    }

    String getCommandName();

    default boolean isExecutable(CommandArgs arguments) {
        return this.getCommandName().equalsIgnoreCase(arguments.getCommand());
    }
}
