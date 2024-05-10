package com.example.proyectoalfari.Model;

import android.net.Uri;

import java.util.ArrayList;

public class Dish {
    private String id;
    private String name;
    private String desc;
    private Uri image;
    private ArrayList<String> allergens;

    public Dish() {
    }

    public Dish(String id, String name, String desc, Uri image, ArrayList<String> allergens) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
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

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public ArrayList<String> getAllergens() {
        return allergens;
    }

    public void setAllergens(ArrayList<String> allergens) {
        this.allergens = allergens;
    }
}
