package com.github.satoshun.events.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.satoshun.events.databinding.AdapterSearchHistoryBinding;
import com.github.satoshun.events.domain.Events.Event;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class SearchHistoryAdapter extends RxAdapter<String> {

    private LayoutInflater inflater;

    public SearchHistoryAdapter(Context context) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            AdapterSearchHistoryBinding binding = AdapterSearchHistoryBinding.inflate(inflater, parent, false);
            view = binding.getRoot();
            view.setTag(binding);
        }

        AdapterSearchHistoryBinding binding = (AdapterSearchHistoryBinding) view.getTag();
        String keyword = getItem(position);
        binding.setKeyword(keyword);

        return view;
    }
}
