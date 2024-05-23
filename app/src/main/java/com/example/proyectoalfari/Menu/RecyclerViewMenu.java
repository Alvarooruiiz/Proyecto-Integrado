package com.example.proyectoalfari.Menu;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;

import java.util.List;

public class RecyclerViewMenu extends RecyclerView.Adapter<RecyclerViewMenu.ViewHolder>{
    private List<Dish> dishList;
    private List<Dish> selectedDishes;

    private CardView cardViewContainer;




    public RecyclerViewMenu(List<Dish> dishList, CardView cardViewContainer) {
        this.dishList = dishList;
        this.cardViewContainer = cardViewContainer;
    }

    @NonNull
    @Override
    public RecyclerViewMenu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_menu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewMenu.ViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.tvName.setText(dish.getName());
        Glide.with(holder.itemView.getContext())
                .load(dish.getImageUrl())
                .into(holder.ivImage);
        holder.btnAdd.setOnClickListener(v -> {
            selectedDishes.add(dish);
        });

        holder.ivImage.setOnClickListener(v -> {
            showDishDetail(holder.itemView,dish);
        });
    }

    private void addDishToSelected(Dish dish) {
        for (Dish selectedDish : selectedDishes) {
            if (selectedDish.getName().equals(dish.getName())) {
                selectedDish.setQuantity(selectedDish.getQuantity() + 1);
                return;
            }
        }
        selectedDishes.add(dish);
    }

    public void showDishDetail(View parentView,Dish dish) {

        AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
        LayoutInflater inflater = LayoutInflater.from(parentView.getContext());
        View detailView = inflater.inflate(R.layout.cardview_dish_detail, null);

        ImageView ivClose = cardViewContainer.findViewById(R.id.ivClose);
        ImageView ivDishDetailImage = cardViewContainer.findViewById(R.id.ivDishDetailImage);
        TextView tvDishDetailName = cardViewContainer.findViewById(R.id.tvDishDetailName);
        TextView tvDishDetailDescription = cardViewContainer.findViewById(R.id.tvDishDetailDescription);
        TextView tvDishDetailPrice = cardViewContainer.findViewById(R.id.tvDishDetailPrice);
        ImageView ibDetailCrustacean = cardViewContainer.findViewById(R.id.ibDetailCrustacean);
        ImageView ibDetailGluten = cardViewContainer.findViewById(R.id.ibDetailGluten);
        ImageView ibDetailEgg = cardViewContainer.findViewById(R.id.ibDetailEgg);
        ImageView ibDetailSesame = cardViewContainer.findViewById(R.id.ibDetailSesame);
        ImageView ibDetailNut = cardViewContainer.findViewById(R.id.ibDetailNut);
        ImageView ibDetailDairy = cardViewContainer.findViewById(R.id.ibDetailDairy);
        ImageView ibDetailFish = cardViewContainer.findViewById(R.id.ibDetailFish);
        ImageView ibDetailSoy = cardViewContainer.findViewById(R.id.ibDetailSoy);
        ImageView ibDetailMustard = cardViewContainer.findViewById(R.id.ibDetailMustard);

        Glide.with(cardViewContainer.getContext()).load(dish.getImageUrl()).into(ivDishDetailImage);
        tvDishDetailName.setText(dish.getName());
        tvDishDetailDescription.setText(dish.getAllergens());
        tvDishDetailPrice.setText(String.valueOf(dish.getPrice()));

        String allergens = dish.getAllergens();
        ibDetailCrustacean.setVisibility(allergens.contains("crustacean") ? View.VISIBLE : View.GONE);
        ibDetailGluten.setVisibility(allergens.contains("gluten") ? View.VISIBLE : View.GONE);
        ibDetailEgg.setVisibility(allergens.contains("egg") ? View.VISIBLE : View.GONE);
        ibDetailSesame.setVisibility(allergens.contains("sesame") ? View.VISIBLE : View.GONE);
        ibDetailNut.setVisibility(allergens.contains("nut") ? View.VISIBLE : View.GONE);
        ibDetailDairy.setVisibility(allergens.contains("dairy") ? View.VISIBLE : View.GONE);
        ibDetailFish.setVisibility(allergens.contains("fish") ? View.VISIBLE : View.GONE);
        ibDetailSoy.setVisibility(allergens.contains("soy") ? View.VISIBLE : View.GONE);
        ibDetailMustard.setVisibility(allergens.contains("mustard") ? View.VISIBLE : View.GONE);

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
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (dishList == null) return 0;
        return dishList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivImage;
        TextView tvName;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvName = itemView.findViewById(R.id.tvName);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}
