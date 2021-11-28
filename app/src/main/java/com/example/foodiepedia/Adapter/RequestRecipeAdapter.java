package com.example.foodiepedia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiepedia.databinding.ItemLayoutRecipeRequestBinding;
import com.example.foodiepedia.databinding.ItemLayoutResepBinding;

public class RequestRecipeAdapter extends RecyclerView.Adapter<RequestRecipeAdapter.ViewHolder> {


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutRecipeRequestBinding binding =ItemLayoutRecipeRequestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull ItemLayoutRecipeRequestBinding binding) {
            super(binding.getRoot());
        }
    }
}
