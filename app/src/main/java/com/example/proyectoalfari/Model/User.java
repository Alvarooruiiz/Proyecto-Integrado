package com.example.proyectoalfari.Model;

public class User {
    private String uid;
    private String userName;
    private String email;
    private String pass;
    private String date;

    public User() {
    }

    public User(String uid, String userName, String email, String pass, String date) {
        this.uid = uid;
        this.userName = userName;
        this.email = email;
        this.pass = pass;
        this.date = date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
