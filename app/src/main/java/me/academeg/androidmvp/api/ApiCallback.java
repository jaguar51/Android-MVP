package me.academeg.androidmvp.api;

//import okhttp3.Callback;

public interface ApiCallback<Result> {

    void onResult(Result res);

    void onError();
}
