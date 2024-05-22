package com.example.proyectoalfari.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.Dish.CreateDish;
import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.R;

public class Admin extends AppCompatActivity {

    private Button btnLogOut;

    private CardView cvAddPlate;
    private CardView cvEditPlate;
    private CardView cvUserList;
    private CardView cvAddPhoto;
    private CardView cvChangeLocation;
    private CardView cvAddTable;

    private SQLiteGestor dbGestor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        btnLogOut = findViewById(R.id.btnLogOut);

        cvAddPlate = findViewById(R.id.cvAddPlate);
        cvEditPlate = findViewById(R.id.cvEditPlate);
        cvUserList = findViewById(R.id.cvUserList);
        cvAddPhoto = findViewById(R.id.cvAddPhoto);
        cvChangeLocation = findViewById(R.id.cvChangeLocation);
        cvAddTable = findViewById(R.id.cvAddTable);

        dbGestor = new SQLiteGestor(this);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbGestor.deleteUserLog();
                Intent intent = new Intent(Admin.this, Login.class);
                startActivity(intent);
            }
        });

        cvAddPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, CreateDish.class);
                startActivity(intent);
            }
        });

        cvEditPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, DishList.class);
                startActivity(intent);
            }
        });

        cvUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, UserList.class);
                startActivity(intent);
            }
        });
        cvChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
