package com.example.proyectoalfari.InitMenu;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.Menu.Menu;
import com.example.proyectoalfari.R;

public class InitMenu extends AppCompatActivity {

    private CardView cvCartaDish;
    private CardView cvCartaDrink;

    private CardView cvGoogleMaps;

    private SQLiteGestor dbGestor;
    private final Uri direccion = Uri.parse("geo:0,0?q=36.718532,-4.421423(Restaurante Alfari)");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initmenu_layout);

        cvCartaDish = findViewById(R.id.cvCartaDish);
        cvCartaDrink = findViewById(R.id.cvCartaDrink);

        cvGoogleMaps = findViewById(R.id.cvGoogleMaps);

        dbGestor = new SQLiteGestor(this);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> {

        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_profile) {
                dbGestor.deleteUserLog();
            }
            return false;
        });

        cvGoogleMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, direccion);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        cvCartaDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitMenu.this, Menu.class);
                startActivity(intent);
            }
        });
        
        cvCartaDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbGestor.deleteUserLog();
                Toast.makeText(InitMenu.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
