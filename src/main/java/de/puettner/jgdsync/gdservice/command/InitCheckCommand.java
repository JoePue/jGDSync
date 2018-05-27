package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import static de.puettner.jgdsync.AppConstants.*;
import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;

@Log
public class InitCheckCommand implements Command {

    private final ConsolePrinter consolePrinter;

    public InitCheckCommand(ConsolePrinter consolePrinter) {
        this.consolePrinter = consolePrinter;
    }

    public CommandResult execute(CommandArgs args) {
        if (!(CONFIG_DIR.exists() && CONFIG_DIR.isDirectory())) {
            consolePrinter.errorf("Missing folder ''{0}''. Use {1}", CONFIG_DIR, Command.INITSYNC);
            return CommandResult.builder().processed(true).successful(false).build();
        }
        if (!(CACHE_DIR.isDirectory() && CACHE_DIR.exists())) {
            consolePrinter.errorf("Missing folder ''{0}''. Use {1}", CACHE_DIR, Command.INITSYNC);
            return CommandResult.builder().processed(true).successful(false).build();
        }
        if (!(CONFIG_FILE.exists() && CONFIG_FILE.isFile())) {
            consolePrinter.errorf("Missing file ''{0}''. Use {1}", CONFIG_FILE, Command.INITSYNC);
            return CommandResult.builder().processed(true).successful(false).build();
        }
        return SUCCESS;
    }

    @Override
    public String getCommandExplanation() {
        return "";
    }

    @Override
    public String getUsageInfo() {
        return "";
    }

    public String getCommandName() {
        return INITCHECK;
    }
}
