package org.elasticJSON.transform.models;

import org.json.JSONException;

import java.util.Map;

/**
 * **BOOL** represents the `Boolean` data type.
 *     - It stores a `Boolean` value.
 *     - **Transformation criteria**.
 *         - **Must** be transformed to the `Boolean` data type.
 *         - **Must** transform `1`, `t`, `T`, `TRUE`, `true`, or `True` to `true`.
 *         - **Must** transform `0`, `f`, `F`, `FALSE`, `false`, or `False` to `false`.
 *         - **Must** sanitize the value of trailing and leading whitespace before processing.
 *         - **Must** omit fields with invalid `Boolean` values.
 */
public class BooleanTransform implements Transformer{



    @Override
    public Object transform(String val,String opsName,  String[] miniKeys, int i,
                            Map<String, Object> ansMap, String[] transformations) throws JSONException {

        if(!val.trim().isEmpty()){
            val = sanitize(val);
        }
        Boolean ans = null;

        switch (val) {
            case "1" :
            case "t":
            case "T":
            case "TRUE":
            case "true":
            case "True":
                ans = true;
                break;

            case "0" :
            case "f":
            case "F":
            case "FALSE":
            case "false":
            case "False":
                ans = false;
                break;

            default:
                ans = null;
                break;


        }

        return ans;
    }

}
