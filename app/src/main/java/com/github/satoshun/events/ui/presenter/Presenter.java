package com.github.satoshun.events.ui.presenter;

public interface Presenter<T> {
    void clear();
    void setView(T view);
}
