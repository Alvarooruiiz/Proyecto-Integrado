package com.example.proyectoalfari.Dish;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CreateDish extends AppCompatActivity {
    private ImageView ivGluten;
    private ImageView ivCrustaceo;
    private ImageView ivSesamo;
    private ImageView ivHuevo;
    private ImageView ivNueces;
    private ImageView ivLeche;
    private ImageView ivSoja;
    private ImageView ivPescado;
    private ImageView ivMustard;

    private TextInputLayout txtDishName;
    private TextInputLayout txtDishDesc;

    private Button btnCreateDish;

    private ArrayList<String> allergens;


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
        ivMustard = findViewById(R.id.imageButtonMustard);

        txtDishName = findViewById(R.id.txtDishName);
        txtDishDesc = findViewById(R.id.txtDishDesc);

        btnCreateDish = findViewById(R.id.btnCrear);
        ivSesamo.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivSesamo.setImageResource(R.drawable.sesame_icon);
                    allergens.add("sesame");
                } else {
                    ivSesamo.setImageResource(R.drawable.sesame_oscuro_icon);
                    allergens.remove("sesame");
                }
            }
        });
        ivPescado = findViewById(R.id.imageButtonFish);
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
        ivMustard.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ivMustard.setImageResource(R.drawable.mustard_icon);
                } else {
                    ivMustard.setImageResource(R.drawable.mustard_oscuro_icon);
                }
            }
        });

        btnCreateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishName = txtDishName.getEditText().toString();
                String dishDesc = txtDishDesc.getEditText().toString();
                Dish d =new Dish();
                d.setId("1");
                d.setName(dishName);
                d.setDesc(dishDesc);
                d.setImage();
                d.setAllergens();
            }
        });
    }
}
