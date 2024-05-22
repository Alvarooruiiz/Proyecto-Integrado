package com.example.proyectoalfari.Menu;

public class Table {
    private int id;
    private String numQR;

    public Table(int id, String numQR) {
        this.id = id;
        this.numQR = numQR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumQR() {
        return numQR;
    }

    public void setNumQR(String numQR) {
        this.numQR = numQR;
    }
}
