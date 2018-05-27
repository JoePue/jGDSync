package de.puettner.jgdsync.exception;

public class AppUnexpectedException extends RuntimeException {
    public AppUnexpectedException(Exception e) {
        super(e);
    }

    public AppUnexpectedException(String m, Exception e) {
        super(m, e);
    }
}
