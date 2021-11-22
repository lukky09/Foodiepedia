package com.example.foodiepedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.foodiepedia.Data.User;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.FragmentResepBinding;

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
    }
}