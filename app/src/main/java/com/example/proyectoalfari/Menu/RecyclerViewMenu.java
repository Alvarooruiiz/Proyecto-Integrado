package com.example.proyectoalfari.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
            showDishDetail(dish);
        });
    }

    public void showDishDetail(Dish dish) {
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

        cardViewContainer.setVisibility(View.VISIBLE);
        ivClose.setOnClickListener(v -> cardViewContainer.setVisibility(View.GONE));
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
