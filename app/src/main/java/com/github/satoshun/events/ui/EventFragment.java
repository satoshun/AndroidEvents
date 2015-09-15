package com.github.satoshun.events.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.github.satoshun.events.R;
import com.github.satoshun.events.databinding.FragmentEventBinding;
import com.github.satoshun.events.ui.presenter.EventPresenter;
import com.github.satoshun.events.ui.adapter.EventAdapter;

import java.util.List;

import javax.inject.Inject;

import static com.github.satoshun.events.domain.Events.Event;

public class EventFragment extends BaseFragment implements EventPresenter.EventView {

    private static final String INTENT_SEARCH_WORD = "INTENT_SEARCH_WORD";

    public static EventFragment newInstance() {
        return newInstance(null);
    }

    public static EventFragment newInstance(String word) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putString(INTENT_SEARCH_WORD, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Inject EventPresenter eventPresenter;

    private FragmentEventBinding binding;
    private EventAdapter eventAdapter;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater, container, false);
        appComponent().inject(this);
        eventPresenter.setView(this);
        setHasOptionsMenu(true);

        return binding.rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventAdapter = new EventAdapter(getContext());
        binding.list.setAdapter(eventAdapter);
        binding.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = (Event) parent.getItemAtPosition(position);
                eventPresenter.onItemClicked(getActivity(), view, event);
            }
        });

        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventPresenter.refresh();
            }
        });

        binding.error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eventPresenter.refresh();
            }
        });

        String keyword = getArguments().getString(INTENT_SEARCH_WORD);
        eventPresenter.inputSearchText(keyword);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        initSearchView(item);
    }

    private void initSearchView(MenuItem item) {
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                eventPresenter.inputSearchText(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void renderEvents(List<Event> events) {
        eventAdapter.call(events);
    }

    @Override
    public void startIntent(Intent intent) {
        startActivity(intent);
    }

    @Override
    public void startIntent(Intent intent, Bundle bundle) {
        ActivityCompat.startActivity(getActivity(), intent, bundle);
    }

    @Override
    public void showRefreshDialog() {
        binding.refresh.setRefreshing(true);
    }

    @Override
    public void hideRefreshDialog() {
        binding.refresh.setRefreshing(false);
    }

    @Override
    public void showErrorView() {
        binding.error.setVisibility(View.VISIBLE);
        binding.refresh.setVisibility(View.GONE);
    }

    @Override
    public void showContentView() {
        binding.error.setVisibility(View.GONE);
        binding.refresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTitle(String keyword) {
        getActivity().setTitle(keyword);
    }

    @Override
    public void setTitle(@StringRes int id) {
        getActivity().setTitle(id);
    }

    @Override
    public void clearCurrentFocus() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
