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

    JSONArray array;

    JSONObject ansObj;

    HashMap<String, String> parentChildMap;

    Map<String, Object> ansMap;

    Map<String, Object> finalAnsMap;


    public static void main(String[] args) throws JSONException, IOException {
        System.out.println("Transforming JSON......");

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

        try {
            process(jsonObject, "");

            for (Map.Entry<String, Object> entry : ansMap.entrySet()) {
                if (!entry.getKey().contains(".")) {
                    finalAnsMap.put(entry.getKey(), entry.getValue());
                }
            }

            ansObj = new JSONObject(finalAnsMap);

            array.put(finalAnsMap);
            System.out.println();
            System.out.println(array);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ansObj;
    }


    public void process(JSONObject jsonObj, String parent) throws JSONException {
        Iterator iterator = jsonObj.keys();
        String key = null;

        while (iterator.hasNext()) {
            key = (String) iterator.next();
            if (key.isEmpty()) {
                continue;
            }
            Object val = jsonObj.get(key);

            if (val instanceof JSONObject) {
                String childKey = parent.isEmpty() ? key : parent + "." + key;

                process((JSONObject) val, childKey);
            } else if (val instanceof JSONArray) {
                JSONArray jsonArray = jsonObj.getJSONArray(key);

                int i = 0;
                if (i < jsonArray.length()) do {
                    if (jsonArray.get(i).toString().equalsIgnoreCase("noop")) {
                        break;
                    }
                    Object innerJson = jsonArray.get(i);
                    String childKey = parent.isEmpty() ? key : parent + "." + key + "#" + i;
                    process((JSONObject) innerJson, childKey);
                    i++;
                } while (i < jsonArray.length());
            } else {

                String childKey = parent.isEmpty() ? key : parent + "." + key;
                String[] transformations = childKey.trim().split("\\.");

                String[] miniKeys = new String[transformations.length];

                String prevs = "";

                for (int i = 0; i < transformations.length; i++) {
                    if (transformations[i].contains("#")) {
                        transformations[i] = removeLastChar(transformations[i]);
                    }
                    if (i == 0) {
                        miniKeys[0] = transformations[i].trim();
                    } else {
                        miniKeys[i] = prevs + "." + transformations[i].trim();
                    }
                    prevs = miniKeys[i].trim();
                }
                for (int i = transformations.length - 1; i >= 0; i--) {
                    //  map_1.M.list_1.L#1.N
                    String opsName = transformations[i].trim();
                    if (opsName.contains("#")) {
                        opsName = removeLastChar(opsName);
                    }
                    if (opsName.equalsIgnoreCase("M") ||
                            opsName.equalsIgnoreCase("L") ||
                            opsName.equalsIgnoreCase("N") ||
                            opsName.equalsIgnoreCase("BOOL") ||
                            opsName.equalsIgnoreCase("S") ||
                            opsName.equalsIgnoreCase("NULL")) {

                        Object transformedObj = TransformUtility.process(val.toString(), opsName,
                                miniKeys, i, ansMap, transformations);
                        if (Objects.isNull(transformedObj)) {
                            break;
                        }
                        ansMap.put(miniKeys[i], transformedObj);

                    } else {

                        Object obj = ansMap.get(miniKeys[i + 1]);
                        if (Objects.isNull(obj)) {
                            break;
                        }
                        if (obj instanceof JSONObject) {
                            //JSONObject transformedObj = new JSONObject(obj);
                            ansMap.put(miniKeys[i], obj);
                        } else if (obj instanceof JSONArray) {
                            JSONArray jsonArray = (JSONArray) obj;
                            //jsonArray.put(transformedObj);
                            ansMap.put(miniKeys[i], jsonArray);
                        } else {
                            ansMap.put(miniKeys[i], obj);
                        }

                    }

                }


            }
        }

    }

    public static String removeLastChar(String s) {
        return (s == null || s.length() == 0)
                ? null
                : (s.substring(0, s.length() - 1));
    }

}