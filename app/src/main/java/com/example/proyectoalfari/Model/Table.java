package com.example.proyectoalfari.Model;

import java.util.List;

public class Table {
    private String id;
    private String numQR;
    private Boolean status;
    private String userName;

    public Table() {
    }

    public Table(String id, String numQR) {
        this.id = id;
        this.numQR = numQR;
        this.status = false;
        this.userName = null;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
