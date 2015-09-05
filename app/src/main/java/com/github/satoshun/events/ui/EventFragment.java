package com.github.satoshun.events.ui;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.AdapterView;

import com.github.satoshun.events.R;
import com.github.satoshun.events.databinding.FragmentEventBinding;
import com.github.satoshun.events.ui.presenter.EventPresenter;
import com.github.satoshun.events.widget.EventAdapter;

import java.util.List;

import javax.inject.Inject;

import static com.github.satoshun.events.domain.Events.Event;

public class EventFragment extends BaseFragment implements EventPresenter.EventView {

    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
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
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (eventAdapter.isEmpty()) {
            eventPresenter.initialize();
            binding.refresh.post(new Runnable() {
                @Override
                public void run() {
                    showRefreshDialog();
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
       inflater.inflate(R.menu.main, menu);
       MenuItem item = menu.findItem(R.id.action_search);
       initSearchView(item);
    }

    private void initSearchView(MenuItem item) {
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
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
}
