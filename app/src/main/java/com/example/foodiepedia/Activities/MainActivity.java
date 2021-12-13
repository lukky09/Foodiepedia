package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //EditText etuser, etpass;
    String adminusername, adminpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //etuser = findViewById(R.id.etloginusername);
        //etpass = findViewById(R.id.etloginpassword);
        adminusername = "admin";
        adminpass = "admin";
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences sharedPref = this.getSharedPreferences("setting", this.MODE_PRIVATE);
        if (sharedPref.getInt("id", -69) > 0) {
            Intent i = new Intent(MainActivity.this, UserHomeActivity.class);
            i.putExtra("id",sharedPref.getInt("id", -69));
            startActivity(i);
            finish();
        }
    }

    public void toregis(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
        binding.etloginusername.setText("");
        binding.etloginpassword.setText("");
    }

    public void attamptlogin(View view) {
        String username = binding.etloginusername.getText().toString();
        String password = binding.etloginpassword.getText().toString();
        if (username.trim().length() == 0 || password.trim().length() == 0) {
            //kalo ada yng g keisi
            Toast.makeText(this, "Mohon diisi semuanya", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest sreq = new StringRequest(
                    Request.Method.POST,
                    getString(R.string.url),
                    response -> {
                        int iduser,isadmin,ban;
                        String msg;
                        try {
                            JSONObject job = new JSONObject(response);
                            iduser = job.getInt("userid");
                            msg = job.getString("message");
                            ban = job.getInt("ban");
                            if (iduser > 0) {
                                isadmin = job.getInt("isadmin");
                                if (isadmin == 1) {
                                    Intent i = new Intent(MainActivity.this, AdminHomeActivity.class);
                                    startActivity(i);
                                } else {
                                    if(ban == 0) {
                                        SharedPreferences sharedPref = this.getSharedPreferences("setting", this.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putInt("id", iduser);
                                        editor.apply();
                                        Intent i = new Intent(MainActivity.this, UserHomeActivity.class);
                                        i.putExtra("id", iduser);
                                        startActivity(i);
                                        finish();
                                    }else{
                                        Toast.makeText(this, "Anda telah di-ban dari Foodiepedia, silahkan Hubungi Admin", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                binding.etloginusername.setText("");
                                binding.etloginpassword.setText("");
                            } else {
                                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("func", "login");
                    param.put("user", username);
                    param.put("pass", password);
                    return param;
                }
            };
            RequestQueue rqueue = Volley.newRequestQueue(this);
            rqueue.add(sreq);
        }
    }
}