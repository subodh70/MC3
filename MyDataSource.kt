package com.example.sensorapp.database

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.ayush.a31.SD

import java.io.File
import java.io.FileWriter


@Suppress("NAME_SHADOWING")
class MyDataSource(val appContext: Context) {

    private val dbHelper: DatabaseHelper = DatabaseHelper(appContext)

    fun insertData(time: Long, x: String, y: String, z: String) {
        val db = dbHelper.writableDatabase

        val tenSecondsAgo = System.currentTimeMillis() - 10_000
        val selection = "${DatabaseContract.Data.COLUMN_NAME_Time} <= ?"
        val selectionArgs = arrayOf(tenSecondsAgo.toString())
        db.delete(DatabaseContract.Data.TABLE_NAME, selection, selectionArgs)

        val values = ContentValues().apply {
            put(DatabaseContract.Data.COLUMN_NAME_Time, time)
            put(DatabaseContract.Data.COLUMN_NAME_X, x)
            put(DatabaseContract.Data.COLUMN_NAME_Y, y)
            put(DatabaseContract.Data.COLUMN_NAME_Z, z)
        }
        val newRowId = db.insert(DatabaseContract.Data.TABLE_NAME, null, values)

        if (newRowId != -1L) {
            Log.d("MyDataSource", "Data inserted successfully with row ID: $newRowId")
        } else {
            Log.e("MyDataSource", "Failed to insert data")
        }
    }

    fun getAllData(): List<SD> {
        val db = dbHelper.readableDatabase
        val dataList = mutableListOf<SD>()

        val query = "SELECT * FROM ${DatabaseContract.Data.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        cursor.use { cursor ->
            while (cursor.moveToNext()) {
                val time = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_Time))
                val x = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_X))
                val y = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_Y))
                val z = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_Z))

                val data = SD(time, x.toFloat(), y.toFloat(), z.toFloat())
                dataList.add(data)
            }
        }

        return dataList
    }

    fun exportDataToFile(fileName: String) {
        val db = dbHelper.readableDatabase

        val query = "SELECT * FROM ${DatabaseContract.Data.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        val externalDir = appContext.getExternalFilesDir(null)
        val file = File(externalDir, fileName)

        try {
            FileWriter(file).use { writer ->
                cursor.use { cursor ->
                    while (cursor.moveToNext()) {
                        val time = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_Time))
                        val x = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_X))
                        val y = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_Y))
                        val z = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.Data.COLUMN_NAME_Z))

                        writer.write("$time, $x, $y, $z\n")
                    }
                }
            }
            Log.d("ExportDataToFile", "Data exported to file: ${file.absolutePath}")
        } catch (e: Exception) {
            Log.e("ExportDataToFile", "Error exporting data to file", e)
        }
    }
}
