package com.example.proyectoalfari.InitMenu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.proyectoalfari.R;

public class InitMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initmenu_layout);

        Toolbar toolbar = findViewById(R.id.topAppBar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> {

        });

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_profile) {

                return true;
            }
            return false;
        });
    }

}
