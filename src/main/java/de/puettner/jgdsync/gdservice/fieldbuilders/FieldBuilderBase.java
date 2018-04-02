package de.puettner.jgdsync.gdservice.fieldbuilders;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by joerg.puettner on 02.04.2018.
 */
public abstract class FieldBuilderBase<T> {

    protected Set<T> fieldList = new HashSet<>();

    public String any() {
        fieldList.add(getAny());
        return build();
    }

    public String build() {
        if (fieldList.contains(getAny())) {
            return "*";
        }
        StringBuilder sb = new StringBuilder();
        for (T field : fieldList) {
            sb.append(",").append(field.toString());
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(0);
        }

        return sb.toString();
    }

    protected abstract T getAny();

}
