package com.github.satoshun.events.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.satoshun.events.databinding.FragmentSearchHistoryBinding;
import com.github.satoshun.events.ui.adapter.SearchHistoryAdapter;
import com.github.satoshun.events.ui.presenter.SearchHistoryPresenter;

import java.util.List;

import javax.inject.Inject;

public class SearchHistoryFragment extends BaseFragment implements SearchHistoryPresenter.SearchHistoryView {

    public static SearchHistoryFragment newInstance() {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Inject SearchHistoryPresenter presenter;

    private FragmentSearchHistoryBinding binding;
    private SearchHistoryAdapter SearchHistoryAdapter;

    public SearchHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchHistoryBinding.inflate(inflater, container, false);
        appComponent().inject(this);
        presenter.setView(this);
        setHasOptionsMenu(true);

        return binding.rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchHistoryAdapter = new SearchHistoryAdapter(getContext());
        binding.list.setAdapter(SearchHistoryAdapter);
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = (String) parent.getItemAtPosition(position);
                presenter.onHistoryClicked(word);
            }
        });
        presenter.initialize();
    }

    @Override
    public void renderHistoryWords(List<String> keywords) {
        SearchHistoryAdapter.call(keywords);
    }

    @Override
    public void showEventFragment(String word) {
        EventFragment fragment = EventFragment.newInstance(word);
        ((MainActivity) getActivity()).toFragment(fragment);
    }
}
