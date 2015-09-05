package com.github.satoshun.events.internal;

import android.content.Context;

import com.github.satoshun.events.AndroidApplication;
import com.github.satoshun.events.model.EventDatabase;
import com.github.satoshun.events.model.ModelModule;
import com.github.satoshun.events.network.NetworkModule;
import com.github.satoshun.events.ui.EventFragment;
import com.github.satoshun.events.ui.SearchHistoryFragment;
import com.github.satoshun.events.ui.MainActivity;
import com.github.satoshun.events.ui.domain.DomainModule;
import com.github.satoshun.events.ui.presenter.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final AndroidApplication application;

    public AppModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    public Context provideContext() {
        return application;
    }
}
