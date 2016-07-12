package me.academeg.androidmvp.presenter;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;

import me.academeg.androidmvp.api.dao.JokeDao;
import me.academeg.androidmvp.api.dataSet.Joke;
import me.academeg.androidmvp.api.exception.InternetException;
import me.academeg.androidmvp.presenter.base.BasePresenter;
import me.academeg.androidmvp.ui.MainActivity;

public class MainPresenter extends BasePresenter<MainActivity> {

    private boolean isLoadingData = false;
    private boolean isFirstLoading = false;
    private ArrayList<Joke> jokesCache;

    public MainPresenter() {
    }

    @Override
    public void onStart() {
        getView().setJokesToList(jokesCache);
        if (isLoadingData) {
            getView().showProgressBar(true);
        }
        if (isFirstLoading) {
            getView().showRefresher();
        }
        if (!isFirstLoading && !isLoadingData && jokesCache == null) {
            getJokes();
        }
    }

    public void getJokes() {
        getView().showRefresher();
        isFirstLoading = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jokesCache = new JokeDao().getRandomJokes(5);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (isViewAttach()) {
                                getView().setJokesToList(jokesCache);
                                getView().showProgressBar(false);
                                getView().hideRefresher();
                                isFirstLoading = false;
                            }
                        }
                    });
                } catch (InternetException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void getNextJokes() {

    }
}
