package com.github.satoshun.events.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.text.TextUtils;
import android.view.View;

import com.github.satoshun.events.R;
import com.github.satoshun.events.domain.Events.Event;
import com.github.satoshun.events.ui.EventDetailActivity;
import com.github.satoshun.events.ui.domain.EventInteractor;
import com.github.satoshun.events.ui.domain.SearchHistoryInteractor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;

public class EventPresenterImpl implements EventPresenter, Observer<List<Event>> {

    private final static int DEFAULT_TITLE_ID = R.string.default_event_title;

    private final EventInteractor eventInteractor;
    private final SearchHistoryInteractor searchHistoryInteractor;
    private final CompositeSubscription composition = new CompositeSubscription();

    private EventView eventView;
    private String currentKeyword;

    @Inject
    public EventPresenterImpl(EventInteractor eventInteractor,
                              SearchHistoryInteractor searchHistoryInteractor) {
        this.eventInteractor = eventInteractor;
        this.searchHistoryInteractor = searchHistoryInteractor;
    }

    @Override
    public void inputSearchText(String keyword) {
        currentKeyword =  keyword.trim();
        search();
    }

    @Override
    public void onItemClicked(Activity context, View rootView, Event event) {
        Intent intent = EventDetailActivity.callingIntent(context, event);
        String transitionName = context.getString(R.string.event_transition);
        View titleView = rootView.findViewById(R.id.title);
        if (titleView == null) {
            eventView.startIntent(intent);
            return;
        }

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                titleView,
                transitionName);
        eventView.startIntent(intent, options.toBundle());
    }

    @Override
    public void refresh() {
        eventView.showRefreshDialog();
        search();
    }

    @Override
    public void clear() {
        composition.clear();
    }

    @Override
    public void setView(EventView view) {
        eventView = view;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        eventView.showErrorView();
    }

    @Override
    public void onNext(List<Event> events) {
        Collections.sort(events, STARTED_DESC);
        eventView.renderEvents(events);
        eventView.hideRefreshDialog();
        eventView.showContentView();
        eventView.clearCurrentFocus();

        if (TextUtils.isEmpty(currentKeyword)) {
            eventView.setTitle(DEFAULT_TITLE_ID);
        } else {
            eventView.setTitle(currentKeyword);
        }

        if (!TextUtils.isEmpty(currentKeyword)) {
            searchHistoryInteractor.registerKeyword(currentKeyword);
        }
    }

    private void search() {
        eventView.showRefreshDialog();
        composition.add(eventInteractor.search(currentKeyword)
                .subscribe(this));
    }

    private static final Comparator<Event> STARTED_DESC = new Comparator<Event>() {
        @Override
        public int compare(Event lhs, Event rhs) {
            if (lhs.startedAt().after(rhs.startedAt())) {
                return 1;
            }
            return -1;
        }
    };
}
