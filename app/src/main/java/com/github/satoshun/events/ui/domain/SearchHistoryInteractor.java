package com.github.satoshun.events.ui.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.github.satoshun.events.model.EventContract;
import com.github.satoshun.events.model.EventDatabase;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class SearchHistoryInteractor {

    private final static String TAG = "SearchHistoryInteractor";

    private final EventDatabase eventDatabase;

    @Inject
    public SearchHistoryInteractor(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
    }

    public void getKeywords(Observer<String> callback) {
        final SQLiteDatabase db = eventDatabase.getReadableDatabase();
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String tbName = EventContract.KeywordEntry.TABLE_NAME;
                Cursor cursor = db.rawQuery("SELECT * FROM " + tbName +
                                            " ORDER BY _id DESC", null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        int index = cursor.getColumnIndex("name");
                        String name = cursor.getString(index);
                        subscriber.onNext(name);
                        cursor.moveToNext();
                    }
                }
                subscriber.onCompleted();
            }})
        .subscribe(callback);
    }

    public void registerKeyword(String keyword) {
        final SQLiteDatabase db = eventDatabase.getWritableDatabase();
        String tbName = EventContract.KeywordEntry.TABLE_NAME;
        try {
            db.insert(tbName, null, createKeyword(keyword));
        } catch (SQLiteConstraintException ignored) {}
    }

    public void removeKeyword(String keyword) {
        final SQLiteDatabase db = eventDatabase.getWritableDatabase();
        String tbName = EventContract.KeywordEntry.TABLE_NAME;
        db.execSQL("DELETE FROM " + tbName +
                    " WHERE name = ?", new String[]{keyword});
    }

    private ContentValues createKeyword(String keyword) {
        ContentValues values = new ContentValues();
        values.put(EventContract.KeywordEntry.COLUMN_NAME_TITLE, keyword);
        return values;
    }
}
