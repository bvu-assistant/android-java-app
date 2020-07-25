package com.bvu.assistant.view.fragments.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bvu.assistant.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class NewsBottomSheetFragment extends BottomSheetDialogFragment {
    private View v;

    public NewsBottomSheetFragment(View v) {
        this.v = v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return v;
    }
}
