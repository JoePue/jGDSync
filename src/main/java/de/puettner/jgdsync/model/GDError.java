package de.puettner.jgdsync.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class GDError extends GenericJson {
    @Key
    private String domain;
    @Key
    private String reason;
    @Key
    private String message;
    @Key
    private String locationType;
    @Key
    private String location;
}
