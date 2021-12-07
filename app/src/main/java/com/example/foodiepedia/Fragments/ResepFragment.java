package com.example.foodiepedia.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Activities.DetailResepActivity;
import com.example.foodiepedia.Adapter.UserRecipeAdapter;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.FragmentResepBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResepFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_USER = "currentUser";

    // TODO: Rename and change types of parameters
    private User currentUser;
    FragmentResepBinding binding;
    ArrayList<Resep> arrResep;
    UserRecipeAdapter adapter;


    public ResepFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ResepFragment newInstance(User user) {
        ResepFragment fragment = new ResepFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_USER,user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentUser = getArguments().getParcelable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResepBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tvResep.setText("Resepku");
        binding.tvNamaUser.setText("Oleh : " + currentUser.getUser_name());

        arrResep = new ArrayList<>();
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
                        JSONArray jar = new JSONObject(response).getJSONArray("dataresep");
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject job = jar.getJSONObject(i);
                            arrResep.add(new Resep(job.getInt("resep_id"),
                                    job.getInt("chef_id"),
                                    job.getString("resep_nama"),
                                    job.getString("resep_desk"),
                                    job.getString("chef_name"),
                                    job.getInt("resep_isapproved")));
                        }
                        adapter = new UserRecipeAdapter(arrResep, new UserRecipeAdapter.OnItemClickCallback() {
                            @Override
                            public void onItemClicked(Resep r, View v, int position) {
                                Intent i = new Intent(getActivity(), DetailResepActivity.class);
                                i.putExtra("u",currentUser);
                                i.putExtra("r",r);
                                startActivity(i);
                            }
                        });
                        binding.rvResep.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rvResep.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getresep");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(getActivity());
        rqueue.add(sreq);
    }
}