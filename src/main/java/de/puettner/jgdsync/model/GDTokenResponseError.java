package de.puettner.jgdsync.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class GDTokenResponseError extends GenericJson {
    @Key
    private String error;

    @Key("error_description")
    private String description;
}
