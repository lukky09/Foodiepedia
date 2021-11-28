package com.example.foodiepedia.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodiepedia.R;
import com.example.foodiepedia.databinding.FragmentAdminResepListBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminResepListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminResepListFragment extends Fragment {

    private FragmentAdminResepListBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_JENIS = "param-jenis";

    // TODO: Rename and change types of parameters
    private String jenis;

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
    }
}