package com.github.satoshun.events.internal;

import com.github.satoshun.events.ui.EventFragment;
import com.github.satoshun.events.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NetworkModule.class
})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(EventFragment fragment);
}
