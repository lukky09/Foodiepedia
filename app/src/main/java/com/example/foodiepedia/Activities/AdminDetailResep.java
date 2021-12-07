package com.example.foodiepedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodiepedia.databinding.ActivityAdminDetailResepBinding;

public class AdminDetailResep extends AppCompatActivity {

    private ActivityAdminDetailResepBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDetailResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}