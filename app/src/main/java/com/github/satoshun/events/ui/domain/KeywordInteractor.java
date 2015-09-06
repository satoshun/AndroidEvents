package com.github.satoshun.events.ui.domain;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.satoshun.events.persistence.EventContract;
import com.github.satoshun.events.persistence.EventDatabase;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Func1;

public class KeywordInteractor {

    private final static String TAG = "KeywordInteractor";

    private final EventDatabase eventDatabase;
    private final SqlBrite sqlBrite;

    @Inject
    public KeywordInteractor(EventDatabase eventDatabase) {
        this.eventDatabase = eventDatabase;
        sqlBrite = SqlBrite.create();
    }

    public void getKeywords(Observer<String> callback) {
        final SQLiteDatabase db = eventDatabase.getReadableDatabase();
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String tbName = EventContract.KeywordEntry.TABLE_NAME;
                Cursor cursor = db.rawQuery("SELECT * FROM " + tbName, null);
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
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(eventDatabase);
        String tbName = EventContract.KeywordEntry.TABLE_NAME;
        db.insert(tbName, createKeyword(keyword));
    }

    private ContentValues createKeyword(String keyword) {
        ContentValues values = new ContentValues();
        values.put(EventContract.KeywordEntry.COLUMN_NAME_TITLE, keyword);
        return values;
    }
}
