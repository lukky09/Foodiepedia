package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText etuser, etpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etuser = findViewById(R.id.etregisusername);
        etpass = findViewById(R.id.etregispassword);
    }

    public void attemptregis(View view) {
        String username = etuser.getText().toString();
        String password = etpass.getText().toString();
        if (username.trim().length() == 0 || password.trim().length() == 0) {
            Toast.makeText(this, "Mohon diisi semua", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest sreq = new StringRequest(
                    Request.Method.POST,
                    getString(R.string.url),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Register Berhasil!")){
                                finish();
                            }
                            Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(RegisterActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> param = new HashMap<>();
                    param.put("func","regis");
                    param.put("user",username);
                    param.put("pass",password);
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