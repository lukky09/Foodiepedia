package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.foodiepedia.Fragments.AdminBahanListFragment;
import com.example.foodiepedia.Fragments.AdminResepListFragment;
import com.example.foodiepedia.Fragments.AdminUserListFragment;
import com.example.foodiepedia.Fragments.ResepFragment;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityAdminHomeBinding;
import com.google.android.material.navigation.NavigationBarView;

public class AdminHomeActivity extends AppCompatActivity {

    private ActivityAdminHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navAdmin.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.menuAdminResep:
                        fragment = AdminResepListFragment.newInstance("Resep");
                        break;
                    case R.id.menuAdminBahan:
                        fragment = AdminBahanListFragment.newInstance();
                        break;
                    case R.id.menuAdminUsers:
                        fragment = AdminUserListFragment.newInstance();
                        break;
                    default:
                        finish();
                }
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(binding.adminContainer.getId(), fragment)
                            .commit();
                }catch (Exception e){
                    Log.e("UserHomeActivity", e.getMessage());
                }
                return true;
            }
        });
        binding.navAdmin.setSelectedItemId(R.id.menuAdminResep);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return true;
    }
}