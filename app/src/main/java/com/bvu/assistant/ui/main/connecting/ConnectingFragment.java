package com.bvu.assistant.ui.main.connecting;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentConnectingBinding;
import com.bvu.assistant.ui.base.BaseFragment;


public class ConnectingFragment extends BaseFragment<FragmentConnectingBinding, ConnectingFragmentViewModel> {



    @Override
    public int getLayoutId() {
        return R.layout.fragment_connecting;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }
}