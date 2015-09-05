package com.github.satoshun.events.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.github.satoshun.events.databinding.AdapterEventBinding;
import com.github.satoshun.events.domain.Events.Event;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import rx.functions.Action1;

public class EventAdapter extends BaseAdapter implements Action1<List<? extends Event>> {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

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
            AdapterEventBinding binding = AdapterEventBinding.inflate(inflater, parent, false);
            view = binding.getRoot();
            view.setTag(binding);
        }

        AdapterEventBinding binding = (AdapterEventBinding) view.getTag();
        Event event = getItem(position);
        binding.setEvent(event);
        binding.setDateFormat(DATE_FORMAT);

        return view;
    }

    @Override
    public void call(List<? extends Event> events) {
        this.events = events;
        notifyDataSetChanged();
    }
}
