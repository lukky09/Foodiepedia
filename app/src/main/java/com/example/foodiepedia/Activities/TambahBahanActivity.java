package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TambahBahanActivity extends AppCompatActivity {

    ListView lv;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayada, arraybaru;
    ArrayList<Boolean> arrayavail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_bahan);
        arrayada = getIntent().getStringArrayListExtra("b");
        arraybaru = new ArrayList<>();
        arrayavail = new ArrayList<>();
        lv = findViewById(R.id.lvbahan);
        setviews();
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Kembali ke menu resep");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_req_bahan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.additem) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Request Bahan");
            alert.setMessage("Nanti bahan akan diaccept oleh admin");

            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Request", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String in = input.getText().toString().trim();
                    if (in.length() == 0) {
                        Toast.makeText(TambahBahanActivity.this, "Nama bahan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean ada = false;
                        for (String nama : arraybaru) {
                            if (nama.equalsIgnoreCase(in)) {
                                ada = true;
                                break;
                            }
                        }
                        if (ada) {
                            Toast.makeText(TambahBahanActivity.this, "Bahan sudah ada", Toast.LENGTH_SHORT).show();
                        } else {
                            StringRequest sreq = new StringRequest(
                                    Request.Method.POST,
                                    getString(R.string.url),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(TambahBahanActivity.this, "PERINGATAN : Bahan yang baru dimasukkan harus diapprove oleh admin", Toast.LENGTH_SHORT).show();
                                            setviews();
                                        }
                                    },
                                    error -> Toast.makeText(TambahBahanActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() {
                                    Map<String, String> param = new HashMap<>();
                                    param.put("func", "adding");
                                    param.put("nama", in);
                                    return param;
                                }
                            };
                            RequestQueue rqueue = Volley.newRequestQueue(TambahBahanActivity.this);
                            rqueue.add(sreq);
                        }
                    }

                }
            });

            alert.show();
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void setviews() {
        StringRequest sreq = new StringRequest(
                Request.Method.POST,
                getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jar = new JSONArray(response);
                            arraybaru.clear();
                            arrayavail.clear();
                            for (int i = 0; i < jar.length(); i++) {
                                JSONObject job = jar.getJSONObject(i);
                                arraybaru.add(job.getString("nama"));
                                if (job.getInt("app") == 1) arrayavail.add(true);
                                else arrayavail.add(false);
                            }
                            adapter = new ArrayAdapter<>(TambahBahanActivity.this, android.R.layout.simple_list_item_1, arraybaru);
                            lv.setAdapter(adapter);
                            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    boolean ada = false;
                                    for (String s : arrayada) {
                                        if (s.equals(arraybaru.get(i))) {
                                            ada = true;
                                            break;
                                        }
                                    }
                                    if (ada) {
                                        Toast.makeText(TambahBahanActivity.this, "Bahan sudah dippilih", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Intent in = new Intent();
                                        in.putExtra("bhn", arraybaru.get(i));
                                        in.putExtra("ada",arrayavail.get(i));
                                        setResult(1, in);
                                        finish();
                                    }
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(TambahBahanActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getapprovedings");
                param.put("verified", 0+"");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }

}