package com.example.proyectoalfari.Admin.RecyclerUserList;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoalfari.Model.User;

import java.util.List;

public class RecyclerAdapterUserList extends RecyclerView.Adapter<RecyclerAdapterUserList.ViewHolder>{
    private List<User> userList;

    public RecyclerAdapterUserList(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RecyclerAdapterUserList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterUserList.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull ViewGroup parent) {
            super(parent);
        }
    }
}
