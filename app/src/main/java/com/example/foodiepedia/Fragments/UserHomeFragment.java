package com.example.foodiepedia.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Activities.DetailResepActivity;
import com.example.foodiepedia.Activities.TambahResepActivity;
import com.example.foodiepedia.Activities.UserHomeActivity;
import com.example.foodiepedia.Adapter.UserRecipeAdapter;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserHomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView rv;
    TextView tit;
    UserRecipeAdapter adapter;
    User curruser;
    ArrayList<Resep> reseps;
    boolean isfollowing;

    public static UserHomeFragment newInstance(User u,boolean b) {
        UserHomeFragment fragment = new UserHomeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, u);
        args.putBoolean(ARG_PARAM2,b);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            curruser = getArguments().getParcelable(ARG_PARAM1);
            isfollowing = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String id;
        rv = getActivity().findViewById(R.id.rvhome);
        tit = getActivity().findViewById(R.id.tvtitt);
        Toast.makeText(getActivity(), isfollowing+"", Toast.LENGTH_SHORT).show();
        if(isfollowing) {
            id = curruser.getUser_id() + "";
            tit.setText("Resep koki favorit anda");
        }
        else {
            id = "0";
            tit.setText("Resep Terbaru");
        }
        reseps = new ArrayList<>();
        StringRequest sreq = new StringRequest(Request.Method.POST, getString(R.string.url),
                response -> {
                    try {
                        JSONArray jar = new JSONObject(response).getJSONArray("dataresep");
                        for (int i = 0; i < jar.length(); i++) {
                            JSONObject job = jar.getJSONObject(i);
                            reseps.add(new Resep(job.getInt("resep_id"),
                                    job.getInt("chef_id"),
                                    job.getString("resep_nama"),
                                    job.getString("resep_desk"),
                                    job.getString("chef_name"),
                                    job.getInt("resep_isapproved")));
                        }
                        adapter = new UserRecipeAdapter(reseps, new UserRecipeAdapter.OnItemClickCallback() {
                            @Override
                            public void onItemClicked(Resep r, View v, int position) {
                                Intent i = new Intent(getActivity(), DetailResepActivity.class);
                                i.putExtra("u",curruser);
                                i.putExtra("r",r);
                                startActivity(i);
                            }
                        });
                        rv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rv.setAdapter(adapter);
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("func", "getresep");
                param.put("id", id);
                return param;
            }
        };
        RequestQueue rqueue = Volley.newRequestQueue(getActivity());
        rqueue.add(sreq);
    }
}