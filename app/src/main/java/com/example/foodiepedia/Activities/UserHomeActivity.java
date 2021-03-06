package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.example.foodiepedia.Fragments.FavoritesFragment;
import com.example.foodiepedia.Fragments.ResepFragment;
import com.example.foodiepedia.Fragments.UserHomeFragment;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityUserHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    User currentuser;
    ActivityUserHomeBinding binding;
    Menu m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getuser(getIntent().getIntExtra("id", -1));
        binding.navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Fragment fragment;
                switch (id) {
                    case R.id.menuHome:
                        fragment = UserHomeFragment.newInstance(currentuser,false);
                        break;
                    case R.id.menuListResep:
                        fragment = ResepFragment.newInstance(currentuser);
                        break;
                    case R.id.menuFavorites:
                        fragment = FavoritesFragment.newInstance(currentuser);
                        break;
                    default:
                        fragment = UserHomeFragment.newInstance(currentuser,true);
                        break;
                }
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.frameLayoutuser.getId(), fragment)
                            .commit();
                }catch (Exception e){
                    Log.e("UserHomeActivity", e.getMessage());
                }
                return true;
            }
        });
        binding.navBar.setSelectedItemId(R.id.menuHome);
    }

    void getuser(int userid) {
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
                        JSONObject job = new JSONObject(response);
                        currentuser = new User(getIntent().getIntExtra("id", -1), job.getString("nama"), job.getString("pass"));
                        binding.navBar.setSelectedItemId(R.id.menuListResep);
                        binding.navBar.setSelectedItemId(R.id.menuHome);
                        changebellcolor();
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

    void changebellcolor(){
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    int eh = Integer.parseInt(response);
                    Drawable drawable = m.findItem(R.id.itemusernotif).getIcon();
                    drawable = DrawableCompat.wrap(drawable);
                    if(eh>0){
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,R.color.blu));
                    }else{
                        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,R.color.white));
                    }
                    m.findItem(R.id.itemusernotif).setIcon(drawable);
                }, error -> Toast.makeText(UserHomeActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "havemsg");
                param.put("id", currentuser.getUser_id() + "");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        m = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.itemuseradd:
                i = new Intent(this,TambahResepActivity.class);
                i.putExtra("user",currentuser);
                startActivityForResult(i,0);
                break;
            case R.id.itemuseredit:
                i = new Intent(this, EditProfileActivity.class);
                i.putExtra("user", currentuser);
                startActivityForResult(i, 0);
                break;
            case R.id.itemusersearch:
                i = new Intent(this, SearchActivity.class);
                i.putExtra("u", currentuser);
                startActivityForResult(i, 0);
                break;
            case R.id.itemusernotif:
                i = new Intent(this, NotificationActivity.class);
                i.putExtra("u", currentuser);
                startActivityForResult(i, 0);
                break;
            default:
                SharedPreferences sharedPref = this.getSharedPreferences("setting",this.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("id",-1);
                editor.apply();
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 111){
            Toast.makeText(this, "Resep sudah ditambahkan :D", Toast.LENGTH_SHORT).show();
        }
        getuser(currentuser.getUser_id());
    }
}