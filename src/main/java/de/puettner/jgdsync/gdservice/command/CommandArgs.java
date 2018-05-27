package de.puettner.jgdsync.gdservice.command;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class CommandArgs {

    private final List<String> list;
    private String[] args;

    public CommandArgs(String[] args) {
        if (args == null) {
            this.args = new String[0];
        }
        this.args = args;
        this.list = Arrays.asList(args);
    }

    public String getCommand() {
        return getParameter(0).get();
    }

    private List<String> getFlags() {
        return this.list.stream().skip(1).filter(item -> item.startsWith("-")).collect(Collectors.toList());
    }

    private Optional<String> getParameter(int position) {
        return this.list.stream().skip(position).filter(item -> !item.startsWith("-")).findFirst();
    }

    public Optional<String> getFirstParameter() {
        return getParameter(1);
    }

    public Optional<String> getSecondParameter() {
        return this.list.stream().skip(1).filter(item -> !item.startsWith("-")).skip(1).findFirst();
    }

    boolean containsFlag(String argument) {
        return list.contains(argument);
    }
}
