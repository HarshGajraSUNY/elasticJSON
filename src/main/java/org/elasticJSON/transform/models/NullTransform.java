package org.elasticJSON.transform.models;

import org.json.JSONException;

import java.util.Map;

/**
 * **NULL** represents the `Null` data type.
 *     - It denotes if a value is supposed to be `Null` using a `Boolean` data type.
 *     - **Transformation criteria**.
 *         - **Must** represent a `null` literal when the value is `1`, `t`, `T`, `TRUE`, `true`, or `True`.
 *         - **Must** omit the field when the value is `0`, `f`, `F`, `FALSE`, `false`, or `False`.
 *         - **Must** sanitize the value of trailing and leading whitespace before processing.
 *         - **Must** omit fields with invalid `Boolean` values.
 */
public class NullTransform implements  Transformer{




    @Override
    public Object transform(String val,String opsName,  String[] miniKeys, int i,
                            Map<String, Object> ansMap,String[] transformations) throws JSONException {
        String ans =null;
        if(!val.trim().isEmpty()){
            val = sanitize(val);

            switch (val) {
                case "1" :
                case "t":
                case "T":
                case "TRUE":
                case "true":
                case "True":
                    ans = "null";
                    break;

                default:
                    ans = null;


            }
        }
        return ans;
    }
}
