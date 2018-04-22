package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.DriveService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncNode;
import lombok.extern.java.Log;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;

@Log
public class LsCommand implements Command {

    private final ConsolePrinter consolePrinter;
    private final DriveService service;

    public LsCommand(ConsolePrinter consolePrinter, DriveService service) {
        this.consolePrinter = consolePrinter;
        this.service = service;
    }

    public CommandResult execute(CommandArgs args) {
        Node<SyncNode> result;
        boolean recursive = args.containsFlag("-r");
        String firstParameter = args.getFirstParameter();
        log.info(out("execute() firstParameter: {0}, recursive: {1}", firstParameter, recursive));
        if (firstParameter == null) {
            result = service.listRootFolder();
        } else {
            if ("/".equals(firstParameter)) {
                result = service.listRootFolder();
            } else {
                Node<SyncNode> node = service.findFolderByName(firstParameter);
                if (node.getChildren().size() != 1) {
                    consolePrinter.printNodeList(node);
                    return CommandResult.builder().processed(true).successful(false).build();
                } else {
                    result = service.listFolderByNode(node.getChildren().get(0), recursive);
                }
            }
        }
        consolePrinter.printNodeList(result);
        return CommandResult.builder().processed(true).successful(true).build();
    }
}
