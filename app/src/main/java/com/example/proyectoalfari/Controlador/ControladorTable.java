package com.example.proyectoalfari.Controlador;

public class ControladorTable {
    private static ControladorTable miController;
    private String qrTable;


    public static ControladorTable getMiController() {
        if (ControladorTable.miController == null) {
            miController = new ControladorTable();}
        return miController;}



    private ControladorTable() {}

    public String getQrTable() {
        return qrTable;
    }

    public void setQrTable(String qrTable) {
        this.qrTable = qrTable;
    }
}
