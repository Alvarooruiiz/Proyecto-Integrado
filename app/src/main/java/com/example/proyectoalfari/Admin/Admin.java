package com.example.proyectoalfari.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.Dish.CreateDish;
import com.example.proyectoalfari.InitMenu.InitMenu;
import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputEditText;

public class Admin extends AppCompatActivity {

    private AlfariDatabase database;

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

        database = new AlfariDatabase();


        database.inicializateFirebase(this);

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

    private void showAddTableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.cardview_dish_detail, null);
        builder.setView(view);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextInputEditText etTableCode = view.findViewById(R.id.txtTableCode);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnSaveTable = view.findViewById(R.id.btnSaveTable);

        AlertDialog dialog = builder.create();

        btnSaveTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tableCode = etTableCode.getText().toString();
                if(!tableCode.isEmpty()){
                    if(!qrExist(tableCode)){
                        saveTableToDatabase(tableCode);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(Admin.this, "El código de mesa ya existe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Admin.this, "Ingrese un código de mesa", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void saveTableToDatabase(String tableCode) {
        database.getDatabaseReference().child("Tables").push().setValue(tableCode);
    }

    public boolean qrExist(String tableCode) {
        boolean exist = false;

        return exist;
    }
}
