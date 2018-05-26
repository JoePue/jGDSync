package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.SyncService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import lombok.extern.java.Log;

import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;

@Log
public class DiffCommand implements Command {

    private final ConsolePrinter consolePrinter;
    private final SyncService syncService;
    private final AppConfig appConfig;

    public DiffCommand(ConsolePrinter consolePrinter, SyncService service, AppConfig appConfig) {
        this.consolePrinter = consolePrinter;
        this.syncService = service;
        this.appConfig = appConfig;
    }

    public CommandResult execute(CommandArgs args) {
        CommandResult cmdResult = new CommandResult(true, true);
        String folderId = appConfig.getGdFolderId();
        log.info("folderId: " + folderId);
        Node<SyncData> rootNode = syncService.createRootSyncNode();
        if (rootNode == null) {
            consolePrinter.error("Base folder not found in gdrive. No folder has id=" + folderId);
            cmdResult.setSuccessful(false);
            return cmdResult;
        }
        diffFolder(rootNode);
        return SUCCESS;
    }

    @Override
    public String getCommandName() {
        return DIFF;
    }

    @Override
    public String getHelpInfo() {
        return "";
    }

    @Override
    public String getUsageInfo() {
        return PROGRAMM_NAME + " " + this.getCommandName() + "";
    }

    private void diffFolder(Node<SyncData> node) {
        SyncData syncData = node.getData();
        if (syncData.isGdFileLoaded()) {
            syncService.syncNodeChildren(node);
        }
        if (!(syncData.getFile().isDirectory() && syncData.getGdFile().isFolder())) {
            throw new IllegalStateException();
        }
    }
}
