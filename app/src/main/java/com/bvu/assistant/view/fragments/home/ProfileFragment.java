package com.bvu.assistant.view.fragments.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentProfileBinding;
import com.bvu.assistant.model.Student;
import com.google.gson.Gson;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding B;
    private static final String FIRST_ARG_KEY = "param1";
    private Student.Profile mParam1;


    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(FIRST_ARG_KEY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = new Gson().fromJson(getArguments().getString(FIRST_ARG_KEY), Student.Profile.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        B = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

}