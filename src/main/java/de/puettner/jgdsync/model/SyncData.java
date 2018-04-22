package de.puettner.jgdsync.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.extern.java.Log;

import java.io.File;

@Data
@Log
public class SyncData {

    @Setter(AccessLevel.PACKAGE)
    private String absoluteName;

    /** Flag to indicate if property was loaded */
    private boolean gdFileLoaded;

    @Setter(AccessLevel.PACKAGE)
    private GDFile gdFile;

    @Setter(AccessLevel.PACKAGE)
    private File file;

    private boolean inSync;

    SyncData(String absoluteName, boolean gdFileLoaded, GDFile gdFile, File file, boolean inSync) {
        this.absoluteName = absoluteName;
        this.gdFileLoaded = gdFileLoaded;
        this.gdFile = gdFile;
        this.file = file;
        this.inSync = inSync;
    }


    @Override
    public int hashCode(){
        return absoluteName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof SyncData)) {
            return false;
        }
        return this.absoluteName.equals(((SyncData) o).absoluteName);
    }

}
