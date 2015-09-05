package com.github.satoshun.events.ui.presenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Singleton
    @Provides
    public EventPresenter provideEventPresenter(EventPresenterImpl eventPresenter) {
        return eventPresenter;
    }

    @Singleton
    @Provides
    public SearchHistoryPresenter provideSearchHistoryPresenter(SearchHistoryPresenterImpl searchHistoryPresenter) {
        return searchHistoryPresenter;
    }
}
