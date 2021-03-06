package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivitySearchBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    ActivitySearchBinding binding;
    ArrayList<CheckBox> ceks;
    ArrayList<Integer> ids;
    User curruser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ceks = new ArrayList<>();
        ids = new ArrayList<>();
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        curruser = getIntent().getParcelableExtra("u");
        setContentView(binding.getRoot());

        StringRequest sreq = new StringRequest(
                Request.Method.POST,
                getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jar = new JSONArray(response);
                            ceks.clear();
                            ids.clear();
                            for (int i = 0; i < jar.length(); i++) {
                                JSONObject job = jar.getJSONObject(i);
                                CheckBox c = new CheckBox(getApplicationContext());
                                c.setText(job.getString("nama"));
                                binding.searchbahan.addView(c);
                                ceks.add(c);
                                ids.add(job.getInt("id"));
                            }
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                },
                error -> Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getapprovedings");
                param.put("verified", 1+"");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);

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

    public void search(View view) {
        JSONArray jarsearch = new JSONArray();
        for (int i = 0; i < ceks.size(); i++) {
            if(ceks.get(i).isChecked())jarsearch.put(ids.get(i));
        }
        System.out.println(jarsearch);
        StringRequest sreq = new StringRequest(
                Request.Method.POST,
                getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Resep> r = new ArrayList<>();
                            JSONArray jar = new JSONArray(response);
                            for (int i = 0; i < jar.length(); i++) {
                                JSONObject job = jar.getJSONObject(i);
                                r.add(new Resep(job.getInt("resep_id"),
                                        job.getInt("chef_id"),
                                        job.getString("resep_nama"),
                                        job.getString("resep_desk"),
                                        job.getString("chef_name"),
                                        job.getInt("resep_isapproved")));
                            }
                            Intent i = new Intent(SearchActivity.this,SearchResultActivity.class);
                            i.putParcelableArrayListExtra("r",r);
                            i.putExtra("u",curruser);
                            startActivity(i);
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(SearchActivity.this, "Tidak ditemukan item", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(SearchActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("func", "searchresep");
                param.put("key", binding.etresepsearch.getText().toString().trim());
                param.put("reseps", jarsearch+"");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }
}