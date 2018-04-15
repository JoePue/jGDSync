package de.puettner.jgdsync.gdservice;

import java.text.MessageFormat;

public abstract class MessagePrinter {

    public static String out(String message, Object... args) {
        String msg = MessageFormat.format(message, args);
        System.out.println(msg);
        return msg;
    }

}
