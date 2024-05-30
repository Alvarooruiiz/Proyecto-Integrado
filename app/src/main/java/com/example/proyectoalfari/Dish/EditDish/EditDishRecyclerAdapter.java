package com.example.proyectoalfari.Dish.EditDish;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EditDishRecyclerAdapter extends RecyclerView.Adapter<EditDishRecyclerAdapter.ViewHolder> {
    private List<Dish> dishList;
    private EditDish con;
    public EditDishRecyclerAdapter(List<Dish> dishList, EditDish con) {
        this.dishList = dishList;
        this.con = con;
    }


    @NonNull
    @Override
    public EditDishRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_edit_dish, parent, false);
        return new EditDishRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.tvName.setText(dish.getName());
        Glide.with(holder.itemView.getContext())
                .load(dish.getImageUrl())
                .into(holder.ivImage);
        holder.btnEdit.setOnClickListener(v -> {
            editDishDialog(holder.itemView,dish);
        });

    }

    public void editDishDialog(View parentView,Dish dish) {
        ArrayList<String> allergensList = new ArrayList<>();

        AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
        LayoutInflater inflater = LayoutInflater.from(parentView.getContext());
        View detailView = inflater.inflate(R.layout.cardview_edit_dish, null);

        ImageView ivClose = detailView.findViewById(R.id.ivClose2);
        ImageView ivDishDetailImage = detailView.findViewById(R.id.ivDishDetailImage2);
        EditText tvDishDetailName = detailView.findViewById(R.id.tvDishDetailName2);
        EditText tvDishDetailDescription = detailView.findViewById(R.id.tvDishDetailDescription2);
        EditText tvDishDetailPrice = detailView.findViewById(R.id.tvDishDetailPrice2);
        ImageView ibDetailCrustacean = detailView.findViewById(R.id.ibDetailCrustacean2);
        ImageView ibDetailGluten = detailView.findViewById(R.id.ibDetailGluten2);
        ImageView ibDetailEgg = detailView.findViewById(R.id.ibDetailEgg2);
        ImageView ibDetailSesame = detailView.findViewById(R.id.ibDetailSesame2);
        ImageView ibDetailNut = detailView.findViewById(R.id.ibDetailNut2);
        ImageView ibDetailDairy = detailView.findViewById(R.id.ibDetailDairy2);
        ImageView ibDetailFish = detailView.findViewById(R.id.ibDetailFish2);
        ImageView ibDetailSoy = detailView.findViewById(R.id.ibDetailSoy2);
        ImageView ibDetailMustard = detailView.findViewById(R.id.ibDetailMustard2);
        Button btnEdit = detailView.findViewById(R.id.btnEdit2);

        Glide.with(detailView.getContext()).load(dish.getImageUrl()).into(ivDishDetailImage);
        tvDishDetailName.setText(dish.getName());
        tvDishDetailDescription.setText(dish.getAllergens());
        tvDishDetailPrice.setText(String.valueOf(dish.getPrice()));

        String allergens = dish.getAllergens();
        if(allergens.contains("crustacean")) ibDetailCrustacean.setImageResource(R.drawable.crustacean_icon); allergensList.add("crustacean");
        if(allergens.contains("gluten")) ibDetailGluten.setImageResource(R.drawable.gluten_icon); allergensList.add("gluten");
        if(allergens.contains("sesame")) ibDetailSesame.setImageResource(R.drawable.sesame_icon); allergensList.add("sesame");
        if(allergens.contains("egg")) ibDetailEgg.setImageResource(R.drawable.egg_icon); allergensList.add("egg");
        if(allergens.contains("nut")) ibDetailNut.setImageResource(R.drawable.nuts_icon); allergensList.add("nut");
        if(allergens.contains("dairy")) ibDetailDairy.setImageResource(R.drawable.dairy_icon); allergensList.add("dairy");
        if(allergens.contains("fish")) ibDetailFish.setImageResource(R.drawable.fish_icon); allergensList.add("fish");
        if(allergens.contains("soy")) ibDetailSoy.setImageResource(R.drawable.soy_icon); allergensList.add("soy");
        if(allergens.contains("mustard")) ibDetailMustard.setImageResource(R.drawable.mustard_icon); allergensList.add("mustard");





        builder.setView(detailView);
        AlertDialog dialog = builder.create();

        dialog.show();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.dimAmount = 0.7f;
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setAttributes(layoutParams);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDishOnFireBase(dish,detailView,allergensList);
                dialog.dismiss();

            }
        });

        ivClose.setOnClickListener(v -> dialog.dismiss());

    }

    public void editDishOnFireBase(Dish dish,View v, List<String> allergensList){

        EditText tvDishDetailName= v.findViewById(R.id.tvDishDetailName2);
        String newName = tvDishDetailName.getText().toString();
        EditText tvDishDetailDescription2= v.findViewById(R.id.tvDishDetailDescription2);
        String newDesc = tvDishDetailDescription2.getText().toString();
        EditText tvDishDetailPrice= v.findViewById(R.id.tvDishDetailPrice2);
        String newPrice = tvDishDetailPrice.getText().toString();

        ImageView ibDetailCrustacean = v.findViewById(R.id.ibDetailCrustacean2);
        ImageView ibDetailGluten = v.findViewById(R.id.ibDetailGluten2);
        ImageView ibDetailEgg = v.findViewById(R.id.ibDetailEgg2);
        ImageView ibDetailSesame = v.findViewById(R.id.ibDetailSesame2);
        ImageView ibDetailNut = v.findViewById(R.id.ibDetailNut2);
        ImageView ibDetailDairy = v.findViewById(R.id.ibDetailDairy2);
        ImageView ibDetailFish = v.findViewById(R.id.ibDetailFish2);
        ImageView ibDetailSoy = v.findViewById(R.id.ibDetailSoy2);
        ImageView ibDetailMustard = v.findViewById(R.id.ibDetailMustard2);


        ibDetailSesame.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailSesame.setImageResource(R.drawable.sesame_icon);
                    allergensList.remove("sesame");
                } else {
                    ibDetailSesame.setImageResource(R.drawable.sesame_oscuro_icon);
                    allergensList.add("sesame");
                }
            }
        });

        ibDetailGluten.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailGluten.setImageResource(R.drawable.gluten_oscuro_icon);
                    allergensList.remove("gluten");
                } else {
                    ibDetailGluten.setImageResource(R.drawable.gluten_icon);
                    allergensList.add("gluten");
                }
            }
        });
        ibDetailCrustacean.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailCrustacean.setImageResource(R.drawable.crustaceo_oscuro_icon);
                    allergensList.remove("crustacean");
                } else {
                    ibDetailCrustacean.setImageResource(R.drawable.crustacean_icon);
                    allergensList.add("crustacean");
                }
            }
        });
        ibDetailEgg.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailEgg.setImageResource(R.drawable.egg_oscuro_icon);
                    allergensList.remove("egg");
                } else {
                    ibDetailEgg.setImageResource(R.drawable.egg_icon);
                    allergensList.add("egg");
                }
            }
        });
        ibDetailNut.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailNut.setImageResource(R.drawable.nuts_oscuro_icon);
                    allergensList.remove("nuts");
                } else {
                    ibDetailNut.setImageResource(R.drawable.nuts_icon);
                    allergensList.add("nuts");
                }
            }
        });
        ibDetailDairy.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailDairy.setImageResource(R.drawable.dairy_oscuro_icon);
                    allergensList.remove("dairy");
                } else {
                    ibDetailDairy.setImageResource(R.drawable.dairy_icon);
                    allergensList.add("dairy");
                }
            }
        });
        ibDetailSoy.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailSoy.setImageResource(R.drawable.soy_oscuro_icon);
                    allergensList.remove("soy");
                } else {
                    ibDetailSoy.setImageResource(R.drawable.soy_icon);
                    allergensList.add("soy");
                }
            }
        });
        ibDetailFish.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailFish.setImageResource(R.drawable.fish_oscuro_icon);
                    allergensList.remove("fish");
                } else {
                    ibDetailFish.setImageResource(R.drawable.fish_icon);
                    allergensList.add("fish");
                }
            }
        });
        ibDetailMustard.setOnClickListener(new View.OnClickListener() {
            boolean isPressed = true;

            @Override
            public void onClick(View v) {
                isPressed = !isPressed;
                if (isPressed) {
                    ibDetailMustard.setImageResource(R.drawable.mustard_oscuro_icon);
                    allergensList.remove("mustard");
                } else {
                    ibDetailMustard.setImageResource(R.drawable.mustard_icon);
                    allergensList.add("mustard");
                }
            }
        });

        StringBuilder allergensString= new StringBuilder();
        for(String s : allergensList){
            allergensString.append(s);
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Dish").child(dish.getId());

        dish.setName(newName);

        dish.setDesc(newDesc);
        dish.setPrice(Double.parseDouble(newPrice));

        databaseReference.setValue(dish).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.e("FirebaseUpdate", "Se modificó correctamente el plato", task.getException());
                con.loadDishesFromFirebase();
                notifyDataSetChanged();
            } else {
                Log.e("FirebaseUpdate", "Error al actualizar el plato", task.getException());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public void filterList(List<Dish> filteredList) {
        dishList = filteredList;
        notifyDataSetChanged();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivImage;
        TextView tvName;
        Button btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.ivImage2);
            tvName = itemView.findViewById(R.id.tvName2);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}
