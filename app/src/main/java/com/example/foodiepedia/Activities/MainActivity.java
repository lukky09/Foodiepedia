package com.example.foodiepedia.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodiepedia.Classes.User;
import com.example.foodiepedia.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etuser,etpass;
    String adminusername,adminpass;
    //dikasih public static krn bakal digantikan ama database
    public static ArrayList<User> daftaruser = new ArrayList<>();

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
                for (User u: daftaruser) {
                    if(u.username.equalsIgnoreCase(username)){
                        if(u.password.equals(password)){
                            etuser.setText("");
                            etpass.setText("");
                            Intent i = new Intent(this,UserHomeActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(this,"Password salah",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }
        }
    }
}