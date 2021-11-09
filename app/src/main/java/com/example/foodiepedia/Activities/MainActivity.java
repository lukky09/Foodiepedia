package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etuser,etpass;
    String adminusername,adminpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etuser = findViewById(R.id.etloginusername);
        etpass = findViewById(R.id.etloginpassword);
        adminusername = "admin";
        adminpass = "admin";
    }

    public void toregis(View view) {
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
        etuser.setText("");
        etpass.setText("");
    }

    public void attamptlogin(View view) {
        String username = etuser.getText().toString();
        String password = etpass.getText().toString();
        if(username.trim().length()==0||password.trim().length()==0){
            //kalo ada yng g keisi
            Toast.makeText(this,"Mohon diisi semuanya",Toast.LENGTH_SHORT).show();
        }else {
            if(username.equalsIgnoreCase(adminusername)){
                //coba login admin
                if(password.equals(adminpass)){
                    Intent i = new Intent(this,AdminHomeActivity.class);
                    startActivity(i);
                }else{
                    Toast.makeText(this,"Password salah",Toast.LENGTH_SHORT).show();
                }
            }else{
                //coba login user
                StringRequest sreq = new StringRequest(
                        Request.Method.POST,
                        getString(R.string.url),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                int iduser;
                                String msg;
                                try {
                                    JSONObject job = new JSONObject(response);
                                    iduser = job.getInt("userid");
                                    msg = job.getString("message");
                                    if(iduser>0){
                                        Intent i = new Intent(MainActivity.this,UserHomeActivity.class);
                                        startActivity(i);
                                    }else{
                                        Toast.makeText(MainActivity.this,msg,Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> param = new HashMap<>();
                        param.put("func","login");
                        param.put("user",username);
                        param.put("pass",password);
                        return param;
                    }
                };
                RequestQueue rqueue = Volley.newRequestQueue(this);
                rqueue.add(sreq);
            }
        }
    }
}