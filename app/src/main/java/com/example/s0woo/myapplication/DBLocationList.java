package com.example.s0woo.myapplication;

/**
 * Created by s0woo on 2017-04-14.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBLocationList extends SQLiteOpenHelper {
    public DBLocationList (Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists LocationList("
                + "idNumber integer, "
                + "name text, "
                + "point double);";
        db.execSQL(sql);
    }

    // DB 업그레이드를 위해 버전이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists LocationList";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insert(int idNumber, String name, double point) {
        SQLiteDatabase db = getWritableDatabase();
        // DB에 입력한 값으로 행 추가
        /* db.execSQL(~sql문 입력~); */
        ContentValues values= new ContentValues();
        values.put("idNumber", idNumber);
        values.put("name", name);
        values.put("point", point);
        db.insert("LocationList", null, values);
        db.close();
    }
}
