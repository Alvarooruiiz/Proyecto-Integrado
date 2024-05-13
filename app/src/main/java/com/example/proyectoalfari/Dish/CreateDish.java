package com.example.proyectoalfari.Dish;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FileDownloadTask;

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

    private ImageView ivImage;

    private TextInputLayout txtDishName;
    private TextInputLayout txtDishDesc;

    private Button btnCreateDish;

    private ArrayList<String> allergens;

    private AlfariDatabase database;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageRef;

    private static final int REQUEST_SELECT_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_dish_layout);

        database = new AlfariDatabase();


        database.inicializateFirebase(this);

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

        ivImage = findViewById(R.id.ivImage);

        allergens = new ArrayList<>();

        storageRef = FirebaseStorage.getInstance().getReference();



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
                    allergens.add("gluten");
                } else {
                    ivGluten.setImageResource(R.drawable.gluten_oscuro_icon);
                    allergens.remove("gluten");
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
                    allergens.add("crustacean");
                } else {
                    ivCrustaceo.setImageResource(R.drawable.crustaceo_oscuro_icon);
                    allergens.remove("crustacean");
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
                    allergens.add("egg");
                } else {
                    ivHuevo.setImageResource(R.drawable.egg_oscuro_icon);
                    allergens.remove("egg");
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
                    allergens.add("nuts");
                } else {
                    ivNueces.setImageResource(R.drawable.nuts_oscuro_icon);
                    allergens.remove("nuts");
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
                    allergens.add("dairy");
                } else {
                    ivLeche.setImageResource(R.drawable.dairy_oscuro_icon);
                    allergens.remove("dairy");
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
                    allergens.add("soy");
                } else {
                    ivSoja.setImageResource(R.drawable.soy_oscuro_icon);
                    allergens.remove("soy");
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
                    allergens.add("fish");
                } else {
                    ivPescado.setImageResource(R.drawable.fish_oscuro_icon);
                    allergens.remove("fish");
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
                    allergens.add("mustard");
                } else {
                    ivMustard.setImageResource(R.drawable.mustard_oscuro_icon);
                    allergens.remove("mustard");
                }
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnCreateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etDishName = txtDishName.getEditText();
                String dishName = etDishName.getText().toString().trim();
                EditText etDishDesc = txtDishDesc.getEditText();
                String dishDesc = etDishDesc.getText().toString().trim();
                Dish d =new Dish();
                d.setId("1");
                d.setName(dishName);
                d.setDesc(dishDesc);
                d.setAllergens(allergens);
                Log.d("Firebase", "Dish: " + d.getId() + ", Name: " + d.getName());

                database.getDatabaseReference().child("Dish").child(d.getId()).setValue(d);
            }
        });
    }

    private void showOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Seleccionar una opción")
                .setItems(new CharSequence[]{"Seleccionar desde la galeria", "Abrir camara"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                openGallery();
                                break;
                            case 1:
                                openCamera();
                                break;
                            default:
                                break;
                        }
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_SELECT_IMAGE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
}
