package com.example.foodiepedia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.databinding.ItemLayoutRecipeRequestBinding;
import com.example.foodiepedia.databinding.ItemLayoutResepBinding;

import java.util.ArrayList;

public class RequestRecipeAdapter extends RecyclerView.Adapter<RequestRecipeAdapter.ViewHolder> {

    ArrayList<Resep> listresep;
    private onItemClickCallback onItemClickCallback;

    public RequestRecipeAdapter(ArrayList<Resep> listresep) {
        this.listresep = listresep;
    }

    public void setOnItemClickCallback(RequestRecipeAdapter.onItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutRecipeRequestBinding binding =ItemLayoutRecipeRequestBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Resep resep = listresep.get(position);
        holder.bind(resep);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.DetailResep(resep);
            }
        });

        holder.binding.buttondelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.DeleteResep(resep);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listresep.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayoutRecipeRequestBinding binding;
        public ViewHolder(@NonNull ItemLayoutRecipeRequestBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Resep resep) {
            binding.textnamaresep.setText(resep.getNama_resep());
            binding.textresepuser.setText(resep.getChef_resep());
        }
    }

    public interface onItemClickCallback{
        void DeleteResep(Resep resep);
        void DetailResep(Resep resep);
    }
}
