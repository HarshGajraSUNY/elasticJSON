package org.elasticJSON.transform.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class ListTransform implements  Transformer {

    @Override
    public Object transform(String val,String opsName,  String[] miniKeys, int i,
                            Map<String, Object> ansMap, String[] transformations) throws JSONException {
      try {
          JSONArray jsonArray =null;

          if(ansMap.containsKey(miniKeys[i])) {

              Object toBeTransformed = ansMap.get(miniKeys[i]);
              if (toBeTransformed instanceof JSONArray) {
                  jsonArray = (JSONArray) toBeTransformed;
                  jsonArray.put(ansMap.get(miniKeys[i+1]));
                  //ansMap.put(miniKeys[i],jsonArray);
              } else {
                  return null;
              }

          } else {
              List<Object> arrList = new ArrayList<>();
              arrList.add(ansMap.get(miniKeys[i+1]));
              jsonArray = new JSONArray(arrList);
              // ansMap.put(miniKeys[i],jsonArray);

          }
          return sort(jsonArray);
      } catch (ArrayIndexOutOfBoundsException | NullPointerException aex) {
          return null;
      }

    }

    public JSONArray sort(JSONArray jsonArr) throws JSONException {

        JSONArray sortedJsonArray = new JSONArray();

        List<Object> jsonValues = new ArrayList<Object>();
        for (int i = 0; i < jsonArr.length(); i++) {
            jsonValues.add(jsonArr.get(i));
        }
        Collections.sort( jsonValues, new Comparator<Object>() {

            @Override
            public int compare(Object a, Object b) {
                String valA =a.toString();
                String valB = b.toString();


                return valA.compareTo(valB);
                //if you want to change the sort order, simply use the following:
                //return -valA.compareTo(valB);
            }
        });

        for (int i = 0; i < jsonArr.length(); i++) {
            sortedJsonArray.put(jsonValues.get(i));
        }
        return sortedJsonArray;
    }
}




