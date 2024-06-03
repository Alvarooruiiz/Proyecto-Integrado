package com.example.proyectoalfari.Controlador;

import com.example.proyectoalfari.Model.User;

public class Controlador {
    private static Controlador miController;
    private String language;
    private User user;


    public static Controlador getMiController() {
        if (Controlador.miController == null) {
            miController = new Controlador();}
        return miController;}



    private Controlador() {}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
