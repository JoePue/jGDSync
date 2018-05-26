package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.SyncService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import lombok.extern.java.Log;

import java.util.Optional;

import static de.puettner.jgdsync.gdservice.MessagePrinter.out;
import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;

@Log
public class LsCommand implements Command {

    private final ConsolePrinter consolePrinter;
    private final SyncService service;

    public LsCommand(ConsolePrinter consolePrinter, SyncService service) {
        this.consolePrinter = consolePrinter;
        this.service = service;
    }

    public CommandResult execute(CommandArgs args) {
        Node<SyncData> result;
        boolean recursive = args.containsFlag("-r");
        Optional<String> firstParameter = args.getFirstParameter();

        log.info(out("execute() firstParameter: {0}, recursive: {1}", firstParameter, recursive));
        if (!firstParameter.isPresent()) {
            result = service.listRootFolder();
        } else {
            if ("/".equals(firstParameter)) {
                result = service.listRootFolder();
            } else {
                Node<SyncData> node = service.findFolderByName(firstParameter.get());
                if (node.getChildren().size() != 1) {
                    consolePrinter.printNodeList(node);
                    return CommandResult.builder().processed(true).successful(false).build();
                } else {
                    result = service.listFolderByNode(node.getChildren().get(0), recursive);
                }
            }
        }
        consolePrinter.printNodeList(result);
        return SUCCESS;
    }

    public String getCommandName() {
        return LS;
    }

    @Override
    public String getHelpInfo() {
        return "List files and folders of your google drive";
    }

    @Override
    public String getUsageInfo() {
        return PROGRAMM_NAME + " " + this.getCommandName() + "[DIR]";
    }
}
