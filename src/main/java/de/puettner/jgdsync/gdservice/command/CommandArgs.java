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

    public String getFirstParameter() {
        Optional<String> first = this.list.stream().skip(1).filter(item -> !item.startsWith("-")).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    boolean containsFlag(String argument) {
        return list.contains(argument);
    }
}
