package me.academeg.androidmvp.presenter;

import android.os.Handler;
import android.os.Looper;

import java.io.Serializable;
import java.util.ArrayList;

import me.academeg.androidmvp.api.dao.JokeDao;
import me.academeg.androidmvp.api.dataSet.Joke;
import me.academeg.androidmvp.api.exception.InternetException;
import me.academeg.androidmvp.presenter.base.BasePresenter;
import me.academeg.androidmvp.ui.MainActivity;

public class MainPresenter extends BasePresenter<MainActivity> implements Serializable {

    private MainActivity mainActivity;
    private boolean isLoadingData = false;
    private ArrayList<Joke> jokesCache;

    public MainPresenter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void getJokes() {
        isLoadingData = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ArrayList<Joke> randomJokes = new JokeDao().getRandomJokes(5);
                    jokesCache = randomJokes;
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            mainActivity.addJokesToList(randomJokes);
                        }
                    });
                } catch (InternetException e) {
                    e.printStackTrace();
                } finally {
                    isLoadingData = false;
                }
            }
        }).start();
    }

    @Override
    public void onStart() {
        if (!isLoadingData && jokesCache != null) {
            getView().setJokesToList(jokesCache);
        } else {
            getJokes();
        }
    }
}
