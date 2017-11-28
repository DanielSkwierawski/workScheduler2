package com.danielskwierawski.workScheduler2.json;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GenreBean implements JSONAware {
    int genre_id;
    int genre_parent_id;
    String genre_handle;
    String genre_title;
    String genre_color;

    @Override
    public String toJSONString() {
        Map<Object, Object> genreBeanJsonMap = new HashMap<Object, Object>();
        genreBeanJsonMap.put("genre_id", getGenre_id());
        genreBeanJsonMap.put("genre_parent_id", getGenre_parent_id());
        genreBeanJsonMap.put("genre_handle", getGenre_handle());
        genreBeanJsonMap.put("genre_title", getGenre_handle());
        genreBeanJsonMap.put("genre_color", getGenre_color());
        return JSONObject.toJSONString(genreBeanJsonMap);
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getGenre_parent_id() {
        return genre_parent_id;
    }

    public void setGenre_parent_id(int genre_parent_id) {
        this.genre_parent_id = genre_parent_id;
    }

    public String getGenre_handle() {
        return genre_handle;
    }

    public void setGenre_handle(String genre_handle) {
        this.genre_handle = genre_handle;
    }

    public String getGenre_title() {
        return genre_title;
    }

    public void setGenre_title(String genre_title) {
        this.genre_title = genre_title;
    }

    public String getGenre_color() {
        return genre_color;
    }

    public void setGenre_color(String genre_color) {
        this.genre_color = genre_color;
    }
}
