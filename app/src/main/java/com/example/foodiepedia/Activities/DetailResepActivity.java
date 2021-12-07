package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityDetailResepBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailResepActivity extends AppCompatActivity {

    User curruser;
    Resep currresep;
    int rating = 0;
    boolean isfollowing,isfavorite;
    ActivityDetailResepBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resep);

        binding = ActivityDetailResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        curruser = getIntent().getParcelableExtra("u");
        currresep = getIntent().getParcelableExtra("r");

        if(curruser.getUser_id() == currresep.getIduser()){
            binding.lldetail.removeViewAt(2);
        }

        binding.tvnamaresepdetail.setText(currresep.getNama_resep());
        binding.tvchefdetail.setText("Chef : "+currresep.getChef_resep());
        binding.tvdeskdetail.setText("Deskripsi :\n"+currresep.getDesk_resep().replaceAll("<br />", "\n"));

        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
                        JSONObject job = new JSONObject(response);
                        rating = job.getInt("rat");
                        if(job.getInt("fav") == 1) isfavorite = true;
                        else isfavorite = false;
                        if(job.getInt("fol") == 1) isfollowing = true;
                        else isfollowing = false;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getresepdets");
                param.put("user", curruser.getUser_id() + "");
                param.put("food", currresep.getIdresep() + "");
                param.put("chef", currresep.getIduser() + "");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }

    public void follow(View view) {
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    if (response.equals("1")){
                        isfollowing = true;
                    }else{
                        isfollowing = false;
                    }
                }, error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "fol");
                param.put("user", curruser.getUser_id() + "");
                param.put("chef", currresep.getIduser() + "");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }
}