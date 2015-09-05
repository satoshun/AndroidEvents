package com.github.satoshun.events.ui.presenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.github.satoshun.events.domain.Events.Event;

import java.util.List;

public interface EventPresenter extends Presenter<EventPresenter.EventView> {

    void inputSearchText(@Nullable String keyword);
    void onItemClicked(Activity context, View view, Event event);
    void refresh();

    interface EventView {
        void renderEvents(List<Event> events);
        void startIntent(Intent intent);
        void startIntent(Intent intent, Bundle bundle);
        void showRefreshDialog();
        void hideRefreshDialog();
        void showErrorView();
        void showContentView();
        void setTitle(String keyword);
    }
}
