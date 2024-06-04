package com.example.proyectoalfari;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.Admin.Admin;
import com.example.proyectoalfari.Controlador.ControladorUser;
import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.InitMenu.InitMenu;
import com.example.proyectoalfari.Model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private User userloged;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        txtUserLog = findViewById(R.id.txtUserLog);
        txtPassLog = findViewById(R.id.txtPassLog);

        database = new AlfariDatabase();

        database.inicializateFirebase(Login.this);

        btnLogin=findViewById(R.id.btnLogin);
        btnRegisterLayout = findViewById(R.id.btnRegisterLayout);

        cbUserLog = findViewById(R.id.cbUserLog);
        dbGestor = new SQLiteGestor(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");



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
                        if(cbUserLog.isChecked()){
                            dbGestor.addUserLog(userLog);
                        }

                        if(userLog.equals("admin") && passLog.equals("admin")){
                            Intent intentAdmin = new Intent(Login.this, Admin.class);
                            startActivity(intentAdmin);
                            clearFields();
                            finish();
                        }else{
                            Intent intentMenu = new Intent(Login.this, InitMenu.class);
                            loadUserFromFirebase(userLog);
                            clearFields();
                            startActivity(intentMenu);
                        }


                    }
                    @Override
                    public void onLoginFailed(String errorMessage) {
                        Toast.makeText(Login.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void loadUserFromFirebase(String userLog) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if(userSnapshot.child("userName").getValue(String.class).equals(userLog)){
                        userloged = userSnapshot.getValue(User.class);
                        ControladorUser.getMiController().setUser(userloged);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void clearFields(){
        txtUserLog.getEditText().setText("");
        txtPassLog.getEditText().setText("");
    }
}