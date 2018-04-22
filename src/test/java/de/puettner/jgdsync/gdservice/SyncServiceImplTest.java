package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class SyncServiceImplTest {

    private SyncServiceImpl sut;

    @Before
    public void before() {
        AppConfig appConfig = Mockito.mock(AppConfig.class);
        SyncData syncData = Mockito.mock(SyncData.class);
        Node<SyncData> rootNode = new Node<SyncData>(syncData, true);
        DriveService driveService = Mockito.mock(DriveService.class);
        FileIgnoreFilter fileIgnoreFilter = Mockito.mock(FileIgnoreFilter.class);
        sut = new SyncServiceImpl(appConfig, rootNode, driveService, fileIgnoreFilter);
    }

    @Test
    @Ignore
    public void listFolderByNode() {
        assertNotNull(sut);
    }
}
