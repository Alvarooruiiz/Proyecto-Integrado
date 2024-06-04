package com.example.proyectoalfari.Order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.Order;
import com.example.proyectoalfari.R;
import com.example.proyectoalfari.Table.RecyclerAdapterTable;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class RecyclerAdapterOrderAdmin  extends RecyclerView.Adapter<RecyclerAdapterOrderAdmin.ViewHolder>{
    private List<Order> orderList;

    public RecyclerAdapterOrderAdmin(List<Order> orderList) {
        this.orderList = orderList;
    }
    @NonNull
    @Override
    public RecyclerAdapterOrderAdmin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_order_admin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOrderAdmin.ViewHolder holder, int position) {
        Order order = orderList.get(position);
        holder.tvNameDishOrderAdmin.setText(order.getDishName());
        holder.cbIsReady.setChecked(order.isReady());

        holder.cbIsReady.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference("Orders").child(order.getIdOrder());
            orderRef.child("ready").setValue(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNameDishOrderAdmin;
        CheckBox cbIsReady;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameDishOrderAdmin = itemView.findViewById(R.id.tvNameDishOrderAdmin);
            cbIsReady = itemView.findViewById(R.id.cbIsReady);
        }
    }
}
