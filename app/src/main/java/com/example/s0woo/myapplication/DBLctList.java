package com.example.s0woo.myapplication;

/**
 * Created by s0woo on 2017-05-24.
 */

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLctList extends SQLiteOpenHelper {
    public DBLctList (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists SavedLocation("
                + "_index integer, "
                + "name text, "
                + "latitude real, "
                + "longitude real);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists SavedLocation";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(int _index, String name, double latitude, double longitude) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("_index", _index);
        values.put("name", name);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        db.insert("LctList", null, values);
        db.close();
    }
}