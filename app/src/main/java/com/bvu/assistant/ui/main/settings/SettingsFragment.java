package com.bvu.assistant.ui.main.settings;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentConnectingBinding;
import com.bvu.assistant.databinding.FragmentSettingsBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.main.MainActivity;


public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsFragmentViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).onDirectChildFragmentAttached((ViewGroup)B.getRoot());
        }
    }


}