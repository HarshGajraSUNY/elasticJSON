package org.example;

import java.util.HashMap;
import java.util.Map;

public enum TransformerModelType {

    BOOLEAN("BOOL"),
    STRING("S"),
    NULL("NULL"),
    NUMBER("N");

    public final String label;
    private static final Map<String, TransformerModelType> lookup ;
    TransformerModelType(String label) {
        this.label = label;
    }

    static {
        lookup = new HashMap<String, TransformerModelType>();

        for (TransformerModelType modelType : TransformerModelType.values()) {
            lookup.put(modelType.label, modelType);
        }
    }

    public static TransformerModelType findEnum(String value) {

        return lookup.get(value.toUpperCase());
    }
}
