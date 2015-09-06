package com.github.satoshun.events.persistence;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class PersistenceModule {

    @Singleton
    @Provides
    public EventDatabase provideEventDatabase(Context context) {
        return new EventDatabase(context);
    }
}
