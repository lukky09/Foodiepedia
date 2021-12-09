package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ceks = new ArrayList<>();
        ids = new ArrayList<>();
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        StringRequest sreq = new StringRequest(
                Request.Method.POST,
                getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONArray jar = null;
                        try {
                            jar = new JSONArray(response);
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
                            e.printStackTrace();
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

    }

    public void search(View view) {
    }
}