package com.github.satoshun.events.network;


import com.github.satoshun.events.domain.Events;
import com.google.gson.annotations.SerializedName;

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
        @SerializedName("catch") public String _catch;
        public String event_url;
        public String hash_tag;
        public Date started_at;
        public Date ended_at;
        public String address;
        public String place;
        public int accepted;
        public int limit;
        public int waiting;
        public Date updated_at;

        @Override
        public String title() {
            return title;
        }

        @Override
        public String description() {
            return _catch;
        }

        @Override
        public String longDescription() {
            return description;
        }

        @Override
        public String url() {
            return event_url;
        }

        @Override
        public String address() {
            return address;
        }

        @Override
        public int accepted() {
            return accepted;
        }

        @Override
        public int limit() {
            return limit;
        }

        @Override
        public Date startedAt() {
            return started_at;
        }

        @Override
        public Date endedAt() {
            return ended_at;
        }

        @Override
        public boolean isValid() {
            return startedAt() != null
                    && title() != null
                    && url() != null;
        }

        @Override
        public Date updatedAt() {
            return updated_at;
        }
    }
}
