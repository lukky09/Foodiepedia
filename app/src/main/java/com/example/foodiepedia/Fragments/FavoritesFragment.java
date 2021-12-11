package com.example.foodiepedia.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
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
import com.example.foodiepedia.databinding.FragmentFavoritesBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private User user;
    ArrayList<Resep> arrResep;
    UserRecipeAdapter adapter;
    FragmentFavoritesBinding binding;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavoritesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritesFragment newInstance(User user) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.user = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoritesBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        arrResep = new ArrayList<>();
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
//                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
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
                                i.putExtra("u",user);
                                i.putExtra("r",r);
                                startActivity(i);
                                adapter.notifyDataSetChanged();
                            }
                        });
                        binding.rvResep.setLayoutManager(new LinearLayoutManager(getContext()));
                        binding.rvResep.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getlistfavorites");
                param.put("userid", user.getUser_id() + "");
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(getActivity());
        rqueue.add(sreq);
        super.onViewCreated(view, savedInstanceState);
    }
}