package com.example.proyectoalfari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Dish.CreateDish;
import com.example.proyectoalfari.Menu.Menu;
import com.google.android.material.textfield.TextInputLayout;

public class Login extends AppCompatActivity {

    private AlfariDatabase database;

    private Button btnRegisterLayout;
    private Button btnLogin;

    private Intent intentRegister;
    private Intent intentRCreate;
    private Intent intentMenu;

    private TextInputLayout txtUserLog;
    private TextInputLayout txtPassLog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        txtUserLog = findViewById(R.id.txtUserLog);
        txtPassLog = findViewById(R.id.txtPassLog);

        database = new AlfariDatabase();

        database.inicializateFirebase(Login.this);

        btnLogin=findViewById(R.id.btnLogin);
        btnRegisterLayout = findViewById(R.id.btnRegisterLayout);

        btnRegisterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentRegister = new Intent(Login.this,CreateDish.class);
                startActivity(intentRegister);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etUserLog = txtUserLog.getEditText();
                String userLog = etUserLog.getText().toString().trim();
                EditText etPassLog = txtPassLog.getEditText();
                String passLog = etPassLog.getText().toString().trim();



                database.checkUserCredentials(userLog, passLog, new AlfariDatabase.OnLoginResultListener() {
                    @Override
                    public void onLoginSuccess() {
                        Intent intentMenu = new Intent(Login.this, Menu.class);
                        startActivity(intentMenu);
                    }

                    @Override
                    public void onLoginFailed(String errorMessage) {
                        Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }


}
