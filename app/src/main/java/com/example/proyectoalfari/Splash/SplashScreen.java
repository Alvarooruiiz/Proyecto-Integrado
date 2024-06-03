package com.example.proyectoalfari.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.Controlador.Controlador;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.InitMenu.InitMenu;
import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.Model.User;
import com.example.proyectoalfari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 3000;
    private SQLiteGestor dbGestor;
    private DatabaseReference databaseReference;
    private User userLoged;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        dbGestor = new SQLiteGestor(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userLog = dbGestor.getUserLog();
                if (userLog != null) {
                    loadUserFromFirebase(userLog);
                } else {
                    navigateToLogin();
                }
            }
        }, SPLASH_SCREEN_DURATION);
    }

    private void loadUserFromFirebase(String userLog) {
        databaseReference.orderByChild("userName").equalTo(userLog).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        userLoged = userSnapshot.getValue(User.class);
                    }
                    Controlador.getMiController().setUser(userLoged);
                    navigateToInitMenu();
                } else {
                    navigateToLogin();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SplashScreen.this, "Error al cargar usuario", Toast.LENGTH_SHORT).show();
                navigateToLogin();
            }
        });
    }

    private void navigateToInitMenu() {
        Intent intent = new Intent(SplashScreen.this, InitMenu.class);
        startActivity(intent);
        finish();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SplashScreen.this, Login.class);
        startActivity(intent);
        finish();
    }
}