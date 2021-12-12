package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.foodiepedia.databinding.ActivityRegisterBinding;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding registerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        registerBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(registerBinding.getRoot());
    }

    public void attemptregis(View view) {
        String username = registerBinding.etregisusername.getText().toString().trim();
        String password = registerBinding.etregispassword.getText().toString().trim();
        String passconf = registerBinding.etregispasswordconf.getText().toString().trim();
        String name = registerBinding.etregisviewedname.getText().toString().trim();
        if (username.length() == 0 || password.length() == 0 || passconf.length() == 0 || name.length() == 0) {
            Toast.makeText(this, "Mohon diisi semua", Toast.LENGTH_SHORT).show();
        } else if (!passconf.equals(password)) {
            Toast.makeText(this, "Inputan password tidak sama", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password minimal 8 digit", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest sreq = new StringRequest(
                    Request.Method.POST,
                    getString(R.string.url),
                    response -> {
                        if (response.equals("Register Berhasil!")) {
                            finish();
                        }
                        Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    },
                    error -> Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Nullable
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> param = new HashMap<>();
                    param.put("func", "regis");
                    param.put("user", username);
                    param.put("pass", password);
                    param.put("name", name);
                    return param;
                }
            };
            RequestQueue rqueue = Volley.newRequestQueue(this);
            rqueue.add(sreq);
        }
    }

    public void tologin(View view) {
        finish();
    }
}