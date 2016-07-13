package me.academeg.androidmvp.api.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

@SuppressWarnings("unused")
public class Joke implements Serializable {

    private int id;
    private String joke;
    private String[] categories;

    public Joke() {
    }

    public static Joke parse(JSONObject obj) throws JSONException {
        Joke joke = new Joke();
        joke.id = obj.optInt("id");
        joke.joke = obj.optString("joke");
        JSONArray categories = obj.optJSONArray("categories");
        if (categories != null) {
            joke.categories = new String[categories.length()];
            for (int i = 0; i < categories.length(); i++) {
                joke.categories[i] = categories.optString(i);
            }
        }
        return joke;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", joke='" + joke + '\'' +
                ", categories=" + Arrays.toString(categories) +
                '}';
    }

    public int getId() {
        return id;
    }

    public String getJoke() {
        return joke;
    }

    public String[] getCategories() {
        return categories;
    }
}

