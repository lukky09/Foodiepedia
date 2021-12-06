package com.example.foodiepedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Adapter.RequestRecipeAdapter;
import com.example.foodiepedia.Adapter.RequestRecipeAdapter;
import com.example.foodiepedia.Data.Resep;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.FragmentAdminResepListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminResepListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminResepListFragment extends Fragment {

    private FragmentAdminResepListBinding binding;
    RequestRecipeAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_JENIS = "param-jenis";

    // TODO: Rename and change types of parameters
    private String jenis;
    private ArrayList<Resep> listresep;

    public static AdminResepListFragment newInstance(String jenis) {
        AdminResepListFragment fragment = new AdminResepListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_JENIS, jenis);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            jenis = getArguments().getString(ARG_PARAM_JENIS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminResepListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_admin_resep_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listresep = new ArrayList<>();
        buatrecyclerview(listresep);
        getallrequest();
    }

    public void buatrecyclerview(ArrayList<Resep> listresep){
        binding.rvrecipe.setHasFixedSize(true);
        binding.rvrecipe.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RequestRecipeAdapter(listresep);
        binding.rvrecipe.setAdapter(adapter);

        /*adapter.setOnItemClickCallback(new RequestRecipeAdapter.onItemClickCallback() {
            @Override
            public void DeleteResep(Resep resep) {
                StringRequest stringRequest = new StringRequest()
            }
        });*/
    }

    public void getallrequest(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            if (code == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("dataresep");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject objresep = jsonArray.getJSONObject(i);
                                    Resep r = new Resep(
                                        objresep.getInt("resep_id"),
                                        objresep.getInt("chef_id"),
                                        objresep.getString("resep_nama"),
                                        objresep.getString("resep_desk"),
                                        objresep.getString("chef_name"),
                                        objresep.getInt("resep_isapproved")
                                    );
                                    listresep.add(r);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("func", "getresep");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}