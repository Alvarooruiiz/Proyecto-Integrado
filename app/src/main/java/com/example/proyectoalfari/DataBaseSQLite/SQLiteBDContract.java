package com.example.proyectoalfari.DataBaseSQLite;

import android.provider.BaseColumns;

public class SQLiteBDContract {
    private SQLiteBDContract() {}
    public static class BDTablas implements BaseColumns {

        // Aquí definiremos las tablas y las columnas que tendrá nuestro SQLite
        public static final String TABLE_NAME="usuario";
        public static final String COLUMN_USERNAME="username";
        public static final String COLUMN_DATOS="datos";
    }
}