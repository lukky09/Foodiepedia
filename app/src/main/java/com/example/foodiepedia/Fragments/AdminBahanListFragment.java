package com.example.foodiepedia.Fragments;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.foodiepedia.Adapter.RequestBahanAdapter;
import com.example.foodiepedia.Data.Bahan;
import com.example.foodiepedia.Data.RequestBahan;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.FragmentAdminBahanListBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminBahanListFragment extends Fragment {

    private FragmentAdminBahanListBinding binding;
    RequestBahanAdapter adapter;
    private ArrayList<RequestBahan> listbahan;


    public static AdminBahanListFragment newInstance() {
        AdminBahanListFragment fragment = new AdminBahanListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAdminBahanListBinding.inflate(getLayoutInflater());
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_admin_bahan_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listbahan = new ArrayList<>();
        buatrecyclerview(listbahan);
        getbahan();
    }

    public void buatrecyclerview(ArrayList<RequestBahan> listrequest){
        binding.rvbahan.setHasFixedSize(true);
        binding.rvbahan.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RequestBahanAdapter(listrequest);
        binding.rvbahan.setAdapter(adapter);

        adapter.setOnItemClickCallback(new RequestBahanAdapter.onItemClickCallback() {
            @Override
            public void AcceptBahan(RequestBahan requestBahan) {
                StringRequest stringRequest = new StringRequest(
                        Request.Method.POST,
                        getResources().getString(R.string.url),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    int code = jsonObject.getInt("code");
                                    if (code == 1){
                                        for (RequestBahan rb: listbahan) {
                                            if (rb.getId_bahan() == requestBahan.getId_bahan()){
                                                rb.setStatusbahan(1);
                                            }
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                    Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                        params.put("func", "accbahanadmin");
                        params.put("bahan_id", String.valueOf(requestBahan.getId_bahan()));
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });
    }

    public void getbahan(){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            if (code == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("bahan");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject objbahan = jsonArray.getJSONObject(i);
                                    RequestBahan b = new RequestBahan(
                                            objbahan.getInt("id_bahan"),
                                            objbahan.getInt("statusbahan"),
                                            objbahan.getString("bahan_nama")
                                    );
                                    listbahan.add(b);
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
                params.put("func", "getbahanadmin");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}