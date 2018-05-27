package de.puettner.jgdsync.gdservice.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    default List<String> getUsageInfo() {
        List<String> list = new ArrayList<>();
        list.add(PROGRAMM_NAME + " " + this.getCommandName());
        return list;
    }

    String getCommandName();

    default boolean isExecutable(CommandArgs arguments) {
        return this.getCommandName().equalsIgnoreCase(arguments.getCommand());
    }

    default boolean displayHelp() {
        return true;
    }
}
