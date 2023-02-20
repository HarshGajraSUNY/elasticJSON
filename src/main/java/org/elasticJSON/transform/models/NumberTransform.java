package org.elasticJSON.transform.models;

import org.json.JSONException;

import java.util.Map;

/**
 * **N** represents the `Number` data type.
 *     - It stores any `Numeric` value (positive, negative, int, float, etc.).
 *     - **Transformation criteria**.
 *         - **Must** be transformed to the relevant `Numeric` data type.
 *         - **Must** sanitize the value of trailing and leading whitespace before processing.
 *         - **Must** strip the leading zeros.
 *         - **Must** omit fields with invalid `Numeric` values.
 */

public class NumberTransform implements  Transformer{


    @Override
    public Object transform(String val,String opsName,  String[] miniKeys, int i,
                            Map<String, Object> ansMap, String[] transformations) throws JSONException {

        try{
             val = sanitize(val);
             val = stripZeros(val);
            Double num = Double.parseDouble(val);
            return num;
        } catch (NumberFormatException ne) {
            return null;
        }

    }


    public String stripZeros(String val) {
        String ans = val.replaceFirst("^0+(?!$)", "");
        return ans;
    }
}
