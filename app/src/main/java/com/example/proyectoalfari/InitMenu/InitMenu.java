package com.example.proyectoalfari.InitMenu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Controlador.ControladorTable;
import com.example.proyectoalfari.Controlador.ControladorUser;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.Login;
import com.example.proyectoalfari.Menu.Menu;
import com.example.proyectoalfari.Model.Table;
import com.example.proyectoalfari.R;
import com.example.proyectoalfari.User.UserSett;
import com.google.android.material.carousel.CarouselLayoutManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class InitMenu extends AppCompatActivity {

    private CardView cvCartaDish;
    private CardView cvTable;

    private RecyclerView carouselRecyclerView;
    private PhotoCarouselAdapter adapter;

    private CardView cvGoogleMaps;

    private SQLiteGestor dbGestor;

    private ImageView ivQrScan;
    private ImageView ivUserIcon;
    private ImageView ivLogOut;
    private TextView tvTable;

    private String qrNumber;
    private Boolean qrExist = false;
    private final Uri direccion = Uri.parse("geo:0,0?q=36.718532,-4.421423(Restaurante Alfari)");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initmenu_layout);

        cvCartaDish = findViewById(R.id.cvCartaDish);

        cvGoogleMaps = findViewById(R.id.cvGoogleMaps);

        tvTable = findViewById(R.id.tvTable);

        dbGestor = new SQLiteGestor(this);

        ivQrScan = findViewById(R.id.ivQrScan);
        ivUserIcon = findViewById(R.id.ivUserIcon);
        ivLogOut = findViewById(R.id.ivUserLogOut);

        carouselRecyclerView = findViewById(R.id.carousel_recycler_view);
        adapter = new PhotoCarouselAdapter(this);
        carouselRecyclerView.setAdapter(adapter);

        CarouselLayoutManager layoutManager = new CarouselLayoutManager();
        carouselRecyclerView.setLayoutManager(layoutManager);


        ivUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userSetIntent = new Intent(InitMenu.this, UserSett.class);
                startActivity(userSetIntent);
            }
        });

        ivQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initScanner();
            }
        });

        ivLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(InitMenu.this)
                        .setTitle("Log Out")
                        .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dbGestor.deleteUserLog();
                                Toast.makeText(InitMenu.this, "Usuario eliminado", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InitMenu.this, Login.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


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
                qrExistOnDatabase(new QRExistCallback() {
                    @Override
                    public void onQRExist(boolean exist) {
                        if (exist) {
                            updateTableStatusAndNavigate();
                        } else {
                            Toast.makeText(v.getContext(), "Introduzca su mesa por QR", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    private void initScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setOrientationLocked(true);
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
                    qrNumber = qrContent;
                    qrExistOnDatabase(new QRExistCallback() {
                        @Override
                        public void onQRExist(boolean exist) {
                            if (!exist) {
                                Toast.makeText(InitMenu.this, "Mesa no encontrada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "El código QR no es un número válido", Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void qrExistOnDatabase(QRExistCallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tables");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                    Table table = tableSnapshot.getValue(Table.class);
                    if (table.getNumQR().equals(qrNumber)) {
                        String userLoged = ControladorUser.getMiController().getUser().getUserName();
                        if (table.getStatus()) {
                            if (userLoged.equals(table.getUserName())) {
                                proceedWithExistingUser(table);
                                callback.onQRExist(true);
                                return;
                            } else {
                                Toast.makeText(InitMenu.this, "Mesa ocupada por otro usuario", Toast.LENGTH_SHORT).show();
                                callback.onQRExist(false);
                                return;
                            }
                        } else {
                            tableSnapshot.getRef().child("userName").setValue(userLoged);
                            tableSnapshot.getRef().child("status").setValue(true);
                            proceedWithExistingUser(table);
                            callback.onQRExist(true);
                            return;
                        }
                    }
                }
                callback.onQRExist(false);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InitMenu.this, "Error al cargar mesas", Toast.LENGTH_SHORT).show();
                callback.onQRExist(false);
            }
        });
    }

    private void proceedWithExistingUser(Table table) {
        ivQrScan.setVisibility(View.GONE);
        tvTable.setText("Mesa: " + qrNumber);
        tvTable.setVisibility(View.VISIBLE);
        qrExist = true;
    }

    private void updateTableStatusAndNavigate() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tables");
        databaseReference.orderByChild("numQR").equalTo(qrNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                    tableSnapshot.getRef().child("status").setValue(true);
                    ControladorTable.getMiController().setQrTable(qrNumber);
                    Intent intent = new Intent(InitMenu.this, Menu.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InitMenu.this, "Error al actualizar el estado de la mesa", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private interface QRExistCallback {
        void onQRExist(boolean exist);
    }

}