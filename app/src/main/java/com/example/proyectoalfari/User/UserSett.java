package com.example.proyectoalfari.User;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.Controlador.ControladorUser;
import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Model.User;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserSett extends AppCompatActivity {
    private TextInputLayout tilUserRegSett;
    private TextInputLayout tilEmailRegSett;
    private TextInputLayout tilPasswordRegSett;
    private TextInputLayout tilPasswordRepeatRegSett;

    private com.google.android.material.textfield.TextInputEditText tiUser;
    private com.google.android.material.textfield.TextInputEditText tiEmail;
    private com.google.android.material.textfield.TextInputEditText tiPass;
    private com.google.android.material.textfield.TextInputEditText tiPassRep;

    private EditText etDateSett;

    private Button btnEditUserSett;
    private ImageView ivEditIcon;

    private String nameUser;
    String idUser;
    private AlfariDatabase database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_settings_layout);

        User userLoged = ControladorUser.getMiController().getUser();


        tilUserRegSett = findViewById(R.id.tilUserRegSett);
        tilEmailRegSett = findViewById(R.id.tilEmailRegSett);
        tilPasswordRegSett = findViewById(R.id.tilPasswordRegSett);
        tilPasswordRepeatRegSett = findViewById(R.id.tilPasswordRepeatRegSett);
        etDateSett = findViewById(R.id.etDateSett);

        tiUser = findViewById(R.id.tiUser);
        tiEmail = findViewById(R.id.tiEmail);
        tiPass = findViewById(R.id.tiPass);
        tiPassRep = findViewById(R.id.tiPassRep);

        btnEditUserSett = findViewById(R.id.btnEditUserSett);
        ivEditIcon = findViewById(R.id.ivEditIcon);
        
        database = new AlfariDatabase();
        database.inicializateFirebase(this);
        idUser ="";
        nameUser = "";

        loadUserDetails(userLoged);


        ivEditIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    editUserOnFireBase();

                }else{
                    Toast.makeText(UserSett.this, "Ha ocurrido un  error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void editUserOnFireBase(){

        EditText etEmail = tilEmailRegSett.getEditText();
        String emailText = etEmail.getText().toString().trim();

        EditText etPass = tilPasswordRegSett.getEditText();
        String passText = etPass.getText().toString().trim();

        String dateText = etDateSett.getText().toString();

        User updatedUser = new User(idUser, nameUser, emailText, passText, dateText);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("User").child(nameUser);

        userRef.setValue(updatedUser).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error al actualizar los datos: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void loadUserDetails(User user){

        idUser=user.getUid();
        tilUserRegSett.getEditText().setText(user.getUserName());
        nameUser = user.getUserName();
        tilEmailRegSett.getEditText().setText(user.getEmail());
        tilPasswordRegSett.getEditText().setText(user.getPass());
        tilPasswordRepeatRegSett.getEditText().setText(user.getPass());
        etDateSett.setText(user.getDate());
    }

    public boolean validation() {

        EditText etEmail = tilEmailRegSett.getEditText();
        String emailText = etEmail.getText().toString().trim();

        EditText etPass = tilPasswordRegSett.getEditText();
        String passText = etPass.getText().toString().trim();

        EditText etPassRep = tilPasswordRepeatRegSett.getEditText();
        String passRepText = etPassRep.getText().toString().trim();

        String dateText = etDateSett.getText().toString();

        if (emailText.isEmpty()) {
            tilEmailRegSett.setError("El email no puede estar vacío");
            return false;
        } else if (!emailText.contains("@")){
            tilEmailRegSett.setError("El formato del email no es correcto (example@example.com)");
            return false;
        }else if (passText.isEmpty()) {
            tilPasswordRegSett.setError("La contraseña no puede estar vacía");
            return false;
        } else if (passRepText.isEmpty()) {
            tilPasswordRegSett.setError("La contraseña no puede estar vacía");
            return false;
        } else if (!passText.equals(passRepText)) {
            tilPasswordRepeatRegSett.setError("La contraseña no coincide");
            return false;
        } else if (dateText.isEmpty()) {
            etDateSett.setError("La fecha no puede estar vacia");
            return false;
        }

        return true;
    }


    public void clearFields(){
        tilUserRegSett.getEditText().setText("");
        tilEmailRegSett.getEditText().setText("");
        tilPasswordRegSett.getEditText().setText("");
        tilPasswordRepeatRegSett.getEditText().setText("");
        etDateSett.setText("");
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setTitle("Confirmar salida")
                .setMessage("¿Estás seguro de que deseas salir? Los cambios se perderán.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearFields();
                        UserSett.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
