package com.danielskwierawski.workScheduler2.json;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Test2 {

    public static void main(String[] args) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("genre_id", 1);
        jsonObject.put("genre_parent_id", null);
        jsonObject.put("genre_title", "International");
        jsonObject.put("genre_handle", "International");
        jsonObject.put("genre_color", "#CC3300");
        System.out.println(jsonObject.toJSONString());

        GenreBean bean = new GenreBean();
        bean.setGenre_title("International");
        bean.setGenre_color("#CC3300");
        bean.setGenre_handle("International");
        bean.setGenre_id(1);
        System.out.println(JSONValue.toJSONString(bean));

    }

}
