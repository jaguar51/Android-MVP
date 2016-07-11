package me.academeg.androidmvp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import me.academeg.androidmvp.R;
import me.academeg.androidmvp.adapter.JokesAdapter;
import me.academeg.androidmvp.api.dataSet.Joke;
import me.academeg.androidmvp.presenter.MainPresenter;

public class MainActivity extends AppCompatActivity {

    private SwipeRefreshLayout refreshLayout;
    private JokesAdapter adapter;
    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new JokesAdapter();
        recyclerView.setAdapter(adapter);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        refreshLayout.setEnabled(false);

        instantiatePresenter(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("main_presenter", mainPresenter);
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

    private void instantiatePresenter(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mainPresenter = (MainPresenter) savedInstanceState.getSerializable("main_presenter");
        }
        if (mainPresenter == null) {
            mainPresenter = new MainPresenter(this);
        }
    }

    public void addJokesToList(List<Joke> dataSet) {
        adapter.addDataSet(dataSet);
    }

    public void setJokesToList(List<Joke> dataSet) {
        adapter.setDataSet(dataSet);
    }
}
