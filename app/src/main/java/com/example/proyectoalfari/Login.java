package com.example.proyectoalfari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.Admin.Admin;
import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.Dish.CreateDish;
import com.example.proyectoalfari.InitMenu.InitMenu;
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

    private SQLiteGestor dbGestor;
    private CheckBox cbUserLog;

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

        cbUserLog = findViewById(R.id.cbUserLog);
        dbGestor = new SQLiteGestor(this);


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
                EditText etUserLog = txtUserLog.getEditText();
                String userLog = etUserLog.getText().toString().trim();
                EditText etPassLog = txtPassLog.getEditText();
                String passLog = etPassLog.getText().toString().trim();

                database.checkUserCredentials(userLog, passLog, new AlfariDatabase.OnLoginResultListener() {
                    @Override
                    public void onLoginSuccess() {
                        if(userLog.equals("admin") && passLog.equals("admin")){
                            Intent intentAdmin = new Intent(Login.this, Admin.class);
                            startActivity(intentAdmin);
                        }
                        if(cbUserLog.isChecked()){
                            dbGestor.addUserLog(userLog);
                        }
                        Intent intentMenu = new Intent(Login.this, InitMenu.class);
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
