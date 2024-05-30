package com.example.proyectoalfari.Menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.Model.DishOrder;
import com.example.proyectoalfari.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class Menu extends AppCompatActivity implements RecyclerViewMenu.OnDishSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuPagerAdapter menuPagerAdapter;

    private ImageView ivShopIcon;

    private TextView tvMesa;

    private List<Dish> selectedDishes;

    public static Context context;

    private FirebaseFirestore db;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        FirebaseApp.initializeApp(this);
        selectedDishes = new ArrayList<>();

        db = FirebaseFirestore.getInstance();

        context = this;

        tvMesa = findViewById(R.id.tvMesa);
        tabLayout = findViewById(R.id.tabMenu);
        viewPager = findViewById(R.id.viewPager);
        ivShopIcon = findViewById(R.id.ivShopIcon);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

            ivShopIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBottomSheet();
                }
            });
    }

    public void showBottomSheet(){
        View view = getLayoutInflater().inflate(R.layout.selected_dish_list, null);
        RecyclerView recyclerView = view.findViewById(R.id.rvSelectedDishList);
        RecyclerAdapterOrderList adapter = new RecyclerAdapterOrderList(selectedDishes);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        Button btnMakeOrder = view.findViewById(R.id.btnMakeOrder);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        double total=0;
        for(Dish dish : selectedDishes){
            total += dish.getPrice();
        }
        tvTotalPrice.setText("Total: " + total + "€");

        String  mesa = tvMesa.getText().toString();
        btnMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(mesa);
            }
        });

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }

    private void showConfirmationDialog(String mesa) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Pedido")
                .setMessage("¿Estás seguro de que deseas realizar este pedido?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveOrderToFirestore(mesa);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void saveOrderToFirestore(String mesa) {
        DishOrder dishOrder = new DishOrder("1", mesa, selectedDishes);
        db.collection("orders")
                .add(dishOrder)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(context, "Pedido realizado con éxito", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Error al realizar el pedido: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void setupViewPager(ViewPager viewPager) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(DishListFragment.newInstance("Entrante", this));
        titles.add("Entrantes");

        fragments.add(DishListFragment.newInstance("Primero", this));
        titles.add("Primeros");

        fragments.add(DishListFragment.newInstance("Segundo", this));
        titles.add("Segundos");

        fragments.add(DishListFragment.newInstance("Postre", this));
        titles.add("Postres");

        fragments.add(DishListFragment.newInstance("Bebida", this));
        titles.add("Bebidas");

        menuPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments, titles);

        viewPager.setAdapter(menuPagerAdapter);
    }

    @Override
    public void onDishSelected(Dish dish) {
        selectedDishes.add(dish);
        Toast.makeText(this, "Plato añadido al carrito"  + selectedDishes.size(), Toast.LENGTH_SHORT).show();

    }
}
