package com.example.foodiepedia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiepedia.Data.Bahan;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.databinding.ItemLayoutRecipeDetailBahanAdminBinding;
import com.example.foodiepedia.databinding.ItemLayoutRecipeRequestBinding;

import java.util.ArrayList;

public class AdmindetailrecipebahanAdapter extends RecyclerView.Adapter<AdmindetailrecipebahanAdapter.ViewHolder> {

    ArrayList<Bahan> listbahan;

    public AdmindetailrecipebahanAdapter(ArrayList<Bahan> listbahan) {
        this.listbahan = listbahan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutRecipeDetailBahanAdminBinding binding = ItemLayoutRecipeDetailBahanAdminBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bahan bahan = listbahan.get(position);
        holder.bind(bahan);
    }

    @Override
    public int getItemCount() {
        return listbahan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayoutRecipeDetailBahanAdminBinding binding;
        public ViewHolder(@NonNull ItemLayoutRecipeDetailBahanAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Bahan bahan){
            binding.textnamabahan.setText(bahan.getNama_bahan());
            binding.textqty.setText(bahan.getQty()+"");
        }
    }
}
