package com.example.proyectoalfari.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;

import java.util.List;

public class RecyclerAdapterOrderList extends RecyclerView.Adapter<RecyclerAdapterOrderList.ViewHolder> {
    private List<Dish> selectedDishes;

    public RecyclerAdapterOrderList(List<Dish> selectedDishes) {
        this.selectedDishes = selectedDishes;
    }

    @NonNull
    @Override
    public RecyclerAdapterOrderList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_dish_selected, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOrderList.ViewHolder holder, int position) {
        Dish dish = selectedDishes.get(position);
        holder.dishNameTextView.setText(dish.getName());
        holder.dishPriceTextView.setText(dish.getPrice().toString() + "€");
        holder.btnDeleteDish.setOnClickListener(v -> {
            selectedDishes.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return selectedDishes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dishNameTextView;
        TextView dishPriceTextView;

        Button btnDeleteDish;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dishNameTextView = itemView.findViewById(R.id.tvDishNameSelected);
            dishPriceTextView = itemView.findViewById(R.id.tvDishPriceSelected);
            btnDeleteDish = itemView.findViewById(R.id.btnDeleteDish);
        }
    }
}