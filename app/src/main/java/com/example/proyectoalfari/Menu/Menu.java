package com.example.proyectoalfari.Menu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.proyectoalfari.Controlador.ControladorTable;
import com.example.proyectoalfari.DataBaseSQLite.SQLiteGestor;
import com.example.proyectoalfari.GestorEmail.CrearPDF;
import com.example.proyectoalfari.GestorEmail.SendEmailTask;
import com.example.proyectoalfari.InitMenu.InitMenu;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.Model.DishOrder;
import com.example.proyectoalfari.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.mail.MessagingException;

public class Menu extends AppCompatActivity implements RecyclerViewMenu.OnDishSelectedListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuPagerAdapter menuPagerAdapter;
    private ImageView ivShopIcon;
    private Button btnVerOrder;
    private TextView tvMesa;
    private List<Dish> selectedDishes;
    public Context context;
    private FirebaseFirestore db;
    private SQLiteGestor sqliteGestor;

    private List<DishOrder> orders = new ArrayList<>();
    private AlertDialog currentDialog;
    private BottomSheetDialog currentBottomSheetDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_layout);
        context = this;
        FirebaseApp.initializeApp(this);
        selectedDishes = new ArrayList<>();
        db = FirebaseFirestore.getInstance();


        sqliteGestor = new SQLiteGestor(this);

        List<Dish> listaDatabase = sqliteGestor.getDishes();

        if(listaDatabase!=null){
            selectedDishes.addAll(listaDatabase);
            DishOrder newOrder = new DishOrder();
            newOrder.setId(String.valueOf(orders.size() + 1));
            newOrder.setIdTable(ControladorTable.getMiController().getQrTable());
            newOrder.setDishListOrder(new ArrayList<>(selectedDishes));
            makeOrder(newOrder);
        }

        tvMesa = findViewById(R.id.tvMesa);
        tabLayout = findViewById(R.id.tabMenu);
        viewPager = findViewById(R.id.viewPager);
        ivShopIcon = findViewById(R.id.ivShopIcon);
        btnVerOrder = findViewById(R.id.btnVerOrder);

        tvMesa.setText("Mesa: " + ControladorTable.getMiController().getQrTable());

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        btnVerOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });

        ivShopIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrdersDialog();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (currentDialog != null && currentDialog.isShowing()) {
            currentDialog.dismiss();
        }
        if (currentBottomSheetDialog != null && currentBottomSheetDialog.isShowing()) {
            currentBottomSheetDialog.dismiss();
        }
    }

    private void showOrdersDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_order, null);
        RecyclerView recyclerViewOrders = dialogView.findViewById(R.id.rvOrderList);
        Button btnPay = dialogView.findViewById(R.id.btnPay);
        TextView tvTotalPrice = dialogView.findViewById(R.id.tvTotalPrice);
        double total = 0;

        List<Dish> allDishes = new ArrayList<>();
        for (DishOrder order : orders) {
            allDishes.addAll(order.getDishListOrder());
        }

        for (Dish d : allDishes) {
            total += d.getPrice();
        }

        tvTotalPrice.setText(total + "€ ");

        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(this));
        RecyclerAdapterPaymentList orderAdapter = new RecyclerAdapterPaymentList(allDishes);
        recyclerViewOrders.setAdapter(orderAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        currentDialog = builder.create();
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog2();
                currentDialog.dismiss();
            }
        });
        currentDialog.show();

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                orderAdapter.notifyDataSetChanged();
            }
        });
    }

    private void simulatePaymentProcess() throws MessagingException, IOException {
        String filePath = context.getExternalFilesDir(null) + "/factura.pdf";
        try {
            List<Dish> allDishes = new ArrayList<>();
            for (DishOrder order : orders) {
                allDishes.addAll(order.getDishListOrder());
            }
            CrearPDF.createInvoice(filePath, allDishes);
            sendEmail("Factura comida en Alfari", filePath);
            Log.e("ASDASD", "Correctyo");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        proceedWithPayment();
    }

    private void sendEmail(String mensaje, String path) {
        String emailEmisor = "alvaro.ruiz.enrique@gmail.com";
        String passwordEmisor = "likt hjet gkav gteb";
        new SendEmailTask(emailEmisor, passwordEmisor, mensaje, path).execute();
    }

    private void proceedWithPayment() {
        Toast.makeText(context, "Procesando pago...", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "La factura se está enviando al correo", Toast.LENGTH_SHORT).show();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, "¡Pago realizado con éxito!", Toast.LENGTH_SHORT).show();
                resetTableUserName();
                sqliteGestor.deleteDishes();
                orders.clear();
            }
        }, 3000);
    }

    private void resetTableUserName() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Tables");
        databaseReference.orderByChild("numQR").equalTo("111").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot tableSnapshot : snapshot.getChildren()) {
                    tableSnapshot.getRef().child("userName").setValue(null);
                    tableSnapshot.getRef().child("status").setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Menu.this, "Error al actualizar el estado de la mesa", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showBottomSheet() {
        View view = getLayoutInflater().inflate(R.layout.selected_dish_list, null);
        RecyclerView recyclerView = view.findViewById(R.id.rvSelectedDishList);
        RecyclerAdapterOrderList adapter = new RecyclerAdapterOrderList(selectedDishes);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        Button btnMakeOrder = view.findViewById(R.id.btnMakeOrder);
        TextView tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        double total = 0;
        for (Dish dish : selectedDishes) {
            total += dish.getPrice();
        }
        tvTotalPrice.setText("Total: " + total + "€");

        String mesa = tvMesa.getText().toString();
        DishOrder newOrder = new DishOrder();
        newOrder.setId(String.valueOf(orders.size() + 1));
        newOrder.setIdTable(mesa);
        newOrder.setDishListOrder(new ArrayList<>(selectedDishes));

        currentBottomSheetDialog = new BottomSheetDialog(context);
        currentBottomSheetDialog.setContentView(view);

        btnMakeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog(newOrder, currentBottomSheetDialog);
            }
        });

        currentBottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                if (bottomSheet != null) {
                    BottomSheetBehavior<?> behavior = BottomSheetBehavior.from(bottomSheet);
                    ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

                    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    Display display = windowManager.getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int screenHeight = size.y;

                    layoutParams.height = screenHeight / 2;
                    bottomSheet.setLayoutParams(layoutParams);
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        currentBottomSheetDialog.show();
    }

    private void showConfirmationDialog(DishOrder newOrder, BottomSheetDialog bottomSheetDialog) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Pedido")
                .setMessage("¿Estás seguro de que deseas realizar este pedido?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqliteGestor.addDishes(selectedDishes);
                        makeOrder(newOrder);
                        bottomSheetDialog.dismiss();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showConfirmationDialog2() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar Pago")
                .setMessage("¿Estás seguro de que deseas realizar el pago? Concluirá la comida de la mesa")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            simulatePaymentProcess();
                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        Intent intent = new Intent(Menu.this, InitMenu.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    public void makeOrder(DishOrder newOrder) {
        if (newOrder != null) {
            orders.add(newOrder);

//            Order order = new Order();
//            order.setIdOrder(UUID.randomUUID().toString());
//            order.setTableQR(ControladorTable.getMiController().getQrTable());


            selectedDishes.clear();
            Toast.makeText(context, "Pedido realizado", Toast.LENGTH_SHORT).show();
        }
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
        Toast.makeText(this, "Plato añadido al carrito" + selectedDishes.size(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar salida")
                .setMessage("¿Estás seguro de que deseas volver atrás? Los cambios se perderán.")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Menu.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}