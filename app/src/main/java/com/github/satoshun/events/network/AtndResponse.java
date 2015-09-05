package com.github.satoshun.events.network;


import com.github.satoshun.events.domain.Events;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/** Represent Atnd Response */
public class AtndResponse implements Events {
    public int results_returned;
    public int results_start;
    public List<AtndEventContainer> events;

    @Override
    public List<? extends Event> getEvents() {
        return events;
    }

    public static class AtndEventContainer implements Event {
        public AtndEvent event;

        @Override
        public String title() {
            return event.title;
        }

        @Override
        public String description() {
            return event._catch;
        }

        @Override
        public String longDescription() {
            return event.description;
        }

        @Override
        public String url() {
            return event.event_url;
        }

        @Override
        public String address() {
            return event.address;
        }

        @Override
        public int accepted() {
            return event.accepted;
        }

        @Override
        public int limit() {
            return event.limit;
        }

        @Override
        public Date startedAt() {
            return event.started_at;
        }

        @Override
        public Date endedAt() {
            return event.ended_at;
        }

        @Override
        public boolean isValid() {
            return startedAt() != null
                    && title() != null
                    && url() != null;
        }

        @Override
        public Date updatedAt() {
            return event.updated_at;
        }
    }

    public static class AtndEvent {
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
    }
}
