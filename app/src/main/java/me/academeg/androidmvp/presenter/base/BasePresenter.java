package me.academeg.androidmvp.presenter.base;

public abstract class BasePresenter<T> {

    private T view;

    protected T getView() {
        return view;
    }

    public void attachView(T view) {
        this.view = view;
    }

    public boolean isViewAttach() {
        return view != null;
    }

    public void detachView() {
        this.view = null;
    }

    public abstract void onStart();
}
