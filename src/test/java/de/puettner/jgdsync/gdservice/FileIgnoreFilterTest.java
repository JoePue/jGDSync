package de.puettner.jgdsync.gdservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class FileIgnoreFilterTest {

    private final File baseDir = new File("c:\\dir");

    @Test
    public void acceptWithEmptyIgnoreList() {
        FileIgnoreFilter sut = new FileIgnoreFilter(baseDir, Collections.EMPTY_LIST);
        String[] testFiles = {"test", "test.txt"};
        File file;

        for (String testFilename : testFiles) {
            file = new File(baseDir.getAbsolutePath() + File.separator + testFilename);
            log.info(file.toString());
            assertThat(sut.accept(file)).isTrue();
        }
    }

    @Test
    public void acceptWithIgnoreList() {
        FileIgnoreFilter sut = new FileIgnoreFilter(baseDir, Arrays.asList("/test", "subDir/"));
        String[] ignoredTestFiles = {"test", "test" + File.separator, "test2", "mydir/subDir/my.txt"};
        File file;

        for (String testFilename : ignoredTestFiles) {
            file = new File(baseDir.getAbsolutePath() + File.separator + testFilename);
            log.info(file.toString());
            assertThat(sut.accept(file)).isFalse();
        }

        String[] includedTestFiles = {"abc", "abc" + File.separator, "abc.txt", "myDir/c.txt"};
        for (String testFilename : includedTestFiles) {
            file = new File(baseDir.getAbsolutePath() + File.separator + testFilename);
            log.info(file.toString());
            assertThat(sut.accept(file)).isTrue();
        }
    }
}
