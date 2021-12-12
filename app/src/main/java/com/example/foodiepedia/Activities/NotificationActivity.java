package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Adapter.UserMessageAdapter;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityNotificationBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    ArrayList<String> message;
    ArrayList<Integer> secondtime;
    User curruser;
    UserMessageAdapter adapter;
    LinearLayout ll;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        ll = findViewById(R.id.llnotif);
        rv = findViewById(R.id.rvmess);
        curruser = getIntent().getParcelableExtra("u");

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Kembali ke beranda");

        getmessages();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    void getmessages(){
        StringRequest sreq = new StringRequest(
                Request.Method.POST,
                getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            message = new ArrayList<>();
                            secondtime = new ArrayList<>();
                            System.out.println(response);
                            JSONArray jar = new JSONArray(response);
                            for (int i = 0; i < jar.length(); i++) {
                                JSONObject job = jar.getJSONObject(i);
                                message.add(job.getString("mess"));
                                secondtime.add(job.getInt("secs"));
                            }
                            adapter = new UserMessageAdapter(message,secondtime);
                            ll.removeViewAt(0);
                            rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            rv.setAdapter(adapter);
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                },
                error -> Toast.makeText(NotificationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getmessages");
                param.put("id", curruser.getUser_id()+"");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }

    public void delet(View view) {
        StringRequest sreq = new StringRequest(
                Request.Method.POST,
                getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ll.removeViewAt(0);
                        TextView teet = new TextView(getApplicationContext());
                        teet.setText("Tidak ada notifikasi");
                        teet.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        teet.setTextColor(getColor(R.color.black));
                        teet.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
                        teet.setTypeface(null, Typeface.BOLD);
                        ll.addView(teet);
                    }
                },
                error -> Toast.makeText(NotificationActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("func", "delmessages");
                param.put("id", curruser.getUser_id()+"");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }
}