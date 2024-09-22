package org.tensorflow.lite.examples.objectdetection.fragments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GEM_LAB";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create the 'log' table
        db.execSQL("CREATE TABLE IF NOT EXISTS 'log' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'uname' TEXT,'mail' TEXT PRIMARY KEY, 'image' BLOB, 'name' TEXT, 'Av.price' FLOAT, 'price' FLOAT, 'weight' FLOAT)");

        // create the 'inventory' table
        db.execSQL("CREATE TABLE IF NOT EXISTS 'inventory' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'product' TEXT, 'quantity' INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle database upgrades here
    }

}

