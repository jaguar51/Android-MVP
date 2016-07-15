package me.academeg.androidmvp.ui.component;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

@SuppressWarnings("unused")
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int currentPage = 1;
    private int previousTotalItemCount = 0;
    private boolean loading = true;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager mLinearLayoutManager) {
        this.mLinearLayoutManager = mLinearLayoutManager;
    }

    public EndlessRecyclerOnScrollListener(LinearLayoutManager mLinearLayoutManager,
                                           int visibleThreshold) {
        this.mLinearLayoutManager = mLinearLayoutManager;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = mLinearLayoutManager.getItemCount();
        int firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = 0;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false;
            previousTotalItemCount = totalItemCount;
            currentPage++;
        }

        if (!loading && firstVisibleItem + visibleThreshold + visibleItemCount > totalItemCount) {
            onLoadMore(currentPage + 1, totalItemCount);
            loading = true;
        }
    }

    public abstract void onLoadMore(int page, int itemCount);
}
