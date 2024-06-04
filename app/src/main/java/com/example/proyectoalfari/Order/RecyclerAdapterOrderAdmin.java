package com.example.proyectoalfari.Order;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.R;
import com.example.proyectoalfari.Table.RecyclerAdapterTable;

public class RecyclerAdapterOrderAdmin  extends RecyclerView.Adapter<RecyclerAdapterOrderAdmin.ViewHolder>{
    @NonNull
    @Override
    public RecyclerAdapterOrderAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOrderAdmin.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
