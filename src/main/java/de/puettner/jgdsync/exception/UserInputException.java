package de.puettner.jgdsync.exception;

import java.util.function.Predicate;

import static java.text.MessageFormat.format;

/**
 * Application Illegal State Exception
 */
public class UserInputException extends RuntimeException {

    public UserInputException(String m, Exception e) {
        super(m, e);
    }

    public static UserInputException constuctAppISException(Exception e, ErrorCode code, Object... args) {
        return new UserInputException(format(code.message, args), e);
    }

    public static UserInputException throwAppISException(Exception e, ErrorCode code, Object... args) {
        throw constuctAppISException(e, code, args);
    }

    public static UserInputException throwAppISException(Predicate p, Exception e, ErrorCode code, Object... args) {
        throw constuctAppISException(e, code, args);
    }

}
