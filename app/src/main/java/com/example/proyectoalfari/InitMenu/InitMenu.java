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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InitMenu extends AppCompatActivity {

    private CardView cvCartaDish;
    private CardView cvCartaDrink;

    private CardView cvGoogleMaps;

    private SQLiteGestor dbGestor;

    private ImageView ivQrScan;

    private int qrNumber;
    private final Uri direccion = Uri.parse("geo:0,0?q=36.718532,-4.421423(Restaurante Alfari)");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initmenu_layout);

        cvCartaDish = findViewById(R.id.cvCartaDish);
        cvCartaDrink = findViewById(R.id.cvCartaDrink);

        cvGoogleMaps = findViewById(R.id.cvGoogleMaps);

        dbGestor = new SQLiteGestor(this);

        ivQrScan = findViewById(R.id.ivQrScan);

        ivQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initScanner();
            }
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
                String qrContent = result.getContents();
                try {
                    qrNumber = Integer.parseInt(qrContent);
                    Toast.makeText(this, "Código QR: " + qrNumber, Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "El código QR no es un número válido", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
