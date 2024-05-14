package com.example.proyectoalfari.Model;

import android.net.Uri;

import java.util.ArrayList;

public class Dish {
    private String id;
    private String name;
    private String desc;
    private String imageUrl;
    private String allergens;
    private Double price;

    public Dish() {
    }

    public Dish(String id, String name, String desc, String imageUrl, Double price, String allergens) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.price = price;
        this.allergens = allergens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }
}