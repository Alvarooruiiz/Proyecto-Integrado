package com.example.proyectoalfari.Menu;

public class Table {
    private String id;
    private String numQR;

    public Table(String id, String numQR) {
        this.id = id;
        this.numQR = numQR;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumQR() {
        return numQR;
    }

    public void setNumQR(String numQR) {
        this.numQR = numQR;
    }
}
