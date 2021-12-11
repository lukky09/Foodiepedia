package com.example.foodiepedia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiepedia.Data.RequestBahan;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ItemLayoutRecipeBahanAdminBinding;

import java.util.ArrayList;

public class RequestBahanAdapter extends RecyclerView.Adapter<RequestBahanAdapter.ViewHolder> {

    ArrayList<RequestBahan> listrequestbahan;
    private onItemClickCallback onItemClickCallback;

    public RequestBahanAdapter(ArrayList<RequestBahan> listrequestbahan) {
        this.listrequestbahan = listrequestbahan;
    }

    public void setOnItemClickCallback(RequestBahanAdapter.onItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutRecipeBahanAdminBinding binding = ItemLayoutRecipeBahanAdminBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RequestBahan rb = listrequestbahan.get(position);
        holder.bind(rb);

        holder.binding.AcceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.AcceptBahan(rb);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listrequestbahan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemLayoutRecipeBahanAdminBinding binding;
        public ViewHolder(@NonNull ItemLayoutRecipeBahanAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(RequestBahan rb){
            binding.NamaBahan.setText(rb.getNama_bahan());
            if (rb.getStatusbahan() == 0) {
                binding.textstatus.setTextColor(binding.getRoot().getResources().getColor(R.color.orang));
                binding.textstatus.setText("Waiting");
                binding.AcceptButton.setVisibility(View.VISIBLE);
            }
            else{
                binding.textstatus.setTextColor(binding.getRoot().getResources().getColor(R.color.green));
                binding.textstatus.setText("Accepted");
                binding.AcceptButton.setVisibility(View.INVISIBLE);
            }
        }
    }

    public interface onItemClickCallback{
        void AcceptBahan(RequestBahan requestBahan);
    }
}
