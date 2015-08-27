package com.github.satoshun.events.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.github.satoshun.events.R;
import com.github.satoshun.events.databinding.FragmentEventBinding;
import com.github.satoshun.events.model.Events;
import com.github.satoshun.events.network.Atnd;
import com.github.satoshun.events.network.Connpass;
import com.github.satoshun.events.network.Zusaar;
import com.github.satoshun.events.widget.EventAdapter;

import org.abego.treelayout.internal.util.java.lang.string.StringUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.github.satoshun.events.model.Events.Event;

public class EventFragment extends BaseFragment {

    public static EventFragment newInstance() {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentEventBinding binding;
    private CompositeSubscription subscription = new CompositeSubscription();
    private EventAdapter eventAdapter;
    private String currentSearchKeyword;

    @Inject Connpass connpass;
    @Inject Atnd atnd;
    @Inject Zusaar zusaar;

    public EventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventBinding.inflate(inflater, container, false);
        appComponent().inject(this);
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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(event.url()));
                startActivity(intent);
            }
        });
        binding.refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                search(currentSearchKeyword);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        refresh(search());
    }

    @Override
    public void onPause() {
        super.onPause();

        subscription.clear();
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
               String keyword = query.trim();
               refresh(search(keyword));
               currentSearchKeyword = keyword;
               return true;
           }

           @Override
           public boolean onQueryTextChange(String newText) {
               return false;
           }
       });
    }

    private void refresh(Observable<Events> events) {
        subscription.add(events
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .reduce(new ArrayList<Event>(), new Func2<List<Event>, Events, List<Event>>() {
                    @Override
                    public List<Event> call(List<Event> events1, Events events2) {
                        events1.addAll(events2.getEvents());
                        return events1;
                    }
                })
                .subscribe(new Observer<List<Event>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError", e.toString());
                        binding.refresh.setRefreshing(false);
                    }

                    @Override
                    public void onNext(List<Event> events) {
                        eventAdapter.call(events);
                        binding.refresh.setRefreshing(false);
                    }
                }));
    }

    private Observable<Events> search() {
        return Observable.merge(
                connpass.search(),
                atnd.search(),
                zusaar.search());
    }

    private Observable<Events> search(String keyword) {
        if (keyword == null || TextUtils.isEmpty(keyword)) return search();
        return Observable.merge(
                connpass.search(keyword),
                atnd.search(keyword),
                zusaar.search(keyword));
    }
}
