package com.example.proyectoalfari.Model;

import android.net.Uri;

import java.util.ArrayList;

public class Dish {
    private String name;
    private String desc;
    private String imageUrl;
    private String allergens;
    private Double price;
    private String typeDish;
    private int quantity;

    public Dish() {
    }

    public Dish(String id, String name, String desc, String imageUrl, Double price,String typeDish, String allergens, int quantity) {
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.price = price;
        this.typeDish = typeDish;
        this.allergens = allergens;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTypeDish() {
        return typeDish;
    }

    public void setTypeDish(String typeDish) {
        this.typeDish = typeDish;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}