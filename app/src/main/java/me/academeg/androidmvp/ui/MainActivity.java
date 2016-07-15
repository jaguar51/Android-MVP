package me.academeg.androidmvp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import me.academeg.androidmvp.PresenterManager;
import me.academeg.androidmvp.R;
import me.academeg.androidmvp.adapter.JokesAdapter;
import me.academeg.androidmvp.api.model.Joke;
import me.academeg.androidmvp.presenter.MainPresenter;
import me.academeg.androidmvp.ui.component.EndlessRecyclerOnScrollListener;
import me.academeg.androidmvp.ui.component.SimpleDividerItemDecoration;

public class MainActivity extends AppCompatActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout refreshLayout;
    private ProgressBar progressBar;

    private JokesAdapter adapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new JokesAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration());
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int itemCount) {
                mainPresenter.getNextJokes();
            }
        });

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refreshLayout.setOnRefreshListener(this);

        progressBar = (ProgressBar) findViewById(R.id.pb_progress);

        instantiatePresenter(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        PresenterManager.getInstance().savePresenter(mainPresenter, outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainPresenter.attachView(this);
        mainPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainPresenter.detachView();
    }

    @Override
    public void onRefresh() {
        mainPresenter.getJokes();
    }

    private void instantiatePresenter(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mainPresenter = PresenterManager.getInstance().restorePresenter(savedInstanceState);
        }
        if (mainPresenter == null) {
            mainPresenter = new MainPresenter();
        }
    }

    public void addJokesToList(List<Joke> dataSet) {
        adapter.addDataSet(dataSet);
    }

    public void setJokesToList(List<Joke> dataSet) {
        adapter.setDataSet(dataSet);
    }

    public void showProgressBar(boolean isShow) {
        progressBar.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    public void hideRefresher() {
        refreshLayout.setRefreshing(false);
    }

    public void showRefresher() {
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(true);
            }
        });
    }
}
