package com.example.proyectoalfari.GestorEmail;

import com.example.proyectoalfari.Controlador.Controlador;
import com.example.proyectoalfari.Model.Dish;
import com.example.proyectoalfari.Model.User;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
public class CrearPDF {
    public static void createInvoice(String dest, List<Dish> dishes) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(dest);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String formattedDate = formatter.format(new Date());

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        Paragraph title = new Paragraph("Factura")
                .setFontSize(20)
                .setBold()
                .setMarginBottom(20);
        document.add(title);

        document.add(new Paragraph("Nombre: " + Controlador.getMiController().getUser().getUserName()));
        document.add(new Paragraph("Email: " + Controlador.getMiController().getUser().getEmail()));
        document.add(new Paragraph("Fecha de hoy: " + formattedDate));
        document.add(new Paragraph("\n"));

        Table table = new Table(UnitValue.createPercentArray(new float[]{3, 1}))
                .useAllAvailableWidth();
        table.addHeaderCell("Plato");
        table.addHeaderCell("Precio");

        for (Dish dish : dishes) {
            table.addCell(dish.getName());
            table.addCell(String.valueOf(dish.getPrice()) + "€");
        }

        document.add(table);

        double total = dishes.stream().mapToDouble(Dish::getPrice).sum();
        document.add(new Paragraph("\nTotal: " + total + "€").setBold());

        document.close();
    }
}
