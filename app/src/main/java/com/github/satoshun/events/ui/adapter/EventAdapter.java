package com.github.satoshun.events.ui.adapter;

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

public class EventAdapter extends RxAdapter<Event> {

    private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);

    private LayoutInflater inflater;

    public EventAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    }}
