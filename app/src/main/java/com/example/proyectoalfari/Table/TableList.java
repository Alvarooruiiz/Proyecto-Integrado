package com.example.proyectoalfari.Table;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Admin.Admin;
import com.example.proyectoalfari.DataBase.AlfariDatabase;
import com.example.proyectoalfari.Model.Table;
import com.example.proyectoalfari.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TableList extends AppCompatActivity {

    private List<Table> tableList;
    private RecyclerAdapterTable adapter;
    RecyclerView rvTableList;
    private DatabaseReference databaseReference;

    private Button btnAddTable;
    private AlfariDatabase database;
    private boolean exist = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_mesas);

        btnAddTable = findViewById(R.id.btnAddTable);

        database = new AlfariDatabase();
        database.inicializateFirebase(this);

        tableList = new ArrayList<>();
        rvTableList = findViewById(R.id.rvTableList);
        adapter = new RecyclerAdapterTable(tableList);
        rvTableList.setAdapter(adapter);
        rvTableList.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("Tables");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tableList.clear();
                for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                    Table table = tableSnapshot.getValue(Table.class);
                    tableList.add(table);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TableList", "Error al obtener los datos de la base de datos");

            }
        });

        btnAddTable.setOnClickListener(new View.OnClickListener() {
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
                if (!tableTxt.isEmpty()) {
                    if (!qrExist(tableTxt)) {
                        saveTableToDatabase(tableTxt);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(TableList.this, "El código de mesa ya existe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TableList.this, "Ingrese un código de mesa", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void saveTableToDatabase(String tableCode) {
        Table table = new Table(UUID.randomUUID().toString(), tableCode);
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
                    String code = tableSnapshot.child("numQR").getValue(String.class);
                    if (code != null && code.equals(tableCode)) {
                        exist = true;
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TableList.this, "Error al cargar mesas", Toast.LENGTH_SHORT).show();
            }
        });
        return exist;
    }
}
