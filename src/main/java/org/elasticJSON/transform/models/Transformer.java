package org.elasticJSON.transform.models;

import org.json.JSONException;

import java.util.Map;

public interface Transformer {

    //Object transform(String val) throws JSONException;

    default public String sanitize(String val) {
        return val.trim();
    }


    Object transform( String val,String opsName, String[] miniKeys, int i,
                     Map<String, Object> ansMap,String[] transformations) throws JSONException;


    // Object transform(String val) throws JSONException;
}
