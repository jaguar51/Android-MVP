package me.academeg.androidmvp;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import me.academeg.androidmvp.presenter.base.BasePresenter;

public class PresenterManager {

    private static final String SIS_KEY_PRESENTER_ID = "presenter_id";

    private static PresenterManager instance = new PresenterManager();

    private final Map<Long, BasePresenter<?>> presenters;
    private final AtomicLong currentId;

    private PresenterManager() {
        currentId = new AtomicLong();
        presenters = new HashMap<>();
    }

    public static PresenterManager getInstance() {
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <P extends BasePresenter<?>> P restorePresenter(Bundle savedInstanceState) {
        Long presenterId = savedInstanceState.getLong(SIS_KEY_PRESENTER_ID);
        P presenter = (P) presenters.get(presenterId);
        presenters.remove(presenterId);
        return presenter;
    }

    public void savePresenter(BasePresenter<?> presenter, Bundle outState) {
        long presenterId = currentId.incrementAndGet();
        presenters.put(presenterId, presenter);
        outState.putLong(SIS_KEY_PRESENTER_ID, presenterId);
    }
}
