package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.MessagePrinter;
import lombok.extern.slf4j.Slf4j;

import static de.puettner.jgdsync.AppConstants.*;

@Slf4j
public class InitCheckCommand implements Command {

    public boolean execute() {
        if (!(CONFIG_DIR.exists() && CONFIG_DIR.isDirectory())) {
            MessagePrinter.out("Missing folder ''{0}''. Use {1}", CONFIG_DIR, Command.INITSYNC);
            return false;
        }
        if (!(CACHE_DIR.isDirectory() && CACHE_DIR.exists())) {
            MessagePrinter.out("Missing folder ''{0}''. Use {1}", CACHE_DIR, Command.INITSYNC);
            return false;
        }
        if (!(CONFIG_FILE.exists() && CONFIG_FILE.isFile())) {
            MessagePrinter.out("Missing file ''{0}''. Use {1}", CONFIG_FILE, Command.INITSYNC);
            return false;
        }
        return true;
    }
}
