package com.example.proyectoalfari.DataBaseSQLite;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.proyectoalfari.Model.Dish;

import java.util.ArrayList;
import java.util.List;

public class SQLiteGestor {
    private SQLiteDBHelper dbHelper;
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;

    public SQLiteGestor(Activity act) {
        dbHelper = new SQLiteDBHelper(act);
        dbWrite = dbHelper.getWritableDatabase();
        dbRead = dbHelper.getReadableDatabase();
    }

    public long addUserLog(String username) {
        ContentValues values = new ContentValues();
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

    public void addDishes(List<Dish> dishes) {
        for(Dish dish:dishes){
            ContentValues values = new ContentValues();
            values.put(SQLiteBDContract.DishEntry.COLUMN_ID, dish.getId());
            values.put(SQLiteBDContract.DishEntry.COLUMN_NAME, dish.getName());
            values.put(SQLiteBDContract.DishEntry.COLUMN_DESC, dish.getDesc());
            values.put(SQLiteBDContract.DishEntry.COLUMN_IMAGE_URL, dish.getImageUrl());
            values.put(SQLiteBDContract.DishEntry.COLUMN_PRICE, dish.getPrice());
            values.put(SQLiteBDContract.DishEntry.COLUMN_TYPE_DISH, dish.getTypeDish());
            values.put(SQLiteBDContract.DishEntry.COLUMN_ALLERGENS, dish.getAllergens());
            values.put(SQLiteBDContract.DishEntry.COLUMN_QUANTITY, dish.getQuantity());
            dbWrite.insert(SQLiteBDContract.DishEntry.TABLE_NAME, null, values);
        }

    }

    public List<Dish> getDishes() {
        List<Dish> dishList = new ArrayList<>();
        Cursor cursor = dbRead.rawQuery("SELECT * FROM " + SQLiteBDContract.DishEntry.TABLE_NAME, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setId(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_ID)));
                dish.setName(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_NAME)));
                dish.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_DESC)));
                dish.setImageUrl(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_IMAGE_URL)));
                dish.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_PRICE)));
                dish.setTypeDish(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_TYPE_DISH)));
                dish.setAllergens(cursor.getString(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_ALLERGENS)));
                dish.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow(SQLiteBDContract.DishEntry.COLUMN_QUANTITY)));
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return dishList;
    }

    public void deleteDishes() {
        dbWrite.delete(SQLiteBDContract.DishEntry.TABLE_NAME, null, null);
    }
}
