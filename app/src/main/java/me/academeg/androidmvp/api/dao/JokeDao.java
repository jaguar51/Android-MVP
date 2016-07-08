package me.academeg.androidmvp.api.dao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import me.academeg.androidmvp.api.dataSet.Joke;
import me.academeg.androidmvp.api.exception.InternetException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeDao {

    public ArrayList<Joke> getRandomJokes(int count) throws InternetException {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = getUrl()
                .addPathSegment("random")
                .addPathSegment(String.valueOf(count))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        ArrayList<Joke> res;
        try {
            Response response = client.newCall(request).execute();
            JSONArray array = new JSONObject(response.body().string()).getJSONArray("value");
            res = new ArrayList<>(array.length());
            for (int i = 0; i < array.length(); i++) {
                res.add(Joke.parse(array.getJSONObject(i)));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            throw new InternetException(e.getMessage());
        }
        return res;
    }

    private HttpUrl.Builder getUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api.icndb.com")
                .addPathSegment("jokes");
    }
}
