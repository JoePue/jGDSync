package de.puettner.jgdsync.model;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import lombok.Data;

import java.util.List;

@Data
public class GDErrorType extends GenericJson {
    @Key
    private int code;
    @Key
    private String message;
    @Key
    private List<GDError> errors;
}
