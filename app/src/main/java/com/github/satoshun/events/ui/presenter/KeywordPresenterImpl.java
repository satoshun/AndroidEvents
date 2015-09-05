package com.github.satoshun.events.ui.presenter;

import android.util.Log;

import com.github.satoshun.events.ui.domain.KeywordInteractor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observer;

import static com.github.satoshun.events.ui.presenter.KeywordPresenter.*;

public class KeywordPresenterImpl implements KeywordPresenter {

    private final static String TAG = "KeywordPresenterImpl";

    private final KeywordInteractor keywordInteractor;
    private KeywordView keywordView;

    @Inject
    public KeywordPresenterImpl(KeywordInteractor keywordInteractor) {
        this.keywordInteractor = keywordInteractor;
    }

    @Override
    public void clear() {
    }

    @Override
    public void setView(KeywordView view) {
        keywordView = view;
    }

    @Override
    public void initialize() {
        initializeKeywords();
    }

    public void onActionButtonClicked() {
        keywordView.showRegisterDialog();
    }

    @Override
    public void inputKeyword(String keyword) {
        keywordInteractor.registerKeyword(keyword);
        initializeKeywords();
    }

    @Override
    public void onKeywordClicked(String keyword) {
    }

    private void initializeKeywords() {
        keywordInteractor.getKeywords(new Observer<String>() {
            List<String> keywords = new ArrayList<>();

            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
                keywordView.renderKeywords(keywords);
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
