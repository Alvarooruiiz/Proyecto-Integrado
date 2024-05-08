package com.example.proyectoalfari.Dish;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.proyectoalfari.R;

public class CreateDish extends AppCompatActivity {
    private ImageButton ivGluten;
    private ImageButton ivCrustaceo;
    private ImageButton ivSesamo;
    private ImageButton ivHuevo;
    private ImageButton ivNueces;
    private ImageButton ivLeche;
    private ImageButton ivSoja;
    private ImageButton ivPescado;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_dish_layout);

        ivGluten = findViewById(R.id.imageButtonGluten);
        ivCrustaceo = findViewById(R.id.imageButtonCrustacean);
        ivSesamo = findViewById(R.id.imageButtonSesame);
        ivHuevo = findViewById(R.id.imageButtonEgg);
        ivNueces = findViewById(R.id.imageButtonNut);
        ivLeche = findViewById(R.id.imageButtonDairy);
        ivSoja = findViewById(R.id.imageButtonSoy);
        ivPescado = findViewById(R.id.imageButtonFish);

        ivSesamo.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = false;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivSesamo.setImageResource(R.drawable.egg_icon);
                } else {
                    ivSesamo.setImageResource(R.drawable.crustacean_icon);
                }
            }
        });
    }
}
