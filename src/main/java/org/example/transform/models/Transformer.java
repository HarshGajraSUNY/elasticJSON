package org.example.transform.models;

import org.json.JSONException;
import org.json.JSONObject;

public interface Transformer {

    Object transform(String object) throws JSONException;

    default public String sanitize(String val) {
        return val.trim();
    }


}
