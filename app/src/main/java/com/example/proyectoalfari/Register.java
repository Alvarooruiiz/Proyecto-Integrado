package com.example.proyectoalfari;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.DatePicker.DatePickerFragment;
import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Register extends AppCompatActivity {

    private AlfariDatabase database;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private TextInputLayout tilUser;
    private TextInputLayout tilPass;
    private TextInputLayout tilPassRep;
    private TextInputLayout tilEmail;
    private EditText etDate;


    private Button btnRegister;
    private Button btnLoginLayout;

    private Intent loginLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        database = new AlfariDatabase();


        database.inicializateFirebase(this);

        tilUser = findViewById(R.id.tilUserReg);
        tilPass = findViewById(R.id.tilPasswordReg);
        tilPassRep = findViewById(R.id.tilPasswordRepeatReg);
        tilEmail = findViewById(R.id.tilEmailReg);
        etDate = findViewById(R.id.etDate);

        btnRegister = findViewById(R.id.btnRegister);
        btnLoginLayout = findViewById(R.id.btnLoginLayout);
        loginLayout = new Intent(Register.this,Login.class);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    addUser();
                    clearFields();
                    Toast.makeText(Register.this, "Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                    startActivity(loginLayout);
                }else {

                }
            }
        });

        btnLoginLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(loginLayout);
            }
        });

    }

    public void inicializateFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + " / " + (month + 1) + " / " + year;
                etDate.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public boolean validation() {

        EditText etUser = tilUser.getEditText();
        String userText = etUser.getText().toString().trim();

        EditText etEmail = tilEmail.getEditText();
        String emailText = etEmail.getText().toString().trim();

        EditText etPass = tilPass.getEditText();
        String passText = etPass.getText().toString().trim();

        EditText etPassRep = tilPassRep.getEditText();
        String passRepText = etPassRep.getText().toString().trim();

        String dateText = etDate.getText().toString();

        if (userText.isEmpty()) {
            tilUser.setError("El usuario no puede estar vacío");
            return false;
        } else if (emailText.isEmpty()) {
            tilEmail.setError("El email no puede estar vacío");
            return false;
        } else if (emailText.contains("@")){
            tilEmail.setError("El formato del email no es correcto (example@example.com)");
            return false;
        }else if (passText.isEmpty()) {
            tilPass.setError("La contraseña no puede estar vacía");
            return false;
        } else if (passRepText.isEmpty()) {
            tilPassRep.setError("La contraseña no puede estar vacía");
            return false;
        } else if (!passText.equals(passRepText)) {
            tilPassRep.setError("La contraseña no coincide");
            return false;
        } else if (dateText.isEmpty()) {
            etDate.setError("La fecha no puede estar vacia");
            return false;
        }

        return true;
    }

    public void clearFields(){
        tilUser.getEditText().setText("");
        tilEmail.getEditText().setText("");
        tilPass.getEditText().setText("");
        tilPassRep.getEditText().setText("");
        etDate.setText("");
    }

    public void addUser(){
        EditText etUser = tilUser.getEditText();
        String userText = etUser.getText().toString().trim();

        EditText etEmail = tilEmail.getEditText();
        String emailText = etEmail.getText().toString().trim();

        EditText etPass = tilPass.getEditText();
        String passText = etPass.getText().toString().trim();

        String dateText = etDate.getText().toString();


        User user = new User();
        user.setUid(UUID.randomUUID().toString());
        user.setUserName(userText);
        user.setEmail(emailText);
        user.setPass(passText);
        user.setDate(dateText);
        Log.d("Firebase", "User: " + user.getUserName() + ", Email: " + user.getEmail());

        database.getDatabaseReference().child("User").child(user.getUserName()).setValue(user);
    }
}
