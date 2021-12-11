package com.example.foodiepedia.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Activities.UserHomeActivity;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.ItemLayoutRecipesUserBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserRecipeAdapter extends RecyclerView.Adapter<UserRecipeAdapter.iniAdapter>{

    ArrayList<Resep> reseps;
    OnItemClickCallback callback;

    public UserRecipeAdapter(ArrayList<Resep> reseps, OnItemClickCallback callback) {
        this.reseps = reseps;
        this.callback = callback;
    }

    @NonNull
    @Override
    public iniAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemLayoutRecipesUserBinding bind = ItemLayoutRecipesUserBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new iniAdapter(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull iniAdapter holder, int position) {
        Resep r = reseps.get(position);
        holder.bindReseptoView(r);
        holder.itemView.setOnClickListener(view -> callback.onItemClicked(r,view,position));
    }

    @Override
    public int getItemCount() {
        return reseps.size();
    }

    public class iniAdapter extends RecyclerView.ViewHolder {

        ImageView[] stars = new ImageView[5];
        ItemLayoutRecipesUserBinding bind;

        public iniAdapter(@NonNull ItemLayoutRecipesUserBinding bindlol) {
            super(bindlol.getRoot());
            bind = bindlol;
            stars[0] = bind.star1;
            stars[1] = bind.star2;
            stars[2] = bind.star3;
            stars[3] = bind.star4;
            stars[4] = bind.star5;
        }

        void bindReseptoView(Resep r) {
            bind.tvfood.setText(r.getNama_resep());
            bind.tvcreator.setText("Chef : "+r.getChef_resep());
            StringRequest sreq = new StringRequest(Request.Method.POST, bind.getRoot().getContext().getString(R.string.url),
                    response -> {
                        float rate = Float.parseFloat(response);
                        int i = 0;
                        while (rate>0) {
                            if (rate >= 1)
                                stars[i].setImageResource(R.drawable.ic_baseline_star_24);
                            else stars[i].setImageResource(R.drawable.ic_baseline_star_half_24);
                            rate--;
                            i++;
                        }
                    }, error -> System.out.println(error.getMessage())) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> param = new HashMap<>();
                    param.put("func", "ret");
                    param.put("resep", r.getIdresep() + "");
                    return param;
                }
            };
            RequestQueue rqueue = Volley.newRequestQueue(bind.getRoot().getContext());
            rqueue.add(sreq);
        }
    }

    public interface OnItemClickCallback{
        void onItemClicked(Resep r,View v,int position);
    }
}
