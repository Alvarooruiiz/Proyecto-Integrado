package com.example.proyectoalfari.Dish;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.R;

public class CreateDish extends AppCompatActivity {
    private ImageView ivGluten;
    private ImageView ivCrustaceo;
    private ImageView ivSesamo;
    private ImageView ivHuevo;
    private ImageView ivNueces;
    private ImageView ivLeche;
    private ImageView ivSoja;
    private ImageView ivPescado;


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
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivSesamo.setImageResource(R.drawable.sesame_icon);
                } else {
                    ivSesamo.setImageResource(R.drawable.sesame_oscuro_icon);
                }
            }
        });
        ivGluten.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivGluten.setImageResource(R.drawable.gluten_icon);
                } else {
                    ivGluten.setImageResource(R.drawable.gluten_oscuro_icon);
                }
            }
        });
        ivCrustaceo.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivCrustaceo.setImageResource(R.drawable.crustacean_icon);
                } else {
                    ivCrustaceo.setImageResource(R.drawable.crustaceo_oscuro_icon);
                }
            }
        });
        ivHuevo.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivHuevo.setImageResource(R.drawable.egg_icon);
                } else {
                    ivHuevo.setImageResource(R.drawable.egg_oscuro_icon);
                }
            }
        });
        ivNueces.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivNueces.setImageResource(R.drawable.nuts_icon);
                } else {
                    ivNueces.setImageResource(R.drawable.nuts_oscuro_icon);
                }
            }
        });
        ivLeche.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivLeche.setImageResource(R.drawable.dairy_icon);
                } else {
                    ivLeche.setImageResource(R.drawable.dairy_oscuro_icon);
                }
            }
        });
        ivSoja.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivSoja.setImageResource(R.drawable.soy_icon);
                } else {
                    ivSoja.setImageResource(R.drawable.soy_oscuro_icon);
                }
            }
        });
        ivPescado.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivPescado.setImageResource(R.drawable.fish_icon);
                } else {
                    ivPescado.setImageResource(R.drawable.fish_oscuro_icon);
                }
            }
        });
    }
}
