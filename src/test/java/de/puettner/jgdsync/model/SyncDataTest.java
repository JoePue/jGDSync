package de.puettner.jgdsync.model;

import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;


public class SyncDataTest {

    @Test
    public void isEqualTo(){
        SyncData syncData1 = new SyncData("absoluteName", false, null, new File("C:\\test"), false);
        SyncData syncData2 = new SyncData("absoluteName", false, null, new File("C:\\test"), false);
        assertThat(syncData1).isEqualTo(syncData2);
    }

    @Test
    public void isNotEqualTo(){
        SyncData syncData1 = new SyncData("absoluteName", false, null, new File("C:\\test"), false);
        SyncData syncData2 = new SyncData("absoluteName2", false, null, new File("C:\\test"), false);
        assertThat(syncData1).isNotEqualTo(syncData2);
    }
}
