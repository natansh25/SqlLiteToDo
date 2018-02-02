package com.example.natan.sqllitetodo.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by natan on 2/1/2018.
 */

public class MySqlProvider extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "notes.db";

    // creating object for sqllite
    private SQLiteDatabase mSQLiteDatabaseW;
    private SQLiteDatabase mSQLiteDatabaseR;


    public MySqlProvider(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        final String SQL_CREATE_TABLE = "CREATE TABLE " + MyContract.NotesEntry.TABLE_NAME + " (" +
                MyContract.NotesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MyContract.NotesEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MyContract.NotesEntry.COLUMN_DATE_TIME + " TEXT NOT NULL " +
                "); ";

        db.execSQL(SQL_CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MyContract.NotesEntry.TABLE_NAME);
        onCreate(db);

    }


    // Insert method


    /*public long insert(String word, String date_time) {
        long newID = 0;
        if (word != null && date_time != null) {

            mSQLiteDatabaseW = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(MyContract.NotesEntry.COLUMN_TITLE, word);
            cv.put(MyContract.NotesEntry.COLUMN_DATE_TIME, date_time);
            newID = mSQLiteDatabaseW.insert(MyContract.NotesEntry.TABLE_NAME, null, cv);

        }
        return newID;
    }*/


}
