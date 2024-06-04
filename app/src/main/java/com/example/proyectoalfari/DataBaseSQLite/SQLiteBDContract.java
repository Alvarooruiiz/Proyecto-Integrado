package com.example.proyectoalfari.DataBaseSQLite;

import android.provider.BaseColumns;

public class SQLiteBDContract {
    private SQLiteBDContract() {}
    public static class BDTablas implements BaseColumns {

        public static final String TABLE_NAME="usuario";
        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_DATOS="datos";
    }

    public static class DishEntry implements BaseColumns {
        public static final String TABLE_NAME = "dish";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_IMAGE_URL = "image_url";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TYPE_DISH = "type_dish";
        public static final String COLUMN_ALLERGENS = "allergens";
        public static final String COLUMN_QUANTITY = "quantity";
    }
}