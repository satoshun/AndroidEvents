package com.github.satoshun.events.network;


import com.github.satoshun.events.model.Events;

import java.util.Date;
import java.util.List;

/** Represent Connpass Response */
public class ConnpassResponse implements Events {
    public int results_returned;
    public int results_available;
    public int results_start;
    public List<ConpassEvent> events;

    @Override
    public List<? extends Event> getEvents() {
        return events;
    }

    public static class ConpassEvent implements Event {
        public int event_id;
        public String title;
        public String description;
        public String event_url;
        public String hash_tag;
        public Date started_at;
        public Date ended_at;
        public int limit;
        public String address;
        public String place;
        public int accepted;
        public int waiting;

        @Override
        public String title() {
            return title;
        }

        @Override
        public String url() {
            return event_url;
        }
    }
}