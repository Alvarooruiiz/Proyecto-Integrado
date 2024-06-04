package com.example.proyectoalfari.DataBaseSQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "usuario.db";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + SQLiteBDContract.BDTablas.TABLE_NAME;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + SQLiteBDContract.BDTablas.TABLE_NAME + " (" +
                    SQLiteBDContract.BDTablas.COLUMN_USERNAME + " TEXT PRIMARY KEY," +
                    SQLiteBDContract.BDTablas.COLUMN_DATOS + " TEXT)";

    private static final String SQL_CREATE_DISH_ENTRIES =
            "CREATE TABLE " + SQLiteBDContract.DishEntry.TABLE_NAME + " (" +
                    SQLiteBDContract.DishEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                    SQLiteBDContract.DishEntry.COLUMN_NAME + " TEXT," +
                    SQLiteBDContract.DishEntry.COLUMN_DESC + " TEXT," +
                    SQLiteBDContract.DishEntry.COLUMN_IMAGE_URL + " TEXT," +
                    SQLiteBDContract.DishEntry.COLUMN_PRICE + " REAL," +
                    SQLiteBDContract.DishEntry.COLUMN_TYPE_DISH + " TEXT," +
                    SQLiteBDContract.DishEntry.COLUMN_ALLERGENS + " TEXT," +
                    SQLiteBDContract.DishEntry.COLUMN_QUANTITY + " INTEGER)";

    private static final String SQL_DELETE_DISH_ENTRIES =
            "DROP TABLE IF EXISTS " + SQLiteBDContract.DishEntry.TABLE_NAME;

    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_DISH_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_DISH_ENTRIES);
        onCreate(db);
    }

}