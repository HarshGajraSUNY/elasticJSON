package org.elasticJSON.transform.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class MapTransform implements Transformer{


    @Override
    public Object transform( String val,String opsName, String[] miniKeys, int i,
                            Map<String, Object> ansMap,String[] transformations) throws JSONException {

        try {
            JSONObject newTransform = null;

            if (ansMap.containsKey(miniKeys[i])) {

                Object toBeTransformed = ansMap.get(miniKeys[i]);

                if (toBeTransformed instanceof JSONObject) {//if its a map
                    //Object mapTransform = ansMap.get(miniKeys[i]);

                    newTransform = (JSONObject) toBeTransformed;
                    //newTransform.put(,transformedObj);
                    newTransform.put(transformations[i + 1], ansMap.get(miniKeys[i + 1]));
                    return newTransform;
                    // ansMap.put(miniKeys[i],newTransform);
                }
            } else {
                newTransform = new JSONObject();
                newTransform.put(transformations[i + 1], ansMap.get(miniKeys[i + 1]));
            }
            return newTransform;
        } catch (ArrayIndexOutOfBoundsException | NullPointerException aex) {
            return null;
        }
    }
}
