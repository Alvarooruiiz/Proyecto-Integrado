package com.example.proyectoalfari.DataBaseSQLite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteGestor {
    private SQLiteDBHelper dbHelper;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;
    public SQLiteGestor(Activity act) {
        dbHelper=new SQLiteDBHelper(act);
        dbWrite=dbHelper.getWritableDatabase();
        dbRead=dbHelper.getReadableDatabase();
    }

    public long addUserLog(String username) {
        ContentValues values=new ContentValues();
        values.put(SQLiteBDContract.BDTablas.COLUMN_USERNAME, username);
        values.put(SQLiteBDContract.BDTablas.COLUMN_DATOS, "nada");
        return dbWrite.insert(SQLiteBDContract.BDTablas.TABLE_NAME, null, values);
    }

    public String getUserLog() {
        String username = null;
        Cursor cursor = dbRead.rawQuery("SELECT " + SQLiteBDContract.BDTablas.COLUMN_USERNAME + " FROM " + SQLiteBDContract.BDTablas.TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            username = cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.BDTablas.COLUMN_USERNAME));
        }
        if (cursor != null) {
            cursor.close();
        }
        return username;
    }

    public void deleteUserLog() {
        dbWrite.delete(SQLiteBDContract.BDTablas.TABLE_NAME, null, null);
    }
}