package de.puettner.jgdsync.gdservice;

import de.puettner.jgdsync.gdservice.command.AppConfig;
import de.puettner.jgdsync.model.Node;
import de.puettner.jgdsync.model.SyncData;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.UUID;

import static junit.framework.TestCase.*;
import static org.mockito.ArgumentMatchers.*;

public class SyncServiceTest {

    private SyncService sut;
    private AppConfig appConfig = Mockito.mock(AppConfig.class);
    private SyncData syncData = Mockito.mock(SyncData.class);
    private Node<SyncData> rootNode = new Node<SyncData>(syncData, true);
    private DriveService driveService = Mockito.mock(DriveService.class);
    private FileIgnoreFilter fileIgnoreFilter = Mockito.mock(FileIgnoreFilter.class);
    private LocalFileService lclFileSrv = Mockito.mock(LocalFileService.class);

    @Before
    public void before() {
        sut = new SyncService(appConfig, rootNode, driveService, fileIgnoreFilter, lclFileSrv);
    }

    @Test
    public void downloadFileByIdWithRemoteFileNotFound() {
        String gdId = UUID.randomUUID().toString();
        Mockito.when(driveService.get(eq((gdId)), eq(0))).thenReturn(null);
        File actual = sut.downloadFileById(gdId, null, false);
        assertNull(actual);
    }

    @Test
    public void downloadFileByIdWithRemoteFileNotFound2() {
        String gdId = UUID.randomUUID().toString();
        Mockito.when(driveService.get(eq((gdId)), eq(0))).thenReturn(null);
        File actual = sut.downloadFileById(gdId, null, false);
        assertNull(actual);
    }
}
