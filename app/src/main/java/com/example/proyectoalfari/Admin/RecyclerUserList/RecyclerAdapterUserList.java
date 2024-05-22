package com.example.proyectoalfari.Admin.RecyclerUserList;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Menu.RecyclerViewMenu;
import com.example.proyectoalfari.Model.User;
import com.example.proyectoalfari.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class RecyclerAdapterUserList extends RecyclerView.Adapter<RecyclerAdapterUserList.ViewHolder>{
    private List<User> userList;

    public RecyclerAdapterUserList(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_user_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User user = userList.get(position);
        holder.tvUserName.setText(user.getUserName());
        holder.tvUserEmail.setText(user.getEmail());
        holder.tvUserBirth.setText(user.getDate());
        holder.btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Eliminar usuario")
                        .setMessage("¿Desea eliminar este usuario de la base de datos?")
                        .setPositiveButton("Aceptar", (dialog, which) -> {
                            deleteUserFromDatabase(user);
                            userList.remove(position);
                            notifyItemRemoved(position);
                            dialog.dismiss();
                        })
                        .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        });
    }

    public void deleteUserFromDatabase(User user) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User userDB = userSnapshot.getValue(User.class);
                    if (userDB.getEmail().equals(user.getEmail())) {
                        userSnapshot.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error al eliminar el usuario de la base de datos");
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserName;
        TextView tvUserEmail;
        TextView tvUserBirth;
        Button btnDeleteUser;
        public ViewHolder(@NonNull View parent) {
            super(parent);
            tvUserName = parent.findViewById(R.id.tvUserName);
            tvUserEmail = parent.findViewById(R.id.tvUserEmail);
            tvUserBirth = parent.findViewById(R.id.tvUserBirth);
            btnDeleteUser = parent.findViewById(R.id.btnDeleteUser);
        }
    }
}
