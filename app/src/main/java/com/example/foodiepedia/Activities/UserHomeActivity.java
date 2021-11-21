package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    User currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        getuser(getIntent().getIntExtra("id", -1));
    }

    void getuser(int userid) {
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
                        JSONObject job = new JSONObject(response);
                        currentuser = new User(getIntent().getIntExtra("id", -1), job.getString("nama"), job.getString("pass"));
                        ((TextView) findViewById(R.id.textView2)).setText("Welcome, " + currentuser.getUser_name());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(UserHomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getuser");
                param.put("userid", userid + "");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemuseradd:
                break;
            case R.id.itemuseredit:
                Intent i = new Intent(this, EditProfileActivity.class);
                i.putExtra("user", currentuser);
                startActivityForResult(i, 0);
                break;
            default:
                finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            getuser(currentuser.getUser_id());
        }
    }
}