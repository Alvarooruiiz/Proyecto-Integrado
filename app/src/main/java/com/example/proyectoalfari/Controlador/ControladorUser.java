package com.example.proyectoalfari.Controlador;

import com.example.proyectoalfari.Model.User;

public class ControladorUser {
    private static ControladorUser miController;
    private String language;
    private User user;


    public static ControladorUser getMiController() {
        if (ControladorUser.miController == null) {
            miController = new ControladorUser();}
        return miController;}



    private ControladorUser() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
