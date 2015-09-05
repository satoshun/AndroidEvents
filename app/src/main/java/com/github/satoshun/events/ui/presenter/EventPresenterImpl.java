package com.github.satoshun.events.ui.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.github.satoshun.events.R;
import com.github.satoshun.events.domain.Events.Event;
import com.github.satoshun.events.ui.EventDetailActivity;
import com.github.satoshun.events.ui.domain.EventInteractor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;
import rx.subscriptions.CompositeSubscription;

public class EventPresenterImpl implements EventPresenter, Observer<List<Event>> {

    private EventInteractor eventInteractor;
    private EventView eventView;
    private CompositeSubscription composition = new CompositeSubscription();
    private String currentKeyword;

    @Inject
    public EventPresenterImpl(EventInteractor eventInteractor) {
        this.eventInteractor = eventInteractor;
    }

    @Override
    public void initialize() {
        currentKeyword = null;
        eventView.showRefreshDialog();
        composition.add(eventInteractor.search()
                .subscribe(this));
    }

    @Override
    public void inputSearchText(String keyword) {
        keyword = keyword.trim();
        currentKeyword = keyword;
        eventView.showRefreshDialog();
        composition.add(eventInteractor.search(keyword)
                .subscribe(this));
    }

    @Override
    public void onItemClicked(Activity context, View rootView, Event event) {
        Intent intent = EventDetailActivity.callingIntent(context, event);
        String transitionName = context.getString(R.string.event_transition);
        View titleView = rootView.findViewById(R.id.title);
        if (titleView == null) {
            eventView.startIntent(intent);
        }

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                titleView,
                transitionName);
        eventView.startIntent(intent, options.toBundle());
    }

    @Override
    public void refresh() {
        eventView.showRefreshDialog();
        inputSearchText(currentKeyword);
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
    }

    @Override
    public void onNext(List<Event> events) {
        Collections.sort(events, STARTED_DESC);
        eventView.renderEvents(events);
        eventView.hideRefreshDialog();
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
