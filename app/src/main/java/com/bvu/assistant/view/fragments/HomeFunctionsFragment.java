package com.bvu.assistant.view.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bvu.assistant.R;
import qiu.niorgai.StatusBarCompat;


public class HomeFunctionsFragment extends Fragment {
    public enum FUNCTIONS {
        PROFILE, ROADMAP, OFFICE365,
        LIABILITY, RECEIPT, AWARD,
        LEARNING_SCORES, EXCERCISE_SCORES, ATTENDANCE
    }


    public HomeFunctionsFragment() {
        // Required empty public constructor
    }


    public static HomeFunctionsFragment newInstance() {
        HomeFunctionsFragment fragment = new HomeFunctionsFragment();
        Bundle args = new Bundle();
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
        return inflater.inflate(R.layout.fragment_home_functions, container, false);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if (!hidden) {
            StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.primaryHeader));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.primaryHeader));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}