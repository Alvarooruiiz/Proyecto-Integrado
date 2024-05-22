package com.example.proyectoalfari.Table;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.Table;
import com.example.proyectoalfari.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.List;

public class RecyclerAdapterTable extends RecyclerView.Adapter<RecyclerAdapterTable.ViewHolder>{

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
        if(table.getStatus()==null){
            table.setStatus(false);
        }
        if (table.getStatus().equals("true")) {
            holder.cvMesa.setCardBackgroundColor(Color.WHITE);
            holder.tvTableStatus.setText("Ocupado");

        } else {
            holder.cvMesa.setCardBackgroundColor(Color.GREEN);
            holder.tvTableStatus.setText("Libre");
        }
        generateQRCode(table.getNumQR(), holder.ivQrImage);

        holder.btnTableOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(table.getStatus().equals("true")){
                    Toast.makeText(v.getContext(), "La mesa está libre", Toast.LENGTH_SHORT).show();
                }
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
        private Button btnTableOrder;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cvMesa = itemView.findViewById(R.id.cvMesa);
            tvTableNumber = itemView.findViewById(R.id.tvTableNumber);
            tvTableStatus = itemView.findViewById(R.id.tvTableStatus);
            ivQrImage = itemView.findViewById(R.id.ivQrImage);
            btnTableOrder = itemView.findViewById(R.id.btnTableOrder);
        }
    }
}
