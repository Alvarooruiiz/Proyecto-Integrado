package com.example.proyectoalfari.DataBaseSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

/*
Esta clase define la estructura de la base de datos local SQLite de autologin
 */
public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "usuario.db";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SQLiteBDContract.BDTablas.TABLE_NAME;

    // Definimos la sentencia que creará nuestra tabla en SQLite y sus columnas.
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SQLiteBDContract.BDTablas.TABLE_NAME + " (" +
                    SQLiteBDContract.BDTablas.COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    SQLiteBDContract.BDTablas.COLUMN_DATOS + " TEXT)";



    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}