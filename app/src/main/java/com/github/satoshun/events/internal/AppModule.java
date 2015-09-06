package com.github.satoshun.events.internal;

import android.content.Context;

import com.github.satoshun.events.AndroidApplication;

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
