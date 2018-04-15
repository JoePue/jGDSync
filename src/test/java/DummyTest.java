import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Key;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * https://developers.google.com/api-client-library/java/google-http-java-client/unit-testing
 */
public class DummyTest {

    @Test
    public void test2() throws IOException {
        TreeSet<String> tree = new TreeSet<>();
        TreeMap<Integer, String> tree2;
    }

    @Test
    public void test() throws IOException {
        JacksonFactory factory = JacksonFactory.getDefaultInstance();
        System.out.println(factory.toPrettyString(new User()));
    }

    /**
     * https://developers.google.com/api-client-library/java/google-http-java-client/json
     */
    @Data
    @NoArgsConstructor
    public static class User {
        @Key("testProp")
        public String test = "testval";
        @Key("name")
        private String name = "joe";
        @Key("surname")
        private String surname = "pue";
    }
}
