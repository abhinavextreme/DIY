package com.diy.travelers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abhinav on 7/9/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "DIYTravels";
    public String TABLE_NAME = null;
    public String query;

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.TABLE_NAME = TABLE_NAME;
    }

    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
        db.execSQL(createTable(TABLE_NAME));

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public String createTable(String tableName) {
        TABLE_NAME = tableName;
        query = "CREATE TABLE " + TABLE_NAME + "(id integer primary key, name text,phone text,email text, street text,place text)";
        return query;
    }

    public boolean insertData(String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.insert(TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " where id=" + id + "", null);
        return res;
    }

}
