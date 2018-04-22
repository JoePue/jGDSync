package de.puettner.jgdsync.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.java.Log;

@Data
@Log
@AllArgsConstructor
public class SyncNode {

    /** Flag to indicate if property was loaded */
    private boolean gdFileLoaded;
    private GDFile gdFile;
    private java.io.File file;

}
