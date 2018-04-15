package de.puettner.jgdsync.gdservice.fieldbuilders;

import static de.puettner.jgdsync.gdservice.fieldbuilders.ListFieldsBuilder.ListField.any;

public class ListFieldsBuilder<ListField> extends FieldBuilderBase {


    private FilesFieldsBuilder filesFieldsBuilder;

    private ListFieldsBuilder() {}

    public static ListFieldsBuilder create() {
        return new ListFieldsBuilder();
    }

    @Override
    protected Object getAny() {
        return any;
    }

    @Override
    public String build() {
        StringBuilder fieldBuild = new StringBuilder(super.build());
        if (filesFieldsBuilder != null) {
            fieldBuild.append(",files(").append(filesFieldsBuilder.build()).append(")");
        }
        return fieldBuild.toString();
    }

    public ListFieldsBuilder add(FilesFieldsBuilder filesFieldsBuilder) {
        this.filesFieldsBuilder = filesFieldsBuilder;
        return this;
    }

    public ListFieldsBuilder add(ListField field) {
        fieldList.add(field);
        return this;
    }

    public enum ListField {
        any(""), kind(""), incompleteSearch("");

        public final String label;

        ListField(String label) {
            this.label = label;
        }
    }

}
