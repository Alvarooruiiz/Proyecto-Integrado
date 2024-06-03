package com.example.proyectoalfari.Menu;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.Model.DishOrder;
import com.example.proyectoalfari.R;

import java.util.List;

public class RecyclerAdapterPaymentList extends RecyclerView.Adapter<RecyclerAdapterPaymentList.ViewHolder> {

    private List<Dish> dishes;

    public RecyclerAdapterPaymentList(List<Dish> dishes) {
        this.dishes = dishes;
    }

    @NonNull
    @Override
    public RecyclerAdapterPaymentList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_order_pay, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPaymentList.ViewHolder holder, int position) {
        Dish dish = dishes.get(position);
        holder.tvDishNameOrderPay.setText(dish.getName());
        holder.tvDishPriceOrderPay.setText(String.valueOf(dish.getPrice()));

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDishNameOrderPay;
        private TextView tvDishPriceOrderPay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDishNameOrderPay = itemView.findViewById(R.id.tvDishNameOrderPay);
            tvDishPriceOrderPay = itemView.findViewById(R.id.tvDishPriceOrderPay);
        }
    }
}
