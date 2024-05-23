package com.example.proyectoalfari.Model;

public class DishOrder {
    private int id;
    private String idTable;
    private String nameUser;
    private String txtDishes;
    private Double price;

    public DishOrder() {
    }

    public DishOrder(int id, String idTable, String nameUser, String txtDishes, Double price) {
        this.id = id;
        this.idTable = idTable;
        this.nameUser = nameUser;
        this.txtDishes = txtDishes;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public String getTxtDishes() {
        return txtDishes;
    }

    public void setTxtDishes(String txtDishes) {
        this.txtDishes = txtDishes;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}