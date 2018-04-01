package de.puettner.jgdsync;

public class AppException extends RuntimeException {
    public AppException(Exception e) {
        super(e);
    }
}
