package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityTambahResepBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TambahResepActivity extends AppCompatActivity {

    ActivityTambahResepBinding binding;
    ArrayList<LinearLayout> daftarll;
    ArrayList<String> daftarbahan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTambahResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        daftarll = new ArrayList<>();
        daftarbahan = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
    }

    public void addbahan(View view) {
        Intent i = new Intent(this,TambahBahanActivity.class);
        i.putStringArrayListExtra("b",daftarbahan);
        startActivityForResult(i,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 1){
            LinearLayout newll = (LinearLayout) getLayoutInflater().inflate(R.layout.item_layout_bahan,null);
            ((TextView)newll.getChildAt(1)).setText(data.getStringExtra("bhn"));
            daftarbahan.add(data.getStringExtra("bhn"));
            ((ImageView)newll.getChildAt(0)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < daftarll.size(); i++) {
                        if(daftarll.get(i).getChildAt(0) == view){
                            daftarll.remove(i);
                            daftarbahan.remove(i);
                            binding.llbahan.removeViewAt(i);
                            break;
                        }
                    }
                }
            });
            if(daftarbahan.size()==1){
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
        if(daftarbahan.size()==0){
            Toast.makeText(this, "Belum ada bahan", Toast.LENGTH_SHORT).show();
        }else{
            boolean full = true;
            for (int i = 0; i < daftarll.size(); i++) {
                if(((EditText)daftarll.get(i).getChildAt(3)).getText().toString().trim().length()==0){
                    full = false;
                    break;
                }
            }
            if(!full){
                Toast.makeText(this, "Mohon diisi semuanya", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Yei :D", Toast.LENGTH_SHORT).show();
            }
        }
    }
}