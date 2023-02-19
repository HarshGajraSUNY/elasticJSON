package org.example;

import org.example.transform.models.BooleanTransform;
import org.example.transform.models.NullTransform;
import org.example.transform.models.NumberTransform;
import org.example.transform.models.StringTransform;
import org.json.JSONException;
import org.json.JSONObject;

public class TransformUtility {

    public static Object process(String key,String val,String parentKey) throws JSONException {

        TransformerModelType modelType = TransformerModelType.findEnum(key);

        switch (modelType) {
            case BOOLEAN:
                BooleanTransform booleanTransform = new BooleanTransform();
                return booleanTransform.transform(val);
                //break;
            case STRING:
                StringTransform stringTransform = new StringTransform();
                return stringTransform.transform(val);

            case NUMBER:
                NumberTransform numberTransform = new NumberTransform();
                return numberTransform.transform(val);
                //break;

           case NULL:
               NullTransform nullTransform = new NullTransform();
               return nullTransform.transform(val);

            default:
               throw new RuntimeException("Invalid Type . No Transformer model found");

        }

    }

}
