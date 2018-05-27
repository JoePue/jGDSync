package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import java.util.List;
import java.util.function.BiPredicate;

import static de.puettner.jgdsync.gdservice.command.CommandResult.SUCCESS;
import static de.puettner.jgdsync.gdservice.console.ConsolePrinter.println;
import static java.text.MessageFormat.format;

@Log
public class HelpCommand implements Command {

    private static final BiPredicate<? super Command, ? super CommandArgs> startsWithPredicate = (cmd, args) -> {
        if (args.getFirstParameter().isPresent()) {
            if (cmd.getCommandName().startsWith(args.getFirstParameter().get())) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    };
    private final ConsolePrinter consolePrinter;
    private final List<Command> commandList;

    public HelpCommand(ConsolePrinter consolePrinter, List<Command> commandList) {
        this.consolePrinter = consolePrinter;
        this.commandList = commandList;
    }

    public CommandResult execute(CommandArgs args) {
        println("Command line list:");
        commandList.stream().filter(cmd -> startsWithPredicate.test(cmd, args)).forEach(cmd -> {
            println(cmd.getCommandName());
            println(format("\t{0}", cmd.getCommandExplanation()));
            println(format("\tUsage: {0}", cmd.getUsageInfo()));
            println("");
        });
        println("");
        return SUCCESS;
    }

    @Override
    public String getCommandExplanation() {
        return "Prints this helpful information.";
    }

    @Override
    public String getUsageInfo() {
        return PROGRAMM_NAME + " " + this.getCommandName() + "";
    }

    public String getCommandName() {
        return HELP;
    }

    @Override
    public boolean isExecutable(CommandArgs arguments) {
        String cmd = arguments.getCommand().toLowerCase().trim();
        return this.getCommandName().equals(cmd) || "?".equals(cmd) || "-?".equals(cmd) || "/?".equals(cmd) || "-h".equals(cmd);
    }
}
