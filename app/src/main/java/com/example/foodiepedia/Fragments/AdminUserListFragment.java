package com.example.foodiepedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.foodiepedia.Adapter.UserListAdapter;
import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminUserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminUserListFragment extends Fragment {

    RecyclerView rvUsers;
    UserListAdapter adapter;

    //    REQUEST ALL USERS DARI DATABASE
    ArrayList<User> users = new ArrayList<>();

    public AdminUserListFragment() {
        // Required empty public constructor
    }

    public static AdminUserListFragment newInstance() {
        AdminUserListFragment fragment = new AdminUserListFragment();
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
        return inflater.inflate(R.layout.fragment_admin_user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvUsers = view.findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);
        rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new UserListAdapter(users);
        rvUsers.setAdapter(adapter);

        adapter.setOnItemClickCallback(new UserListAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(int is_banned, int userid) {
                setBan(is_banned, userid);
            }
        });

        GetAllUserProcess();
    }

    private void GetAllUserProcess(){
        users.clear();
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
                            String message = jsonObject.getString("message");
                            if(code == 1){
                                JSONArray jsonArray = jsonObject.getJSONArray("datauser");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject mhsObj = jsonArray.getJSONObject(i);
                                    User user = new User(
                                            mhsObj.getInt("id"),
                                            mhsObj.getString("nama"),
                                            mhsObj.getString("pass")
                                    );
                                    user.setUser_isbanned(mhsObj.getInt("is_banned"));
                                    users.add(user);
                                }
                            }
                            adapter.notifyDataSetChanged();
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
                params.put("func","getalluser");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void setBan(int isbanned, int userid){
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                getResources().getString(R.string.url),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        adapter.notifyDataSetChanged();
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
                params.put("func","setban");
                params.put("userid", userid+"");
                params.put("isbanned", isbanned+"");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}