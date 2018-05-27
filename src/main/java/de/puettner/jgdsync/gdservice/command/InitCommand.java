package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import java.io.File;

import static de.puettner.jgdsync.AppConstants.*;
import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;
import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.println;

@Log
public class InitCommand implements Command {

    private final ConsolePrinter consolePrinter;

    public InitCommand(ConsolePrinter consolePrinter) {
        this.consolePrinter = consolePrinter;
    }

    public CommandResult execute(CommandArgs args) {
        CommandResult result;
        File[] folders = {CONFIG_DIR, CACHE_DIR, DOWNLOAD_DIR};
        for (File folder : folders) {
            result = createFolderIfNotExists(folder);
            if (result != null) {
                return result;
            }
        }
        println("Initialization complete.");
        return SUCCESS;
    }

    private CommandResult createFolderIfNotExists(File dir) {
        if (!dir.exists()) {
            log.info(println("mkdirs() " + dir.getAbsolutePath()));
            if (!dir.mkdirs()) {
                return CommandResult.builder().processed(true).successful(false).build();
            }
        }
        return null;
    }

    @Override
    public String getCommandExplanation() {
        return "set up actual directory as synchronizable with a your Google Drive";
    }

    public String getCommandName() {
        return INIT;
    }

    @Override
    public boolean displayHelp() {
        return false;
    }
}
