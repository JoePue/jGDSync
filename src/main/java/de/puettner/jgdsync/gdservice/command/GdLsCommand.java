package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;

public class GdLsCommand implements Command {

    private final ConsolePrinter consolePrinter;
    private final DriveService service;

    public GdLsCommand(ConsolePrinter consolePrinter, DriveService service) {
        this.consolePrinter = consolePrinter;
        this.service = service;
    }

    public boolean execute() {
        Node<SyncNode> result = service.listRootFolder();
        consolePrinter.printFileList(result);
        return true;
    }
}
