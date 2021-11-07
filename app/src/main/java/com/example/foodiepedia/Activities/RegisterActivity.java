package com.example.foodiepedia.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodiepedia.Classes.User;
import com.example.foodiepedia.R;

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
            boolean ada = false;
            for (User u : MainActivity.daftaruser) {
                if (u.username.equalsIgnoreCase(username)) {
                    ada = true;
                    break;
                }
            }
            if (ada) {
                Toast.makeText(this, "Username telah dipakai", Toast.LENGTH_SHORT).show();
            } else {
                //regis user baru lalu langsung mbalek mainactivity
                MainActivity.daftaruser.add(new User(username, password));
                finish();
            }
        }
    }

    public void tologin(View view) {
        finish();
    }
}