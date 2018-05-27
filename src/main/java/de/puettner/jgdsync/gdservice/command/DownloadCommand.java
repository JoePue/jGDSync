package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.exception.UserInputException;
import de.puettner.jgdsync.gdservice.SyncService;
import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.puettner.jgdsync.gdservice.command.CommandResult.PROCESS_ERROR;
import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;
import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.error;

@Log
public class DownloadCommand implements Command {

    public static final String OVERWRITE_FLAG = "-o";
    private final ConsolePrinter consolePrinter;
    private final SyncService service;

    public DownloadCommand(ConsolePrinter consolePrinter, SyncService service) {
        this.consolePrinter = consolePrinter;
        this.service = service;
    }

    public CommandResult execute(CommandArgs args) {
        try {
            String source = args.getFirstParameter().get();
            if (isPath(source)) {
                service.findFileByPath(source);
            }
            service.downloadFileById(source, args.getSecondParameter().orElseGet(() -> null), args.containsFlag(OVERWRITE_FLAG));
        } catch (UserInputException e) {
            error(e.getMessage());
            return PROCESS_ERROR;
        }
        return SUCCESS;
    }

    private boolean isPath(String source) {
        return source.contains("/");
    }

    @Override
    public String getCommandExplanation() {
        return "Download a file";
    }

    @Override
    public List<String> getUsageInfo() {
        List<String> list = new ArrayList<>();
        list.add(PROGRAMM_NAME + " " + this.getCommandName());
        list.add("[Flags]");
        list.add(OVERWRITE_FLAG + " : overwrite an existing local file");
        return list;
    }

    @Override
    public String getCommandName() {
        return DOWNLOAD;
    }

    private boolean validate(Optional<String> firstParameter, Optional<String> secondParameter) {
        if (!firstParameter.isPresent()) {
            error("first parameter is missing");
        }
        return true;
    }
}
