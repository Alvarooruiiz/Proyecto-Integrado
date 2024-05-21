package com.example.proyectoalfari.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;
import com.google.android.material.tabs.TabLayout;
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

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuPagerAdapter menuPagerAdapter;

    private ImageView ivQrScan;
    private RecyclerViewMenu recyclerViewMenuAdapter;
    private RecyclerView rvMenuList;
    private List<Dish> dishList;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        ivQrScan = findViewById(R.id.ivQrScan);

        tabLayout = findViewById(R.id.tabMenu);
        viewPager = findViewById(R.id.viewPager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);


        dishList = new ArrayList<>();




        ivQrScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initScanner();
            }
        });



    }

    private void setupViewPager(ViewPager viewPager) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        // Add fragments and titles for each dish type
        fragments.add(DishListFragment.newInstance("Entrante"));
        titles.add("Entrantes");

        fragments.add(DishListFragment.newInstance("Primero"));
        titles.add("Primeros");

        fragments.add(DishListFragment.newInstance("Segundo"));
        titles.add("Segundos");

        fragments.add(DishListFragment.newInstance("Postre"));
        titles.add("Postres");

        fragments.add(DishListFragment.newInstance("Bebida"));
        titles.add("Bebidas");

        menuPagerAdapter = new MenuPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                fragments, titles);

        viewPager.setAdapter(menuPagerAdapter);
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
