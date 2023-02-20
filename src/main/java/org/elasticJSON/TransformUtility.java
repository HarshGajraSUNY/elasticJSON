package org.elasticJSON;

import org.elasticJSON.transform.models.*;
import org.json.JSONException;

import java.util.Map;

public class TransformUtility {



    public static Object process(String val,String opsName,  String[] miniKeys, int i,Map<String, Object> ansMap,
                                 String[] transformations)
            throws JSONException {
        TransformerModelType modelType = TransformerModelType.findEnum(opsName);

        switch (modelType) {
            case BOOLEAN:
                BooleanTransform booleanTransform = new BooleanTransform();
                return booleanTransform.transform( val, null,   null,  -1,
                        null,null);
            //break;
            case STRING:
                StringTransform stringTransform = new StringTransform();
                return stringTransform.transform( val, null,   null,  -1,
                        null,null);

            case NUMBER:
                NumberTransform numberTransform = new NumberTransform();
                return numberTransform.transform( val, null,   null,  -1,
                        null,null);
            //break;

            case NULL:
                NullTransform nullTransform = new NullTransform();
                return nullTransform.transform( val, null,   null,  -1,
                        null,null);

            case LIST: //give out an updated LIST
                ListTransform lst = new ListTransform();
                return lst.transform( val, opsName,   miniKeys,  i,
                        ansMap,transformations);//lst.transform(val);
            case MAP:
                MapTransform mp = new MapTransform();
                return mp.transform( val, opsName,   miniKeys,  i,
                    ansMap,transformations);
            default:
                throw new RuntimeException("Invalid Type . No Transformer model found");

        }

    }
}
