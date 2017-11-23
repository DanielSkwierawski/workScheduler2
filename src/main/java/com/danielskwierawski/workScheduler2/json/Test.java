package com.danielskwierawski.workScheduler2.json;

import org.json.JSONObject;



public class Test {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("genre_id", 1);
        jsonObject.put("genre_id", 2);
        jsonObject.put("genre_id", 2);
        jsonObject.put("genre_parent_id", JSONObject.NULL);
        jsonObject.put("genre_title", "Internationalion");
        // use the accumulate function to add to an existing value. The value
        // will now be converted to a list
        jsonObject.accumulate("genre_title", "Pop");
        // append to the key
        jsonObject.append("genre_title", "slow");
        jsonObject.put("genre_handle", "International");
        jsonObject.put("genre_color", "#CC3300");

        // get the json array for a string
        System.out.println(jsonObject.getJSONArray("genre_title"));
        System.out.println(jsonObject.get("genre_title"));
        System.out.println(jsonObject.get("genre_id"));
        System.out.println(jsonObject.get("genre_parent_id"));
        System.out.println(jsonObject.get("genre_handle"));
        System.out.println(jsonObject.get("genre_color"));
        // prints ["International","Pop","slow"]

        // increment a number by 1
        jsonObject.increment("genre_id");

        // quote a string allowing the json to be delivered within html
        System.out.println(JSONObject.quote(jsonObject.toString()));
        System.out.println(jsonObject);

        String key = "genre_parent_id";
        if (jsonObject.has(key)) {
            System.out.println("yes, it contains: " + key);
        } else {
            System.out.println("no, it doesn't contain: " + key);
        }

        if (jsonObject.isNull(key)) {
            System.out.println("key: " + key + " is null");
        } else {
            System.out.println("key: " + key + " isn't null");
        }



        // prints
        // "{\"genre_color\":\"#CC3300\",\"genre_title\":[\"International\",\"Pop\",\"slow\"],
        // \"genre_handle\":\"International\",\"genre_parent_id\":null,\"genre_id\":2}"
    }
}
