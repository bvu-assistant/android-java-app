package com.bvu.assistant.ui.main.settings;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentConnectingBinding;
import com.bvu.assistant.databinding.FragmentSettingsBinding;
import com.bvu.assistant.ui.base.BaseFragment;


public class SettingsFragment extends BaseFragment<FragmentSettingsBinding, SettingsFragmentViewModel> {

    

    @Override
    public int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }
}