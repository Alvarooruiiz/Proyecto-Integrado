package com.example.proyectoalfari.Dish;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FileDownloadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

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
    private TextInputLayout txtDishPrice;

    private Button btnCreateDish;

    private ArrayList<String> allergens;

    private AlfariDatabase database;
    private FirebaseDatabase firebaseDatabase;
    private StorageReference storageRef;

    private static final int REQUEST_SELECT_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    
    private Uri imageUri;
    private String imageUrl;


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
        txtDishPrice = findViewById(R.id.txtDishPrice);

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
                    ivGluten.setImageResource(R.drawable.gluten_oscuro_icon);
                    allergens.add("gluten");
                } else {
                    ivGluten.setImageResource(R.drawable.gluten_icon);
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
                    ivCrustaceo.setImageResource(R.drawable.crustaceo_oscuro_icon);
                    allergens.add("crustacean");
                } else {
                    ivCrustaceo.setImageResource(R.drawable.crustacean_icon);
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
                    ivHuevo.setImageResource(R.drawable.egg_oscuro_icon);
                    allergens.add("egg");
                } else {
                    ivHuevo.setImageResource(R.drawable.egg_icon);
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
                    ivNueces.setImageResource(R.drawable.nuts_oscuro_icon);
                    allergens.add("nuts");
                } else {
                    ivNueces.setImageResource(R.drawable.nuts_icon);
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
                    ivLeche.setImageResource(R.drawable.dairy_oscuro_icon);
                    allergens.add("dairy");
                } else {
                    ivLeche.setImageResource(R.drawable.dairy_icon);
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
                    ivSoja.setImageResource(R.drawable.soy_oscuro_icon);
                    allergens.add("soy");
                } else {
                    ivSoja.setImageResource(R.drawable.soy_icon);
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
                    ivPescado.setImageResource(R.drawable.fish_oscuro_icon);
                    allergens.add("fish");
                } else {
                    ivPescado.setImageResource(R.drawable.fish_icon);
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
                    ivMustard.setImageResource(R.drawable.mustard_oscuro_icon);
                    allergens.add("mustard");
                } else {
                    ivMustard.setImageResource(R.drawable.mustard_icon);
                    allergens.remove("mustard");
                }
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });

        btnCreateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etDishName = txtDishName.getEditText();
                String dishName = etDishName.getText().toString().trim();
                EditText etDishDesc = txtDishDesc.getEditText();
                String dishDesc = etDishDesc.getText().toString().trim();
                EditText etDishPrice = txtDishPrice.getEditText();
                Double dishPrice =Double.parseDouble(etDishPrice.getText().toString()) ;

                StringBuilder allergensString= new StringBuilder();
                for(String s : allergens){
                    allergensString.append(s);
                }

                Dish d = new Dish(UUID.randomUUID().toString(), dishName, dishDesc, imageUrl,dishPrice, allergensString.toString());
                Log.d("Firebase", "Dish: Name: " + d.getName());

                database.getDatabaseReference().child("Dish").child(d.getName()).setValue(d);
                Toast.makeText(CreateDish.this, "Se ha introducido el plato", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_IMAGE && data != null) {
                Uri selectedImageUri = data.getData();
                uploadImageToFirebase(selectedImageUri);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE && data != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageUri = getImageUri(photo);
                uploadImageToFirebase(imageUri);
            }
        }
    }

    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            String imageName = "image_" + System.currentTimeMillis() + ".jpg";

            StorageReference imageRef = storageRef.child("imagesDish/" + imageName);

            imageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            imageUrl = uri.toString();
                            Glide.with(this).load(imageUrl).into(ivImage);
                        });
                    })
                    .addOnFailureListener(exception -> {
                        Log.e("Firebase", "Error uploading image to Firebase Storage: " + exception.getMessage());
                    });
        }
    }
}
