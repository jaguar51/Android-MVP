package me.academeg.androidmvp.api;

public interface ApiCallback<Result> {

    void onResult(Result res);

    void onError();
}
