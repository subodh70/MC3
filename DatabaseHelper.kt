package com.example.sensorapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Coordinates.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DatabaseContract.Data.TABLE_NAME}(" +
                    "${DatabaseContract.Data._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContract.Data.COLUMN_NAME_X} TEXT," +
                    "${DatabaseContract.Data.COLUMN_NAME_Y} TEXT," +
                    "${DatabaseContract.Data.COLUMN_NAME_Z} TEXT," +
                    "${DatabaseContract.Data.COLUMN_NAME_Time} TEXT)"
    }
}
