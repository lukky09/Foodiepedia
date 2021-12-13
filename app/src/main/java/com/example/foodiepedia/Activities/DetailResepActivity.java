package com.example.foodiepedia.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ActivityDetailResepBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DetailResepActivity extends AppCompatActivity {

    User curruser;
    Resep currresep;
    int rating = 0;
    String respond;
    boolean isfollowing, isfavorite, isMine, isStarted;
    ActivityDetailResepBinding binding;
    Menu option;
    MenuItem optFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDetailResepBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isfavorite = false;
        curruser = getIntent().getParcelableExtra("u");
        currresep = getIntent().getParcelableExtra("r");

        if (curruser.getUser_id() == currresep.getIduser()) {
            binding.lldetail.removeViewAt(6);
            binding.ratingBar.setIsIndicator(true);
        }
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
                        JSONArray jar = new JSONObject(response).getJSONArray("bahanbahan");
                        String listBahan = "";
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject job = jar.getJSONObject(i);
                            String Bahan = job.getString("bahan_nama") + " - " + job.getString("qty");
                            listBahan += Bahan + "\n";
                        }
                        binding.tvListBahan.setText(listBahan);
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getbahan");
                param.put("resep_id", currresep.getIdresep() + "");
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
        binding.tvnamaresepdetail.setText(currresep.getNama_resep());
        binding.tvchefdetail.setText("Chef : " + currresep.getChef_resep());
        binding.tvdeskdetail.setText("Deskripsi :\n" + currresep.getDesk_resep().replaceAll("<br />", "\n"));
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Kembali ke beranda");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(curruser.getUser_id()!=currresep.getIduser()) {
            MenuInflater menuInflater = getMenuInflater();
            checkFavorite(menuInflater, menu); // untuk mengecek apakah user ini sudah melakukan favorites pada resep ini atau blm
            optFav = option.findItem(R.id.optFav);
            StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                    response -> {
                        try {
                            JSONObject job = new JSONObject(response);
//                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            rating = job.getInt("rat");
                            if (job.getInt("fav") == 1) {
                                isfavorite = true;
                                optFav.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_24));
                            }
                            if (job.getInt("fol") == 1) {
                                isfollowing = true;
                                binding.btnfollow.setText("Unfollow");
                            }
                            else{
                                isfollowing = false;
                                binding.btnfollow.setText("follow");
                            }
                            rating = job.getInt("rat");
                            binding.ratingBar.setRating(rating);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("func", "getresepdets");
                    param.put("user", curruser.getUser_id() + "");
                    param.put("food", currresep.getIdresep() + "");
                    param.put("chef", currresep.getIduser() + "");
                    return param;
                }
            };
            RequestQueue rqueue = Volley.newRequestQueue(this);
            rqueue.add(sreq);
            if (rating == 0) {
                rating = -1;
            }
            isStarted = false;

            binding.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                    if (ratingBar.getRating() != 0) {
                        if (rating == 0) {
                            rating = Math.round(ratingBar.getRating());
                            insertRating(rating, curruser, currresep);
                        } else {
                            if (rating > 0 && isStarted) {
                                rating = Math.round(ratingBar.getRating());
                                updateRating(rating, curruser, currresep);
                            }
                            rating = Math.round(ratingBar.getRating());
                        }
                    }
                    isStarted = true;
                }
            });
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.optFav) {
            if (isfavorite) {
                optFav.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_border_24));
                isfavorite = false;
                insertFavorite("delete");
            } else {
                optFav.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_24));
                isfavorite = true;
                insertFavorite("insert");
            }
        } else if (item.getItemId() == R.id.optMsg) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            alert.setTitle("Kirim Pesan");
            final EditText input = new EditText(this);
            alert.setView(input);

            alert.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String in = input.getText().toString().trim();
                    if (in.length() == 0) {
                        Toast.makeText(DetailResepActivity.this, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    } else {
                        StringRequest sreq = new StringRequest(
                                Request.Method.POST,
                                getString(R.string.url),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(DetailResepActivity.this, response, Toast.LENGTH_SHORT).show();
                                    }
                                },
                                error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> param = new HashMap<>();
                                param.put("func", "makemsg");
                                param.put("idasal", curruser.getUser_id()+"");
                                param.put("idtuju", currresep.getIduser()+"");
                                param.put("msg", in);
                                return param;
                            }
                        };
                        RequestQueue rqueue = Volley.newRequestQueue(DetailResepActivity.this);
                        rqueue.add(sreq);
                    }

                }
            });

            alert.show();
        } else {
            finish();
        }
        return true;
    }

    public void insertRating(int rating, User user, Resep resep) {
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    Toast.makeText(DetailResepActivity.this, response, Toast.LENGTH_SHORT).show();
                }, error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "insertrating");
                param.put("rating", rating + "");
                param.put("userid", user.getUser_id() + "");
                param.put("resepid", resep.getIdresep() + "");
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void updateRating(int rating, User user, Resep resep) {
        StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
//                    Toast.makeText(DetailResepActivity.this, response, Toast.LENGTH_SHORT).show();
                }, error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "updaterating");
                param.put("rating", rating + "");
                param.put("userid", user.getUser_id() + "");
                param.put("resepid", resep.getIdresep() + "");
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    public void follow(View view) {
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    if (response.equals("1")) {
                        isfollowing = true;
                        binding.btnfollow.setText("Unfollow");
                    } else {
                        isfollowing = false;
                        binding.btnfollow.setText("follow");
                    }
                }, error -> Toast.makeText(DetailResepActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "fol");
                param.put("user", curruser.getUser_id() + "");
                param.put("chef", currresep.getIduser() + "");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(this);
        rqueue.add(sreq);
    }

    public void checkFavorite(MenuInflater menuInflater, Menu menu) {
        isMine = false;
        StringRequest req = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    if (response.toString().equalsIgnoreCase("favorites")) {
                        isfavorite = true;
                    } else {
                        isfavorite = false;
                        if (response.toString().equalsIgnoreCase("resep sendiri")) {
                            isMine = true;
                        }
                    }
                }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getfavorites");
                param.put("user_id", curruser.getUser_id() + "");
                param.put("resep_id", currresep.getIdresep() + "");
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);

        if (!isMine) {
            menuInflater.inflate(R.menu.option_menu_detail, menu);
            option = menu;
        }

    }

    public void insertFavorite(String query) {
        StringRequest req = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "insertfavorites");
                param.put("query", query);
                param.put("user_id", curruser.getUser_id() + "");
                param.put("resep_id", currresep.getIdresep() + "");
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(req);
    }
}