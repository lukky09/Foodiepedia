package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.foodiepedia.Adapter.UserRecipeAdapter;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    ArrayList<Resep> reseps = new ArrayList<>();
    RecyclerView rv;
    UserRecipeAdapter adapter;
    User curruser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        rv = findViewById(R.id.rvresultsearch);
        reseps = getIntent().getParcelableArrayListExtra("r");
        curruser = getIntent().getParcelableExtra("u");
        adapter = new UserRecipeAdapter(reseps, new UserRecipeAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Resep r, View v, int position) {
                Intent i = new Intent(SearchResultActivity.this, DetailResepActivity.class);
                i.putExtra("u",curruser);
                i.putExtra("r",r);
                finish();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Kembali ke beranda");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}