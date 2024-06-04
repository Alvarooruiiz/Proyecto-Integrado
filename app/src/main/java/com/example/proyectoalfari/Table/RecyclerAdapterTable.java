package com.example.proyectoalfari.Table;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Menu.RecyclerAdapterOrderList;
import com.example.proyectoalfari.Model.Order;
import com.example.proyectoalfari.Model.Table;
import com.example.proyectoalfari.Order.RecyclerAdapterOrderAdmin;
import com.example.proyectoalfari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterTable extends RecyclerView.Adapter<RecyclerAdapterTable.ViewHolder> {

    private List<Table> listTable;

    public RecyclerAdapterTable(List<Table> listTable) {
        this.listTable = listTable;
    }

    @NonNull
    @Override
    public RecyclerAdapterTable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_table_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterTable.ViewHolder holder, int position) {
        Table table = listTable.get(position);
        holder.tvTableNumber.setText("Mesa: " + table.getNumQR());
        if (table.getStatus() == null) {
            table.setStatus(false);
        }
        if (table.getStatus().equals(true)) {
            holder.cvMesa.setCardBackgroundColor(Color.WHITE);
            holder.tvTableStatus.setText("Ocupado");
        } else {
            holder.cvMesa.setCardBackgroundColor(Color.GREEN);
            holder.tvTableStatus.setText("Libre");
        }
        generateQRCode(table.getNumQR(), holder.ivQrImage);

        if(table.getStatus().equals(true)) {
            holder.btnSeeOrder.setVisibility(View.VISIBLE);
        } else {
            holder.btnSeeOrder.setVisibility(View.GONE);
        }

        holder.btnSeeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderDetailDialog(v, table.getNumQR());
            }
        });
    }

    private void showOrderDetailDialog(View v, String tableQR) {
        LayoutInflater inflater = LayoutInflater.from(v.getContext());
        View dialogView = inflater.inflate(R.layout.order_admin_layout, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);
        TextView tvAdminTitle = dialogView.findViewById(R.id.tvAdminTitle);
        tvAdminTitle.setText("Pedidos de la mesa " + tableQR);

        RecyclerView recyclerViewOrders = dialogView.findViewById(R.id.rvOrderAmind);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(v.getContext()));
        List<Order> orderList = new ArrayList<>();

        RecyclerAdapterOrderAdmin adapter = new RecyclerAdapterOrderAdmin(orderList);
        recyclerViewOrders.setAdapter(adapter);

        DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference("Orders");
        ordersRef.orderByChild("tableQR").equalTo(tableQR).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot orderSnapshot : dataSnapshot.getChildren()) {
                    Order order = orderSnapshot.getValue(Order.class);
                    if (order != null) {
                        orderList.add(order);
                    }
                }
                if (orderList.isEmpty()) {
                    Toast.makeText(v.getContext(), "Aun no se ha realizado el pedido", Toast.LENGTH_SHORT).show();
                } else {
                    adapter.notifyDataSetChanged();
                    builder.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(v.getContext(), "Error al recuperar los pedidos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTable.size();
    }

    private void generateQRCode(String text, ImageView imageView) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            int size = 256;
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565);
            com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, size, size);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cvMesa;
        private TextView tvTableNumber;
        private TextView tvTableStatus;
        private ImageView ivQrImage;
        private Button btnSeeOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvMesa = itemView.findViewById(R.id.cvMesa);
            tvTableNumber = itemView.findViewById(R.id.tvTableNumber);
            tvTableStatus = itemView.findViewById(R.id.tvTableStatus);
            ivQrImage = itemView.findViewById(R.id.ivQrImage);
            btnSeeOrder = itemView.findViewById(R.id.btnSeeOrder);
        }
    }
}