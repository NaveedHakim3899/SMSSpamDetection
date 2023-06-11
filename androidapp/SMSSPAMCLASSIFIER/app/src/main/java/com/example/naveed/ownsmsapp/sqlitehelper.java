package com.example.naveed.ownsmsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlitehelper extends SQLiteOpenHelper {
    private static final String databse= "msgdb";
    private static final int version =1;
    public  String number;
    public  String message;
    public sqlitehelper(Context context) {
        super(context,databse , null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String sql= "CREATE TABLE MYMESSAGES(_ID INTEGER PRIMARY KEY AUTOINCREMENT, NUMBER TEXT, MESSAGE TEXT,IMI TEXT,BelongToCurrentUser BOOLEAN, RESULT TEXT)";
            db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inseertdata(String number, String message, SQLiteDatabase db){
        ContentValues values= new ContentValues();
        values.put("NUMBER", number);
        values.put("MESSAGE",message);
        db.insert("MYMESSAGES",null,values);
    }



}
