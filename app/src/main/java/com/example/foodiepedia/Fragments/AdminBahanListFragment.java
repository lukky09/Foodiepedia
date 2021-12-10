package com.example.foodiepedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodiepedia.Data.Bahan;
import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.FragmentAdminBahanListBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminBahanListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminBahanListFragment extends Fragment {

    private FragmentAdminBahanListBinding binding;
    private String mParam1;
    private ArrayList<Bahan> listbahan;


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
    }
}