package com.example.androidassignments;

import android.content.Context;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    protected static String ACTIVITY_NAME = "ChatDatabaseHelper";


    public static final String DATABASE_NAME ="Chat.db" ;
    public static final int VERSION_NUM=4;
    public static final String TABLE_NAME = "TextTable";
    public static final String KEY_ID="KEY_ID";
    public static final String KEY_MESSAGE="message";

    //2 create database
    public static final String DATABASE_CREATE = "create table "
            +TABLE_NAME + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";


    public ChatDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        Log.i(ACTIVITY_NAME,"Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        Log.i(ACTIVITY_NAME,"Calling onUpgrade, oldVersion="+oldVersion + "newVersion=" + newVersion);
        onCreate(db);

    }
}
