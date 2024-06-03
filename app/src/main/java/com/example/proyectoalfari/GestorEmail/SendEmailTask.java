package com.example.proyectoalfari.GestorEmail;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.proyectoalfari.Controlador.Controlador;

import java.io.IOException;

import jakarta.mail.MessagingException;

public class SendEmailTask extends AsyncTask<Void, Void, Boolean> {
    private String emailEmisor;
    private String passwordEmisor;
    private String mensaje;
    private String path;

    public SendEmailTask(String emailEmisor, String passwordEmisor, String mensaje, String path) {
        this.emailEmisor = emailEmisor;
        this.passwordEmisor = passwordEmisor;
        this.mensaje = mensaje;
        this.path = path;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            GestorEmail email = new GestorEmail();
            email.enviarMensajeConAdjunto(emailEmisor, Controlador.getMiController().getUser().getEmail(), "Factura", mensaje, emailEmisor, passwordEmisor,path);
            return true;
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.e("correo", "Email enviado correctamente");
        } else {
            Log.e("correo", "Email no se ha podido enviar");

        }
    }
}