package com.example.proyectoalfari.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.R;

import java.util.List;

public class RecyclerViewMenu extends RecyclerView.Adapter<RecyclerViewMenu.ViewHolder>{
    private List<Dish> dishList;
    private List<Dish> selectedDishes;


    public RecyclerViewMenu(List<Dish> dishList) {
        this.dishList = dishList;
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
    }

    @Override
    public int getItemCount() {
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
