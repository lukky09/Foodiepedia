package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityTambahResepBinding;

import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TambahResepActivity extends AppCompatActivity {

    ActivityTambahResepBinding binding;
    ArrayList<LinearLayout> daftarll;
    ArrayList<String> daftarbahan;
    ArrayList<Boolean> daftarudahada;
    User currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        daftarll = new ArrayList<>();
        daftarbahan = new ArrayList<>();
        daftarudahada = new ArrayList<>();
        currentuser = getIntent().getParcelableExtra("user");

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    public void addbahan(View view) {
        Intent i = new Intent(this, TambahBahanActivity.class);
        i.putStringArrayListExtra("b", daftarbahan);
        startActivityForResult(i, 0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            LinearLayout newll = (LinearLayout) getLayoutInflater().inflate(R.layout.item_layout_bahan, null);
            String eh = "";
            if(!data.getBooleanExtra("ada",false)){
                Toast.makeText(this, "Bila semua bahan anda sudah diapprove semua maka resep anda akan tampil secara otomatis", Toast.LENGTH_SHORT).show();
                eh=" (!)";
            }
            ((TextView) newll.getChildAt(1)).setText(data.getStringExtra("bhn")+eh);
            daftarbahan.add(data.getStringExtra("bhn"));
            daftarudahada.add(data.getBooleanExtra("ada",false));
            ((ImageView) newll.getChildAt(0)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < daftarll.size(); i++) {
                        if (daftarll.get(i).getChildAt(0) == view) {
                            daftarll.remove(i);
                            daftarbahan.remove(i);
                            daftarudahada.remove(i);
                            binding.llbahan.removeViewAt(i);
                            if (binding.llbahan.getChildCount() == 0) {
                                TextView tv = new TextView(TambahResepActivity.this);
                                tv.setText("Belum Ada Bahan");
                                tv.setLayoutParams(new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));
                                binding.llbahan.addView(tv);
                            }
                            break;
                        }
                    }
                }
            });
            if (daftarbahan.size() == 1) {
                binding.llbahan.removeAllViews();
            }
            daftarll.add(newll);
            binding.llbahan.addView(newll);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void addresep(View view) {
        if (daftarbahan.size() == 0) {
            Toast.makeText(this, "Belum ada bahan", Toast.LENGTH_SHORT).show();
        } else {
            boolean full = true;
            for (int i = 0; i < daftarll.size(); i++) {
                if (((EditText) daftarll.get(i).getChildAt(3)).getText().toString().trim().length() == 0) {
                    full = false;
                    break;
                }
            }
            if (!full || binding.etnamaresep.getText().toString().trim().length() == 0 || binding.etdeskripsi.getText().toString().trim().length() == 0) {
                Toast.makeText(this, "Mohon diisi semuanya", Toast.LENGTH_SHORT).show();
            } else {
                int ver = 1;
                for (boolean b:daftarudahada) {
                    if(!b) {
                        ver = 0;
                        break;
                    }
                }
                int finalVer = ver;
                StringRequest sreq = new StringRequest(
                        Request.Method.POST,
                        getString(R.string.url),
                        response -> {
                            int foodid = Integer.parseInt(response);
                            for (int i = 0; i < daftarbahan.size(); i++) {
                                int finalI = i;
                                StringRequest sreq2 = new StringRequest(Request.Method.POST,
                                        getString(R.string.url),
                                        response1 -> {
                                            if(Integer.parseInt(response1) == daftarbahan.size()){
                                                setResult(111);
                                                finish();
                                            }
                                        }, error -> Toast.makeText(TambahResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> param = new HashMap<>();
                                        param.put("func", "addresepings");
                                        param.put("id", foodid+"");
                                        param.put("nama", daftarbahan.get(finalI));
                                        param.put("jum", ((EditText)daftarll.get(finalI).getChildAt(3)).getText().toString().trim());
                                        return param;
                                    }
                                };
                                RequestQueue rqueue2 = Volley.newRequestQueue(this);
                                rqueue2.add(sreq2);
                            }
                        },
                        error -> Toast.makeText(TambahResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> param = new HashMap<>();
                        param.put("func", "addresep");
                        param.put("id", currentuser.getUser_id()+"");
                        param.put("desc", binding.etdeskripsi.getText().toString().trim());
                        param.put("nama", binding.etnamaresep.getText().toString().trim());
                        param.put("ada",  finalVer +"");
                        return param;
                    }
                };
                RequestQueue rqueue = Volley.newRequestQueue(this);
                rqueue.add(sreq);

            }
        }
    }
}