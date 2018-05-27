package de.puettner.jgdsync.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

@Data
public class GDErrorResponse extends GenericJson {
    @Key
    private GDErrorType error;
}
