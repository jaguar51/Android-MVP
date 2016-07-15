package me.academeg.androidmvp.presenter;

import java.util.List;

import me.academeg.androidmvp.api.ApiCallback;
import me.academeg.androidmvp.api.methods.JokeDao;
import me.academeg.androidmvp.api.model.Joke;
import me.academeg.androidmvp.presenter.base.BasePresenter;
import me.academeg.androidmvp.ui.MainActivity;

public class MainPresenter extends BasePresenter<MainActivity> {

    private boolean isLoadingData = false;
    private boolean isFirstLoading = false;
    private List<Joke> jokesCache;

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
        JokeDao jokeDao = new JokeDao();
        jokeDao.getRandomJokes(5, new ApiCallback<List<Joke>>() {
            @Override
            public void onResult(List<Joke> res) {
                jokesCache = res;
                getView().setJokesToList(jokesCache);
                getView().hideRefresher();
                isFirstLoading = false;
            }

            @Override
            public void onError() {
            }
        });
    }

    public void getNextJokes() {
        isLoadingData = true;
        JokeDao jokeDao = new JokeDao();
        jokeDao.getRandomJokes(5, new ApiCallback<List<Joke>>() {
            @Override
            public void onResult(List<Joke> res) {
                jokesCache.addAll(res);
                getView().setJokesToList(jokesCache);
                isLoadingData = false;
            }

            @Override
            public void onError() {
            }
        });
    }
}
