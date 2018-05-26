package de.puettner.jgdsync.gdservice.command;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public Optional<String> getFirstParameter() {
        return getParameter(1);
    }

    private Optional<String> getParameter(int position) {
        return this.list.stream().skip(position).filter(item -> !item.startsWith("-")).findFirst();
    }

    public Optional<String> getSecondParameter() {
        return getParameter(2);
    }

    boolean containsFlag(String argument) {
        return list.contains(argument);
    }
}
