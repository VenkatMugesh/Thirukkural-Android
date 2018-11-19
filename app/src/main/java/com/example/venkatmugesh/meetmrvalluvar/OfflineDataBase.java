package com.example.venkatmugesh.meetmrvalluvar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OfflineDataBase extends SQLiteOpenHelper {



    DataFetcher dataFetcher;
    private static final String TAG = "DatabaseHelper";
    private static final String TABLE_NAME = "kural_table";

    private static final String COL1 = "ID";

    private static final String COL2 = "kuralNumber";
    private static final String COL3 = "line1";
    private static final String COL4 = "line2";
    private static final String COL5 = "trans";

    public OfflineDataBase(Context context) {
        super(context , TABLE_NAME , null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_NAME +"(" + COL1  +" INTEGER PRIMARY KEY AUTOINCREMENT, " +

                COL2 +" TEXT, " + COL3 + " TEXT," + COL4 + " TEXT, " + COL5 + " TEXT)";

        db.execSQL(createTable);
        dataFetcher = new DataFetcher();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }


    public boolean addData(String number , String line1 , String line2 , String trans) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, number);
        contentValues.put(COL3, line1);
        contentValues.put(COL4, line2);
        contentValues.put(COL5, trans);



        Log.d(TAG, "addData: Adding " + number + " to " + TABLE_NAME);



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
    public Cursor getItemID(String name){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +

                " WHERE " + COL2 + " = '" + name + "'";

        Cursor data = db.rawQuery(query, null);

        return data;

    }

    public Cursor getData(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME +" WHERE " + COL1 + " = '" + id + "'";

        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getLine1(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL3 +" FROM " + TABLE_NAME +" WHERE " + COL1 + " = '" + id + "'";

        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public Cursor getLine2(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL4 +" FROM " + TABLE_NAME +" WHERE " + COL1 + " = '" + id + "'";

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteKural(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + id + "'";

        db.execSQL(query);
    }

}


