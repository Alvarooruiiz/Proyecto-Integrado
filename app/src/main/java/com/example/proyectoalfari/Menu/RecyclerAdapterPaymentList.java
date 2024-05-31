package com.example.proyectoalfari.Menu;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.DishOrder;
import com.example.proyectoalfari.R;

public class RecyclerAdapterPaymentList extends RecyclerView.Adapter<RecyclerAdapterPaymentList.ViewHolder> {

    private DishOrder dishOrder;

    public RecyclerAdapterPaymentList(DishOrder dishOrder) {
        this.dishOrder = dishOrder;
    }

    @NonNull
    @Override
    public RecyclerAdapterPaymentList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterPaymentList.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
    private TextView tvDishNameOrderPay;
    private TextView tvDishPriceOrderPay;
    private TextView tvAmountDish;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDishNameOrderPay = itemView.findViewById(R.id.tvDishNameOrderPay);
            tvDishPriceOrderPay = itemView.findViewById(R.id.tvDishPriceOrderPay);
            tvAmountDish = itemView.findViewById(R.id.tvAmountDish);
        }
    }
}
