package com.example.proyectoalfari.Admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.Dish.CreateDish;
import com.example.proyectoalfari.InitMenu.InitMenu;
import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.Menu.Table;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.UUID;

public class Admin extends AppCompatActivity {

    private AlfariDatabase database;

    private Button btnLogOut;

    private CardView cvAddPlate;
    private CardView cvEditPlate;
    private CardView cvUserList;
    private CardView cvAddPhoto;
    private CardView cvChangeLocation;
    private CardView cvAddTable;

    private SQLiteGestor dbGestor;

    private boolean exist = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        database = new AlfariDatabase();


        database.inicializateFirebase(this);

        btnLogOut = findViewById(R.id.btnLogOut);

        cvAddPlate = findViewById(R.id.cvAddPlate);
        cvEditPlate = findViewById(R.id.cvEditPlate);
        cvUserList = findViewById(R.id.cvUserList);
        cvAddPhoto = findViewById(R.id.cvAddPhoto);
        cvChangeLocation = findViewById(R.id.cvChangeLocation);
        cvAddTable = findViewById(R.id.cvAddTable);

        dbGestor = new SQLiteGestor(this);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbGestor.deleteUserLog();
                Intent intent = new Intent(Admin.this, Login.class);
                startActivity(intent);
            }
        });

        cvAddPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, CreateDish.class);
                startActivity(intent);
            }
        });

        cvEditPlate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, DishList.class);
                startActivity(intent);
            }
        });

        cvUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this, UserList.class);
                startActivity(intent);
            }
        });
        cvChangeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cvAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddTableDialog();
            }
        });
    }

    private void showAddTableDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.carview_add_table, null);
        builder.setView(view);

        TextInputLayout etTableCode = view.findViewById(R.id.txtTableCode);
        Button btnSaveTable = view.findViewById(R.id.btnSaveTable);



        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        btnSaveTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etTable = etTableCode.getEditText();
                String tableTxt = etTable.getText().toString().trim();
                if(!tableTxt.isEmpty()){
                    if(!qrExist(tableTxt)){
                        saveTableToDatabase(tableTxt);
                        dialog.dismiss();
                    }else{
                        Toast.makeText(Admin.this, "El código de mesa ya existe", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Admin.this, "Ingrese un código de mesa", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void saveTableToDatabase(String tableCode) {
        Table  table = new Table(UUID.randomUUID().toString(), tableCode);
        database.getDatabaseReference().child("Tables").push().setValue(table);
        Toast.makeText(this, "Se ha agregado una mesa", Toast.LENGTH_SHORT).show();
    }

    public boolean qrExist(String tableCode) {
        DatabaseReference databaseReference = database.getDatabaseReference().child("Tables");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                exist = false;
                for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                    if(tableSnapshot.child("tableCode").getValue(String.class).equals(tableCode)){
                        exist = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin.this, "Error al cargar mesas", Toast.LENGTH_SHORT).show();
            }
        });
        return exist;
    }
}
