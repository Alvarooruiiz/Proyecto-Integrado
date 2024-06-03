package com.example.proyectoalfari.InitMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.R;
import com.google.android.material.carousel.MaskableFrameLayout;

public class PhotoCarouselAdapter extends RecyclerView.Adapter<PhotoCarouselAdapter.PhotoViewHolder>  {

    private Context context;
    private int[] photoList = {R.drawable.card_cocteles, R.drawable.slide3, R.drawable.slide2};

    public PhotoCarouselAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_layout, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        holder.carouselImageView.setImageResource(photoList[position]);
    }

    @Override
    public int getItemCount() {
        return photoList.length;
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        MaskableFrameLayout carouselItemContainer;
        ImageView carouselImageView;

        PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            carouselItemContainer = itemView.findViewById(R.id.carousel_item_container);
            carouselImageView = itemView.findViewById(R.id.carousel_image_view);
        }
    }
}