package de.puettner.jgdsync.gdservice;

import com.google.common.io.Resources;
import de.puettner.jgdsync.model.GDErrorResponse;
import org.apache.commons.io.Charsets;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.*;

public class JacksonFactoryUtilTest {

    @Test
    public void parseGDErrorResponse() throws IOException {
        URL url = Resources.getResource("gdErrorResponse.json");
        String text = Resources.toString(url, Charsets.UTF_8);
        GDErrorResponse response = JacksonFactoryUtil.parseGDErrorResponse(text);
        assertNotNull(response);
        assertNotNull(response.getError().getMessage());
        assertNotNull(response.getError().getErrors().get(0));
    }
}
