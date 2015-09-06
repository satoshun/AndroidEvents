package com.github.satoshun.events.internal;

import android.content.Context;

import com.github.satoshun.events.persistence.PersistenceModule;
import com.github.satoshun.events.network.NetworkModule;
import com.github.satoshun.events.ui.EventFragment;
import com.github.satoshun.events.ui.SearchHistoryFragment;
import com.github.satoshun.events.ui.MainActivity;
import com.github.satoshun.events.ui.domain.DomainModule;
import com.github.satoshun.events.ui.presenter.PresenterModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        NetworkModule.class,
        PresenterModule.class,
        DomainModule.class,
        PersistenceModule.class
})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(EventFragment fragment);
    void inject(SearchHistoryFragment fragment);

    Context context();
}
