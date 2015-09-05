package com.github.satoshun.events.ui.presenter;

import android.util.Log;

import com.github.satoshun.events.ui.domain.SearchHistoryInteractor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;

public class SearchHistoryPresenterImpl implements SearchHistoryPresenter {

    private final static String TAG = "SearchHistoryPresenterImpl";

    private final SearchHistoryInteractor searchHistoryInteractor;
    private SearchHistoryView searchHistoryView;

    @Inject
    public SearchHistoryPresenterImpl(SearchHistoryInteractor searchHistoryInteractor) {
        this.searchHistoryInteractor = searchHistoryInteractor;
    }

    @Override
    public void clear() {
    }

    @Override
    public void setView(SearchHistoryView view) {
        searchHistoryView = view;
    }

    @Override
    public void initialize() {
        initializeKeywords();
    }

    @Override
    public void onHistoryClicked(String word) {
        searchHistoryView.showEventFragment(word);
    }

    private void initializeKeywords() {
        searchHistoryInteractor.getKeywords(new Observer<String>() {
            List<String> keywords = new ArrayList<>();

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
                searchHistoryView.renderHistoryWords(keywords);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
                keywords.add(s);
            }
        });
    }
}
