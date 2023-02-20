package org.elasticJSON.transform.models;

import org.json.JSONException;

import java.time.Instant;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * **S** represents the `String` data type.
 *     - It stores a `String` value.
 *     - **Transformation criteria**.
 *         - **Must** transform value to the `String` data type.
 *         - **Must** sanitize the value of trailing and leading whitespace before processing.
 *         - **Must** transform `RFC3339` formatted `Strings` to `Unix Epoch` in `Numeric` data type.
 *         - **Must** omit fields with empty values.
 */
public class StringTransform implements Transformer{

    public static String rfc339_regex  = "^((?:(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2}(?:\\.\\d+)?))(Z|[\\+-]\\d{2}:\\d{2})?)$";
    public static Pattern rfc339_pattern;

    static {
        rfc339_pattern = Pattern.compile(rfc339_regex);
    }


    @Override
    public Object transform(String val,String opsName,  String[] miniKeys, int i,
                            Map<String, Object> ansMap, String[] transformations) throws JSONException {

        if(!val.trim().isEmpty()){
            val = sanitize(val);
            Matcher m = rfc339_pattern.matcher(val);
            if(m.matches()) {
                Long seconds = convertRFC339ToEpoch(val);
                return seconds;
            }
            return val;
        } else{
            return null;
        }

    }

    public Long convertRFC339ToEpoch(String val) {

        Instant instant = Instant.parse(val);
        long epochSeconds = instant.getEpochSecond();
        return Long.valueOf(epochSeconds);
    }


}
