package de.puettner.jgdsync.gdservice.command;

import de.puettner.jgdsync.gdservice.console.ConsolePrinter;
import lombok.extern.java.Log;

import java.util.ArrayList;
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
        commandList.stream().filter(cmd -> cmd.displayHelp()).filter(cmd -> startsWithPredicate.test(cmd, args)).forEach(cmd -> {
            println(cmd.getCommandName());
            println(format("\t{0}", cmd.getCommandExplanation()));
            if (cmd.getUsageInfo() != null) {
                for (int i = 0; i < cmd.getUsageInfo().size(); ++i) {
                    if (i == 0) {
                        println(format("\tUsage: {0}", cmd.getUsageInfo().get(0)));
                    } else {
                        println(format("\t{0}", cmd.getUsageInfo().get(i)));
                    }
                }
            }
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
    public List<String> getUsageInfo() {
        List<String> list = new ArrayList<>();
        list.add(PROGRAMM_NAME + " " + this.getCommandName());
        return list;
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
