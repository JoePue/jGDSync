package de.puettner.jgdsync.gdservice.command;

public class CommandArgs {

    private String[] args;

    public CommandArgs(String[] args) {
        if (args == null) {
            this.args = new String[0];
        }
        this.args = args;
    }

    public String getFirstArgument() {
        if (args.length > 1) {
            return args[1];
        }
        return null;
    }

}
