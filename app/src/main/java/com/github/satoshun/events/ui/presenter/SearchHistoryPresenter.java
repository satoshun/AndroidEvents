package com.github.satoshun.events.ui.presenter;

import java.util.List;

public interface SearchHistoryPresenter extends Presenter<SearchHistoryPresenter.SearchHistoryView> {

    void initialize();
    void onHistoryClicked(String word);

    interface SearchHistoryView {
        void renderHistoryWords(List<String> words);
        void showEventFragment(String word);
    }
}
