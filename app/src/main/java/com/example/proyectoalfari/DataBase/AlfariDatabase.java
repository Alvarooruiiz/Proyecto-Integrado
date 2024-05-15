package com.example.proyectoalfari.DataBase;


import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AlfariDatabase {


    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public void inicializateFirebase(Context a){
        FirebaseApp.initializeApp(a);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return firebaseDatabase;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        this.firebaseDatabase = firebaseDatabase;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.databaseReference = databaseReference;
    }

    public void checkUserCredentials(String username, String password, OnLoginResultListener listener) {
        DatabaseReference userRef = databaseReference.child("User").child(username);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String storedPassword = dataSnapshot.child("pass").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(password)) {
                        listener.onLoginSuccess();
                    } else {
                        listener.onLoginFailed("Incorrect password");
                    }
                } else {
                    listener.onLoginFailed("User not found");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.onLoginFailed("Database error: " + databaseError.getMessage());
            }
        });
    }

    public interface OnLoginResultListener {
        void onLoginSuccess();
        void onLoginFailed(String errorMessage);
    }
}
