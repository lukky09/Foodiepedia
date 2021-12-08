package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityAdminDetailResepBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AdminDetailResep extends AppCompatActivity {

    private ActivityAdminDetailResepBinding binding;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDetailResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        i = getIntent();

        if (i.hasExtra("idresep")){
            //System.out.println(i.getIntExtra("idresep",-1)+"");
            getdata(i.getIntExtra("idresep",-1));
        }

    }

    private void getdata(int i){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject resep = jsonObject.getJSONObject("resep");
                            binding.judulresep.setText(resep.getString("resep_nama"));
                            binding.rating.setNumStars(jsonObject.getInt("rating"));
                            binding.chefresep.setText("by : " + resep.getString("chef_name"));
                            binding.textdeskripsi.setText(resep.getString("resep_desk"));
                            System.out.println(resep);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("func","admingetresepdetail");
                params.put("idresep", String.valueOf(i));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}