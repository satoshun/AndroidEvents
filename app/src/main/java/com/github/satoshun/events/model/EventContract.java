package com.github.satoshun.events.model;


import android.provider.BaseColumns;

public class EventContract {
    public EventContract() {}

    public static abstract class EventEntry implements BaseColumns {
        public static final String TABLE_NAME = "event";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
    }

    public static final String EVENT_CREATE_SQL =
            "CREATE TABLE " + EventEntry.TABLE_NAME + " ( "
            + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + EventEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL,"
            + EventEntry.COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL"
            + ");";

    public static abstract class KeywordEntry implements BaseColumns {
        public static final String TABLE_NAME = "keyword";
        public static final String COLUMN_NAME_TITLE = "name";
    }

    public static final String KEYWORD_CREATE_SQL =
            "CREATE TABLE " + KeywordEntry.TABLE_NAME + " ( "
                    + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + KeywordEntry.COLUMN_NAME_TITLE + " VARCHAR(255) NOT NULL UNIQUE"
                    + ");";
}
