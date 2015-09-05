package com.github.satoshun.events.task;

import com.github.satoshun.events.domain.Events;
import com.github.satoshun.events.ui.domain.EventInteractor;

import java.util.List;

import rx.Observer;

public class EventFetcher implements Runnable {

    private final String keyword;
    private final EventInteractor interactor;

    public EventFetcher(String keyword, EventInteractor interactor) {
        this.keyword = keyword;
        this.interactor = interactor;
    }

    @Override
    public void run() {
        interactor.search(keyword)
                .subscribe(new Observer<List<Events.Event>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(List<Events.Event> events) {
                    }
                });
    }
}
