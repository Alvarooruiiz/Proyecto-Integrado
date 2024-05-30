package com.example.proyectoalfari.Menu;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

public class DishListFragment extends Fragment {
    private static final String ARG_TYPE_DISH = "typeDish";

    private String typeDish;
    private RecyclerView recyclerView;
    private RecyclerViewMenu adapter;
    private List<Dish> dishList;
    private DatabaseReference databaseReference;
    private RecyclerViewMenu.OnDishSelectedListener onDishSelectedListener;

    public DishListFragment() {
    }

    public static Fragment newInstance(String typeDish, RecyclerViewMenu.OnDishSelectedListener listener) {
        DishListFragment fragment = new DishListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE_DISH, typeDish);
        fragment.setArguments(args);
        fragment.setOnDishSelectedListener(listener);
        return fragment;
    }

    public void setOnDishSelectedListener(RecyclerViewMenu.OnDishSelectedListener listener) {
        this.onDishSelectedListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_list, container, false);
        databaseReference = FirebaseDatabase.getInstance().getReference("Dish");
        dishList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        typeDish = getArguments().getString(ARG_TYPE_DISH);

        adapter = new RecyclerViewMenu(dishList, view.findViewById(R.id.cardViewDishDetail));
        adapter.setOnDishSelectedListener(onDishSelectedListener);
        recyclerView.setAdapter(adapter);
        loadDishesFromFirebase();
        return view;
    }

    private void loadDishesFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dishSnapshot : snapshot.getChildren()) {
                    if(dishSnapshot.child("typeDish").getValue(String.class).equals(typeDish)){
                        Dish dish = dishSnapshot.getValue(Dish.class);
                        dishList.add(dish);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error al cargar platos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
