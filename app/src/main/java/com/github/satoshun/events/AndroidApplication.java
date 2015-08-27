package com.github.satoshun.events;

import android.app.Application;

import com.github.satoshun.events.internal.AppComponent;
import com.github.satoshun.events.internal.DaggerAppComponent;

public class AndroidApplication extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppComponent getAppComponent() {
        if (component == null) {
            component = DaggerAppComponent.create();
        }
        return component;
    }
}
