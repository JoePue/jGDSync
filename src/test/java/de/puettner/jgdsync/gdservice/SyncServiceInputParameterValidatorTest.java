package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.exception.UserInputException;
import org.junit.Test;

import static org.junit.Assert.*;

public class SyncServiceInputParameterValidatorTest {

    @Test
    public void validateGDFilePathWithRoot() {
        String[] actual = SyncServiceInputParameterValidator.validateGDFilePath("/");
        assertNotNull(actual);
        assertEquals(1, actual.length);
    }

    @Test(expected = UserInputException.class)
    public void validateGDFilePathWithEmptyString() {
        String[] actual = SyncServiceInputParameterValidator.validateGDFilePath("");
        assertNotNull(actual);
    }

    @Test
    public void validateGDFilePathWithValidPath() {
        String[] actual = SyncServiceInputParameterValidator.validateGDFilePath("/a/b");
        assertNotNull(actual);
        assertEquals(3, actual.length);
    }

    @Test
    public void validateGDFilePathWithValidPathAndTrailingSlash() {
        String[] actual = SyncServiceInputParameterValidator.validateGDFilePath("/a/b/");
        assertNotNull(actual);
        assertEquals(3, actual.length);
    }

    @Test(expected = UserInputException.class)
    public void validateGDFilePathWithInvalidPath() {
        SyncServiceInputParameterValidator.validateGDFilePath("/a//b/");
    }
}
