package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityEditProfileBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {

    ActivityEditProfileBinding binding;
    User currentuser;
    boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        for (int i = 0; i < binding.llpass.getChildCount(); i++) {
            EditText e = (EditText) binding.llpass.getChildAt(i);
            e.setClickable(false);
            e.setEnabled(false);
        }

        currentuser = getIntent().getParcelableExtra("user");
        binding.tvviewedname.setText("Nama Tampilan sekarang : " + currentuser.getUser_name());
        binding.etnewviewedname.setText(currentuser.getUser_name());

        binding.btncanceledit.setOnClickListener(view -> {
            setResult(0);
            finish();
        });
    }

    public void ablepasswordchange(View view) {
        checked = ((CheckBox)view).isChecked();
        for (int i = 0; i < binding.llpass.getChildCount(); i++) {
            EditText e = (EditText) binding.llpass.getChildAt(i);
            e.setClickable(checked);
            e.setEnabled(checked);
        }
    }

    public void editprofile(View view) {
        String newname = binding.etnewviewedname.getText().toString().trim();
        String oldpass = binding.etoldpassedit.getText().toString().trim();
        String newpass = binding.etnewpassedit.getText().toString().trim();
        String newpassconf = binding.etnewpassconfedit.getText().toString().trim();

        if(newname.length()==0 || (checked && (oldpass.length() == 0||newpass.length() == 0||newpassconf.length() == 0))){
            Toast.makeText(this, "Belum diisi semua vroh", Toast.LENGTH_SHORT).show();
        } else if(checked && !oldpass.equals(currentuser.getPassword())){
            Toast.makeText(this, "Password lama salah", Toast.LENGTH_SHORT).show();
        }else if(checked && newpass.length()<8){
            Toast.makeText(this, "Password minimal 8 digit", Toast.LENGTH_SHORT).show();
        } else if(checked && newpass.equals(oldpass)){
            Toast.makeText(this, "Password baru tidak boleh sama dengan password lama", Toast.LENGTH_SHORT).show();
        }else if(checked && newpass.equals(oldpass)){
            Toast.makeText(this, "Konfirmasi password baru salah", Toast.LENGTH_SHORT).show();
        }else{
            String updatepass;
            if(checked) updatepass ="y";
            else updatepass = "n";
            StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                    response -> {
                        try {
                            JSONObject job = new JSONObject(response);
                            if((job.getInt("code")==2 && checked) || (job.getInt("code")==1 && !checked)){
                                Toast.makeText(this, "Berhasil Update :D", Toast.LENGTH_SHORT).show();
                                binding.tvviewedname.setText("Nama Tampilan sekarang : " + newname);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> Toast.makeText(EditProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("func", "updateuser");
                    param.put("ispass", updatepass);
                    param.put("name", newname);
                    param.put("pass", newpass);
                    param.put("userid", currentuser.getUser_id() + "");
                    return param;
                }
            };
            RequestQueue rqueue = Volley.newRequestQueue(this);
            rqueue.add(sreq);
        }
    }
}