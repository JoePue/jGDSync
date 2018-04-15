package de.puettner.jgdsync.gdservice.fieldbuilders;

import static de.puettner.jgdsync.gdservice.fieldbuilders.FilesFieldsBuilder.FileField.any;

public class FilesFieldsBuilder<FileField> extends FieldBuilderBase {

    private FilesFieldsBuilder() {}

    public static FilesFieldsBuilder create() {
        return new FilesFieldsBuilder();
    }

    @Override
    protected Object getAny() {
        return any;
    }

    public FilesFieldsBuilder add(FileField field) {
        fieldList.add(field);
        return this;
    }

    public enum FileField {
        any(""),
        id(""),
        kind(""),
        mimeType(""),
        name(""),
        parents(""),
        createdTime(""),
        modifiedTime("");
        public final String label;

        FileField(String label) {
            this.label = label;
        }
    }

}
