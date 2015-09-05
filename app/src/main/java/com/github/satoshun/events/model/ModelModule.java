package com.github.satoshun.events.model;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ModelModule {

    @Singleton
    @Provides
    public EventDatabase provideEventDatabase(Context context) {
        return new EventDatabase(context);
    }
}
