package com.example.foodiepedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodiepedia.databinding.ActivityTambahResepBinding;

public class TambahResepActivity extends AppCompatActivity {
    ActivityTambahResepBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}