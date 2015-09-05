package com.github.satoshun.events.ui.presenter;

import java.util.List;

public interface KeywordPresenter extends Presenter<KeywordPresenter.KeywordView> {

    void initialize();
    void inputKeyword(String keyword);
    void onActionButtonClicked();
    void onKeywordClicked(String keyword);

    interface KeywordView {
        void renderKeywords(List<String> keywords);
        void showRegisterDialog();
    }
}
