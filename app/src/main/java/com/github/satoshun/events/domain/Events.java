package com.github.satoshun.events.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface Events {

    List<? extends Event> getEvents();

    interface Event extends Serializable {
        String title();
        String description();
        String longDescription();
        String url();
        String address();
        int accepted();
        int limit();
        Date startedAt();
        Date endedAt();
        boolean isValid();
    }
}

