package com.example.foodiepedia.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ItemLayoutRecipesUserBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRecipeAdapter extends RecyclerView.Adapter<UserRecipeAdapter.iniAdapter>{

    ArrayList<Resep> reseps;
    OnItemClickCallback callback;

    public UserRecipeAdapter(ArrayList<Resep> reseps, OnItemClickCallback callback) {
        this.reseps = reseps;
        this.callback = callback;
    }

    @NonNull
    @Override
    public iniAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutRecipesUserBinding bind = ItemLayoutRecipesUserBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new iniAdapter(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull iniAdapter holder, int position) {
        Resep r = reseps.get(position);
        holder.bindReseptoView(r);
        holder.itemView.setOnClickListener(view -> callback.onItemClicked(r,view,position));
    }

    @Override
    public int getItemCount() {
        return reseps.size();
    }

    public class iniAdapter extends RecyclerView.ViewHolder {

        ItemLayoutRecipesUserBinding bind;

        public iniAdapter(@NonNull ItemLayoutRecipesUserBinding bindlol) {
            super(bindlol.getRoot());
            bind = bindlol;
        }

        void bindReseptoView(Resep r) {
            bind.tvfood.setText(r.getNama_resep());
            bind.tvcreator.setText("Chef : "+r.getChef_resep());
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Resep r,View v,int position);
    }
}
