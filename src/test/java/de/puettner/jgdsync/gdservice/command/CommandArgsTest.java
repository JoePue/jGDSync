package de.puettner.jgdsync.gdservice.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CommandArgsTest {

    @Test
    public void argsWithOneParamAndOneFlag() {
        CommandArgs args;
        String[] array;
        array = new String[]{"command", "-f", "p1"};
        args = new CommandArgs(array);

        assertArgsWithOneParamAndOneFlag(args);
        assertTrue(args.containsFlag("-f"));

        array = new String[]{"command", "p1", "-f"};
        args = new CommandArgs(array);
        assertArgsWithOneParamAndOneFlag(args);
    }

    private void assertArgsWithOneParamAndOneFlag(CommandArgs args) {
        assertEquals("command", args.getCommand());
        assertEquals("p1", args.getFirstParameter().get());
    }

    @Test
    public void argsWithTwoParamsAndOneFlag() {
        String[] array;
        array = new String[]{"command", "-f", "p1", "p2"};
        assertArgsWithTwoParamsAndOneFlag(array);

        array = new String[]{"command", "p1", "-f", "p2"};
        assertArgsWithTwoParamsAndOneFlag(array);

        array = new String[]{"command", "p1", "p2", "-f"};
        assertArgsWithTwoParamsAndOneFlag(array);

    }

    private void assertArgsWithTwoParamsAndOneFlag(String[] array) {
        CommandArgs args;
        args = new CommandArgs(array);
        assertArgsWithOneParamAndOneFlag(args);
        assertEquals("p2", args.getSecondParameter().get());
        assertTrue(args.containsFlag("-f"));
    }
}
