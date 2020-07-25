package com.bvu.assistant.view.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentHomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    FragmentHomeBinding B;


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        B = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        B.refresher.setColorSchemeColors(
                Color.rgb(0, 138, 230),
                Color.rgb(25, 189, 0),
                Color.rgb(255, 204, 0),
                Color.rgb(245, 0, 53)
        );

        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }


}