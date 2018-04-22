package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.MessagePrinter;
import lombok.extern.java.Log;

import static de.puettner.jgdsync.AppConstants.*;
import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;

@Log
public class InitCheckCommand implements Command {

    public CommandResult execute(CommandArgs args) {
        if (!(CONFIG_DIR.exists() && CONFIG_DIR.isDirectory())) {
            MessagePrinter.out("Missing folder ''{0}''. Use {1}", CONFIG_DIR, Command.INITSYNC);
            return CommandResult.builder().processed(true).successful(false).build();
        }
        if (!(CACHE_DIR.isDirectory() && CACHE_DIR.exists())) {
            MessagePrinter.out("Missing folder ''{0}''. Use {1}", CACHE_DIR, Command.INITSYNC);
            return CommandResult.builder().processed(true).successful(false).build();
        }
        if (!(CONFIG_FILE.exists() && CONFIG_FILE.isFile())) {
            MessagePrinter.out("Missing file ''{0}''. Use {1}", CONFIG_FILE, Command.INITSYNC);
            return CommandResult.builder().processed(true).successful(false).build();
        }
        return SUCCESS;
    }
}
