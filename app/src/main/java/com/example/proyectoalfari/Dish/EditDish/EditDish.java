package com.example.proyectoalfari.Dish.EditDish;

import android.content.Context;
import android.os.Bundle;
        import android.text.Editable;
        import android.text.TextWatcher;
        import android.widget.EditText;
        import android.widget.Toast;

        import androidx.annotation.NonNull;
        import androidx.annotation.Nullable;
        import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.Dish;
        import com.example.proyectoalfari.R;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class EditDish extends AppCompatActivity {

    private EditText searchEditText;
    private List<Dish> dishList;
    private DatabaseReference databaseReference;

    private EditDishRecyclerAdapter adapter;
    private RecyclerView rvEditDish;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_dish_layout);



        rvEditDish = findViewById(R.id.rvEditDish);

        databaseReference = FirebaseDatabase.getInstance().getReference("Dish");
        dishList = new ArrayList<>();

        rvEditDish.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new EditDishRecyclerAdapter(dishList, this);

        rvEditDish.setAdapter(adapter);
        searchEditText = findViewById(R.id.searchEditText);
        loadDishesFromFirebase();


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }



    private void filter(String text) {
        List<Dish> filteredList = new ArrayList<>();
        for (Dish dish : dishList) {
            if (dish.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(dish);
            }
        }
        adapter.filterList(filteredList);
    }

    public void loadDishesFromFirebase() {
        dishList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dishSnapshot : snapshot.getChildren()) {

                    Dish dish = dishSnapshot.getValue(Dish.class);
                    dishList.add(dish);


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EditDish.this, "Error al cargar platos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}