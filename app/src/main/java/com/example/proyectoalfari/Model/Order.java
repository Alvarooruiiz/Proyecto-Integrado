package com.example.proyectoalfari.Model;

public class Order {

    private String idOrder;
    private String tableQR;
    private String dishName;
    private boolean isReady;

    public Order() {
    }

    public Order(String idOrder, String tableQR, String dishName) {
        this.idOrder = idOrder;
        this.tableQR = tableQR;
        this.dishName = dishName;
        this.isReady = false;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getTableQR() {
        return tableQR;
    }

    public void setTableQR(String tableQR) {
        this.tableQR = tableQR;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }
}

