package com.example.proyectoalfari.DataBaseSQLite;

import android.provider.BaseColumns;

public class SQLiteBDContract {
    private SQLiteBDContract() {}
    public static class BDTablas implements BaseColumns {

        public static final String TABLE_NAME="usuario";
        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_DATOS="datos";
    }
}