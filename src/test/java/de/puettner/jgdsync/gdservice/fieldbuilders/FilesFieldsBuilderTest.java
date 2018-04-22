package de.puettner.jgdsync.gdservice.fieldbuilders;

import org.junit.Test;

import static de.puettner.jgdsync.gdservice.fieldbuilders.FilesFieldsBuilder.FileField.*;
import static de.puettner.jgdsync.gdservice.fieldbuilders.ListFieldsBuilder.ListField.incompleteSearch;
import static de.puettner.jgdsync.gdservice.fieldbuilders.ListFieldsBuilder.ListField.kind;
import static org.junit.Assert.assertEquals;

public class FilesFieldsBuilderTest {

    @Test
    public void empty() {
        assertEquals(ListFieldsBuilder.create().build(), "");
    }

    @Test
    public void any() {
        assertEquals("*", ListFieldsBuilder.create().any());
    }

    @Test
    public void kindAndIncompleteSearchAndEmptyFiles() {
        assertEquals("kind,incompleteSearch,files()", ListFieldsBuilder.create().add(kind).add(incompleteSearch).add(FilesFieldsBuilder
                .create()).build());
    }

    @Test
    public void kindAndIncompleteSearchAndFiles() {
        assertEquals("kind,incompleteSearch,files(id)", ListFieldsBuilder.create().add(kind).add(incompleteSearch).add(FilesFieldsBuilder
                .create().add(id)).build());
    }

    @Test
    public void kindAndIncompleteSearchAnyFiles() {
        assertEquals("kind,incompleteSearch,files(*)", ListFieldsBuilder.create().add(kind).add(incompleteSearch).add(FilesFieldsBuilder
                .create().add(any)).build());
    }

    @Test
    public void kindAndIncompleteSearchAnyFilesWithMimeTypeAndId() {
        assertEquals("kind,incompleteSearch,files(id,mimeType)", ListFieldsBuilder.create().add(kind).add(incompleteSearch).add
                (FilesFieldsBuilder.create().add(id).add(mimeType)).build());
    }

}
