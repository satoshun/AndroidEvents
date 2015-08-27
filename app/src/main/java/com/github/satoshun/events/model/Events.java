package com.github.satoshun.events.model;


import java.util.List;

public interface Events {

    List<? extends Event> getEvents();

    interface Event {
        String title();
        String url();
    }
}

