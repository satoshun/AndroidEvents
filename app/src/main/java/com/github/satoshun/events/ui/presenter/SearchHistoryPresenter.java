package com.github.satoshun.events.ui.presenter;

import android.view.View;

import java.util.List;

public interface SearchHistoryPresenter extends Presenter<SearchHistoryPresenter.SearchHistoryView> {

    void initialize();
    void removeKeyword(String keyword);
    void onHistoryClicked(String keyword);
    void onHistoryLongClicked(View target, String keyword);

    interface SearchHistoryView {
        void renderHistoryWords(List<String> words);
        void showEventFragment(String keyword);
        void showPopupMenu(View anchor, String keyword);
    }
}
