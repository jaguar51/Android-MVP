package me.academeg.androidmvp.api.methods;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.academeg.androidmvp.api.ApiCallback;
import me.academeg.androidmvp.api.model.Joke;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JokeDao {

    private Handler handler;

    public JokeDao() {
        handler = new Handler(Looper.getMainLooper());
    }

    public void getRandomJokes(int count, final ApiCallback<List<Joke>> callback) {
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = getUrl()
                .addPathSegment("random")
                .addPathSegment(String.valueOf(count))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONArray array = new JSONObject(response.body().string()).getJSONArray("value");
                    final ArrayList<Joke> res = new ArrayList<>(array.length());
                    for (int i = 0; i < array.length(); i++) {
                        res.add(Joke.parse(array.getJSONObject(i)));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onResult(res);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private HttpUrl.Builder getUrl() {
        return new HttpUrl.Builder()
                .scheme("https")
                .host("api.icndb.com")
                .addPathSegment("jokes");
    }
}
