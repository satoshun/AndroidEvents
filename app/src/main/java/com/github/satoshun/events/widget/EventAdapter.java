package com.github.satoshun.events.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.satoshun.events.R;
import com.github.satoshun.events.model.Events.Event;

import java.util.Collections;
import java.util.List;

import rx.functions.Action1;

public class EventAdapter extends BaseAdapter implements Action1<List<? extends Event>> {

    private List<? extends Event> events = Collections.emptyList();
    private LayoutInflater inflater;

    public EventAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return events.size();
    }

    @Override
    public Event getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.adapter_event, parent, false);
        }

        Event event = getItem(position);
        ((TextView) view.findViewById(R.id.title)).setText(event.title());
        return view;
    }

    @Override
    public void call(List<? extends Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
