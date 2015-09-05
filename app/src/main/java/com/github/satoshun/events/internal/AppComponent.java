package com.github.satoshun.events.internal;

import com.github.satoshun.events.network.NetworkModule;
import com.github.satoshun.events.ui.EventFragment;
import com.github.satoshun.events.ui.MainActivity;
import com.github.satoshun.events.ui.domain.DomainModule;
import com.github.satoshun.events.ui.presenter.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        NetworkModule.class,
        PresenterModule.class,
        DomainModule.class
})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(EventFragment fragment);
}
