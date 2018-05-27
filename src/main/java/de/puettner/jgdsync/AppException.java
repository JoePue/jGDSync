package de.puettner.jgdsync;

public class AppException extends RuntimeException {
    public AppException(Exception e) {
        super(e);
    }

    public AppException(String m, Exception e) {
        super(m, e);
    }
}
