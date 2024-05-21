package com.example.proyectoalfari.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.InitMenu.InitMenu;
import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.R;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 3000;
    private SQLiteGestor dbGestor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        dbGestor = new SQLiteGestor(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String userLog = dbGestor.getUserLog();
                if(userLog != null){
                    Intent intent = new Intent(SplashScreen.this, InitMenu.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(SplashScreen.this, Login.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, SPLASH_SCREEN_DURATION);
    }
}
