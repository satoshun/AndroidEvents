package com.github.satoshun.events.ui.domain;

import android.text.TextUtils;

import com.github.satoshun.events.domain.Events;
import com.github.satoshun.events.network.Atnd;
import com.github.satoshun.events.network.Connpass;
import com.github.satoshun.events.network.Zusaar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import static com.github.satoshun.events.domain.Events.Event;

public class EventInteractor {

    private static final SimpleDateFormat YMD_FORMAT = new SimpleDateFormat("yyyyMMdd");

    Connpass connpass;
    Atnd atnd;
    Zusaar zusaar;

    @Inject
    public EventInteractor(Connpass connpass, Atnd atnd, Zusaar zusaar) {
        this.connpass = connpass;
        this.atnd = atnd;
        this.zusaar = zusaar;
    }

    public Observable<List<Event>> search() {
        return commonPerform(Observable.merge(
                connpass.search(generateYmd()),
                atnd.search(generateYmd()),
                zusaar.search(generateYmd())));
    }

    public Observable<List<Event>> search(String keyword) {
        if (keyword == null || TextUtils.isEmpty(keyword)) return search();
        return commonPerform(Observable.merge(
                connpass.search(keyword, generateYmd()),
                atnd.search(keyword, generateYmd()),
                zusaar.search(keyword, generateYmd())));
    }

    private Observable<List<Event>> commonPerform(Observable<Events> events) {
        return events
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .reduce(new ArrayList<Event>(), new Func2<List<Event>, Events, List<Event>>() {
                    @Override
                    public List<Event> call(List<Event> events1, Events events2) {
                        events1.addAll(validEvent(events2.getEvents()));
                        return events1;
                    }
                });
    }

    private List<Event> validEvent(List<? extends Event> events) {
        return Observable.from(events)
                .filter(new Func1<Event, Boolean>() {
                    @Override
                    public Boolean call(Event event) {
                        return event.isValid();
                    }
                }).toList().toBlocking().first();
    }

    private List<String> generateYmd() {
        List<String> ymds = new ArrayList<>();
        Calendar instance = Calendar.getInstance();
        for (int i = 0; i < 10; i++) {
            ymds.add(YMD_FORMAT.format(instance.getTime()));
            instance.add(Calendar.DATE, 1);
        }
        return ymds;
    }
}
