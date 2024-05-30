package com.example.proyectoalfari.Model;

import java.util.List;

public class DishOrder {
    private String id;
    private String idTable;
    private List<Dish> dishListOrder;

    public DishOrder() {
    }

    public DishOrder(String id, String idTable, List<Dish> dishListOrder ) {
        this.id = id;
        this.idTable = idTable;
        this.dishListOrder = dishListOrder;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTable() {
        return idTable;
    }

    public void setIdTable(String idTable) {
        this.idTable = idTable;
    }


    public List<Dish> getDishListOrder() {
        return dishListOrder;
    }

    public void setDishListOrder(List<Dish> dish) {
        this.dishListOrder = dish;
    }
}