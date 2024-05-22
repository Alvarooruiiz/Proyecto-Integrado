package com.example.proyectoalfari.Model;

public class Table {
    private String id;
    private String numQR;
    private Boolean status;

    public Table() {
    }

    public Table(String id, String numQR) {
        this.id = id;
        this.numQR = numQR;
        this.status = false;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
