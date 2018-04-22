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

    public CommandResult execute(CommandArgs args) {
        Node<SyncNode> result;
        if (args.getFirstArgument() == null) {
            result = service.listRootFolder();
        } else {
            if ("/".equals(args.getFirstArgument())) {
                result = service.listRootFolder();
            } else {
                result = service.listFolderByName(args.getFirstArgument());
            }
        }
        consolePrinter.printFileList(result);
        return CommandResult.builder().processed(true).successful(true).build();
    }
}
