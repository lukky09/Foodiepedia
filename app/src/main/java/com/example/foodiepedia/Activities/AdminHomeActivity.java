package com.example.foodiepedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityAdminHomeBinding;

public class AdminHomeActivity extends AppCompatActivity {

    private ActivityAdminHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}