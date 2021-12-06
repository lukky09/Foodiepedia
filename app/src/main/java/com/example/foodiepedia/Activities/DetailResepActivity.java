package com.example.foodiepedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;

public class DetailResepActivity extends AppCompatActivity {

    User curruser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resep);
    }
}