package org.elasticJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonTransformEntry {

    JSONArray array ;

    JSONObject ansObj ;

    HashMap<String, String> parentChildMap;

    Map<String, Object> ansMap ;

    Map<String, Object> finalAnsMap ;


    public static void main(String[] args) throws JSONException, IOException {
        System.out.println("Transforming JSON......");


        //get input json
        //get input
       // String text = new String(Files.readAllBytes(Paths.get("/Users/hgajra/Downloads/input1.json")),
        //        StandardCharsets.UTF_8);
        InputStream is = JsonTransformEntry.class.getResourceAsStream("/input.json");
        String text = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        String output = new String(Files.readAllBytes(Paths.get("/Users/hgajra/Downloads/output.json")),
                StandardCharsets.UTF_8);

        //pass to transform
        JsonTransformEntry jst = new JsonTransformEntry();
        jst.transform(text);

    }

    public JSONObject transform(String inputJSON) throws JSONException {
        array = new JSONArray();
        ansObj = new JSONObject();
        parentChildMap = new HashMap<>();
        ansMap = new TreeMap<>();
        finalAnsMap = new TreeMap<>();
        JSONObject jsonObject = new JSONObject(inputJSON);

        //JSONArray jsonArray = new JSONArray(inputJSON);
        try {
            //processJsonObject(jsonObject, null);
            process(jsonObject,"");
            /*
            for (Map.Entry<String, String> entry : parentChildMap.entrySet()) {
                System.out.println("Child is " + entry.getKey() + " : Parent is " + entry.getValue());
            }
            */


            for (Map.Entry<String, Object> entry : ansMap.entrySet()) {
                System.out.println("ansMap key " + entry.getKey() + " : value is " + entry.getValue());
                if(!entry.getKey().contains(".")) {
                    finalAnsMap.put(entry.getKey(),entry.getValue());
                }
            }


            ansObj = new JSONObject(finalAnsMap);

            /*
            Iterator itr2 = ansObj.keys();

            while (itr2.hasNext()) {
                String mainKey = (String) itr2.next();
                Object val = ansObj.get(mainKey);
                if (val instanceof JSONObject) {
                    ArrayList<String> sortedList = new ArrayList<>();
                    JSONObject val2 = (JSONObject) val;
                    Iterator sortItr = val2.sortedKeys();
                    while (sortItr.hasNext()) {
                        sortedList.add((String) sortItr.next());
                    }
                    JSONObject sortedObject = new JSONObject();
                    Collections.sort(sortedList);
                    for(int i=0;i<sortedList.size();i++) {
                        String key = sortedList.get(i);
                        sortedObject.append(sortedList.get(i),val2.get(key));
                         //sortedObject.put(sortedList.get(i),val2.get(key));
                    }

                    ansObj.put(mainKey, sortedObject);
                } else {
                    continue;
                }
            }

            */
            array.put(finalAnsMap);
            System.out.println();
            System.out.println(array);

            //for (int i = 0; i < array.length(); i++) {
             //   System.out.println(array.get(i));
          //  }
            //System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.exit(1);
        }


        return null;
    }

    public void processJsonObject(JSONObject jsonObj, String parentKey) throws JSONException {

        Iterator iterator = jsonObj.keys();
        String key = null;

        while (iterator.hasNext()) {

            key = (String) iterator.next();
            if (key.isEmpty()) {
                continue;
            }
            parentChildMap.put(key, parentKey);

            Object val = jsonObj.get(key);
            if (val instanceof JSONObject) {
                if (key.equalsIgnoreCase("M") || key.equalsIgnoreCase("L") ||
                        key.equalsIgnoreCase("N") || key.equalsIgnoreCase("BOOL") ||
                        key.equalsIgnoreCase("S") || key.equalsIgnoreCase("NULL")) {

                    processJsonObject((JSONObject) val, parentKey.trim());
                } else {
                    processJsonObject((JSONObject) val, key.trim());
                }
            } else if (val instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(key);

                int i = 0;
                if (i < jsonArray.length()) do {
                    if (jsonArray.get(i).toString().equalsIgnoreCase("noop")) {
                        break;
                    }
                    Object innerJson = jsonArray.get(i);
                    processJsonObject((JSONObject) innerJson, parentKey.trim());
                    i++;
                } while (i < jsonArray.length());
            } else {
                //base case
                //System.out.println("ParentKey is " + parentKey + " Key is " + key + " : " + "Value is " + jsonObj.get(key));

                //build decision on type of key and apply the transformations here and save to newJsonObj
                //process val and apply all transformation rules. After that save it in parentKey in ansObj.

                //after that check if parentkey has parent and keep iterating till parent !=null and store create a jsonObject out of it
                //Object transformedObj = TransformUtility.process(key.trim(), val.toString(), null);
                String curr = null;
                String prev = null;
                Object transformedObj = new JSONObject();
                //traverse up from child to parent hierarchy and update objects.
                if (!Objects.isNull(null)) {
                    curr = parentChildMap.get(parentKey);
                    prev = key;

                    if (curr != null) {
                        while (curr != null) {
                            if (ansMap.containsKey(curr)) {
                                JSONObject obj = (JSONObject) ansMap.get(curr);

                                if (obj.has(prev)) {
                                    Object existingObject = obj.get(prev);

                                    if (existingObject instanceof JSONArray) {
                                        JSONArray jsonArray = (JSONArray) existingObject;
                                        jsonArray.put(transformedObj);
                                        obj.put(prev, jsonArray);
                                    } else {
                                        List<Object> arrayList = new ArrayList<>();
                                        arrayList.add(existingObject);
                                        arrayList.add(transformedObj);

                                        JSONArray jsonArray = new JSONArray(arrayList);
                                        obj.put(prev, jsonArray);
                                    }
                                } else {
                                    obj.put(prev, transformedObj);
                                }

                                transformedObj = obj;
                            } else {
                                JSONObject obj = new JSONObject();
                                obj.put(prev, transformedObj);
                                ansMap.put(curr, obj);
                                transformedObj = obj;
                            }
                            prev = curr;
                            curr = parentChildMap.get(curr);

                        }
                    } else {

                        ansMap.put(parentKey, transformedObj);
                    }

                }

            }

        }

    }

    public void process(JSONObject jsonObj,String parent) throws JSONException {
        Iterator iterator = jsonObj.keys();
        String key = null;

        while(iterator.hasNext()) {
            key = (String) iterator.next();
            if (key.isEmpty()) {
                continue;
            }
            Object val = jsonObj.get(key);


           // System.out.println("Key is " + key + " Val is " + val.toString());
            if(val instanceof JSONObject) {
                String childKey = parent.isEmpty() ? key : parent + "." + key;
                //ansMap.put(childKey,val);
                process((JSONObject) val,childKey);
            }
            else if(val instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(key);

                int i = 0;
                if (i < jsonArray.length()) do {
                    if (jsonArray.get(i).toString().equalsIgnoreCase("noop")) {
                        break;
                    }
                    Object innerJson = jsonArray.get(i);
                    String childKey = parent.isEmpty() ? key : parent + "." + key + "#" +i;
                   // ansMap.put(childKey,(JSONObject)innerJson);
                    process((JSONObject) innerJson,childKey);
                    i++;
                } while (i < jsonArray.length());
            }
            else {

                String childKey = parent.isEmpty() ? key : parent + "." + key;
                System.out.println("Base Key is " + childKey + " Val is " + val.toString());
                String[] transformations = childKey.trim().split("\\.");

                String[] miniKeys = new String[transformations.length];

                String prevs ="";

                for(int i=0;i<transformations.length;i++) {
                    if(transformations[i].contains("#")) {
                       transformations[i] = removeLastChar(transformations[i]);
                    }
                    if(i==0) {
                        miniKeys[0] = transformations[i].trim();
                    } else {
                        miniKeys[i] = prevs +"." + transformations[i].trim();
                    }
                    prevs = miniKeys[i].trim();
                }
                for(int i=transformations.length-1;i>=0;i--){
                  //  map_1.M.list_1.L#1.N
                    String opsName =transformations[i].trim();
                    if(opsName.contains("#")) {
                        opsName = removeLastChar(opsName);
                    }
                    if (opsName.equalsIgnoreCase("M") ||
                            opsName.equalsIgnoreCase("L") ||
                            opsName.equalsIgnoreCase("N") ||
                            opsName.equalsIgnoreCase("BOOL") ||
                            opsName.equalsIgnoreCase("S") ||
                            opsName.equalsIgnoreCase("NULL")) {

                        Object transformedObj = TransformUtility.process(val.toString(),opsName,
                                miniKeys,i,ansMap,transformations);
                        if(Objects.isNull(transformedObj)) {
                            break;
                        }
                        ansMap.put(miniKeys[i],transformedObj);

                    } else {

                            Object obj =  ansMap.get(miniKeys[i+1]) ;
                            if(Objects.isNull(obj)) {
                                break;
                            }
                            if (obj instanceof JSONObject) {
                                //JSONObject transformedObj = new JSONObject(obj);
                                ansMap.put(miniKeys[i],obj);
                            } else if(obj instanceof JSONArray){
                                JSONArray jsonArray = (JSONArray) obj;
                                //jsonArray.put(transformedObj);
                                ansMap.put(miniKeys[i],jsonArray);
                            } else {
                                ansMap.put(miniKeys[i],obj);
                            }

                    }

                }


            }
        }

    }

    public  static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

}