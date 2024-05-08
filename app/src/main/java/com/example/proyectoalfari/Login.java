package com.example.proyectoalfari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Dish.CreateDish;

public class Login extends AppCompatActivity {

    private AlfariDatabase database;

    private Button btnRegisterLayout;
    private Button btnLogin;

    private Intent intentRegister;
    private Intent intentRCreate;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        database = new AlfariDatabase();

        database.inicializateFirebase(Login.this);

        btnLogin=findViewById(R.id.btnLogin);
        btnRegisterLayout = findViewById(R.id.btnRegisterLayout);

        btnRegisterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentRegister = new Intent(Login.this,Register.class);
                startActivity(intentRegister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentRCreate = new Intent(Login.this, CreateDish.class);
                startActivity(intentRCreate);
            }
        });


    }


}
