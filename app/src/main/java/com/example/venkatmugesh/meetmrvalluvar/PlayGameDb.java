package com.example.venkatmugesh.meetmrvalluvar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PlayGameDb extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "Count_Table";

    private static final String COL1 = "ID";

    private static final String COL2 = "kuralID";

    public PlayGameDb(Context context) {
        super(context , TABLE_NAME , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME +"(" + COL1  +" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " INTEGER)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public boolean addData(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, id);

        long result = db.insert(TABLE_NAME, null, contentValues);


        if (result == -1) {

            return false;

        } else {

            return true;

        }

    }
    public Cursor getData(){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor data = db.rawQuery(query, null);

        return data;

    }
}
