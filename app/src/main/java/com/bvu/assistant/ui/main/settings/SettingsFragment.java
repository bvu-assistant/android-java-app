package com.bvu.assistant.ui.main.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Login;
import com.bvu.assistant.databinding.FragmentConnectingBinding;
import com.bvu.assistant.databinding.FragmentSettingsBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.login.LoginActivity;
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        B.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(activity, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
            }
        });
    }

}