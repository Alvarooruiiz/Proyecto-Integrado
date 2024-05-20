package com.example.proyectoalfari.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity {

    private ImageView ivQrScan;
    private RecyclerViewMenu recyclerViewMenuAdapter;
    private RecyclerView rvMenuList;
    private List<Dish> dishList;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        ivQrScan = findViewById(R.id.ivQrScan);
        rvMenuList = findViewById(R.id.rvMenuList);


        databaseReference = FirebaseDatabase.getInstance().getReference("Dish");
        dishList = new ArrayList<>();

        rvMenuList.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMenuAdapter = new RecyclerViewMenu(dishList);

        rvMenuList.setAdapter(recyclerViewMenuAdapter);
        
        loadDishesFromFirebase();
        ivQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initScanner();
            }
        });



    }

    private void loadDishesFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dishSnapshot : snapshot.getChildren()) {
                    Dish dish = dishSnapshot.getValue(Dish.class);
                    dishList.add(dish);
                }
                recyclerViewMenuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Menu.this, "Error al cargar los platos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setTorchEnabled(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show();
            } else {
                String query = result.getContents();

            }
        }
    }
}
