package com.github.satoshun.events.ui.adapter;

import android.widget.BaseAdapter;

import com.github.satoshun.events.domain.Events.Event;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

abstract class RxAdapter<T> extends BaseAdapter implements Action1<List<T>> {

    protected List<T> events = Collections.emptyList();

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public T getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void call(List<T> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
