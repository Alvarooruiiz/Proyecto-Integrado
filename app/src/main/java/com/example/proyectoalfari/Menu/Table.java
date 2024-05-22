package com.example.proyectoalfari.Menu;

public class Table {
    private int id;
    private int numQR;

    public Table(int id, int numQR) {
        this.id = id;
        this.numQR = numQR;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumQR() {
        return numQR;
    }

    public void setNumQR(int numQR) {
        this.numQR = numQR;
    }
}
