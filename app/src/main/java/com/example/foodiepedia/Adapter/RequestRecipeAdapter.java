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
            binding.visibleparam.setVisibility(View.INVISIBLE);
            switch (resep.getStatusresep()){
                case (0):
                    binding.textstatusresep.setText("Waiting");
                    binding.visibleparam.setVisibility(View.VISIBLE);
                    break;
                case (1):
                    binding.textstatusresep.setText("Accepted");
                    break;
                default:
                    binding.textstatusresep.setText("Declined");
                    break;
            }
        }
    }

    public interface onItemClickCallback{
        void AcceptResep(Resep resep);
    }
}
