package com.example.sensorapp.database

import android.provider.BaseColumns

object DatabaseContract {
    object Data : BaseColumns {
        const val TABLE_NAME = "Dimensions"
        const val COLUMN_NAME_Time = "Time"
        const val COLUMN_NAME_X = "X"
        const val COLUMN_NAME_Y = "Y"
        const val COLUMN_NAME_Z = "Z"
        const val _ID = BaseColumns._ID
    }
}