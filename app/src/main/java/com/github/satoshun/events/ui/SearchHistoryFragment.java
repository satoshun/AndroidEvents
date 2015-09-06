package com.github.satoshun.events.ui;

import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.satoshun.events.R;
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
        binding.list.setEmptyView(binding.empty);
        binding.list.setAdapter(SearchHistoryAdapter);
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String word = (String) parent.getItemAtPosition(position);
                presenter.onHistoryClicked(word);
            }
        });
        binding.list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String word = (String) parent.getItemAtPosition(position);
                presenter.onHistoryLongClicked(view, word);
                return true;
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

    @Override
    public void showPopupMenu(View anchor, final String keyword) {
        PopupMenu popupMenu = new PopupMenu(getContext(), anchor);
        popupMenu.getMenuInflater().inflate(R.menu.search_history, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        presenter.removeKeyword(keyword);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}
