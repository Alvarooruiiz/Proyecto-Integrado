package com.example.proyectoalfari.Splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.R;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_SCREEN_DURATION = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
