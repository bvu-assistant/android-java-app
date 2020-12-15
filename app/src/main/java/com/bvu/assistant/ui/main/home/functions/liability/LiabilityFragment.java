package com.bvu.assistant.ui.main.home.functions.liability;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentLiabilityBinding;
import com.bvu.assistant.ui.base.BaseFragment;

import java.util.List;


public class LiabilityFragment extends BaseFragment<FragmentLiabilityBinding, LiabilityFragmentViewModel> {
    private final String ssid;

    public LiabilityFragment(String ssid) {
        this.ssid = ssid;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_liability;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VM = ViewModelProviders.of(this).get(LiabilityFragmentViewModel.class);
        observe();
    }

    private void observe() {
        B.refresher.setColorSchemeColors(
                Color.rgb(0, 138, 230),
                Color.rgb(25, 189, 0),
                Color.rgb(255, 204, 0),
                Color.rgb(245, 0, 53)
        );
        B.refresher.setRefreshing(true);


        VM.getLiabilityInfo(ssid).observe(getViewLifecycleOwner(), new Observer<Student.LiabilityInfo>() {
            @Override
            public void onChanged(Student.LiabilityInfo liabilityInfo) {
                B.refresher.setRefreshing(false);
                B.refresher.setEnabled(false);

                if (liabilityInfo != null) {
                    processLiabilityInfo(liabilityInfo);
                }
                else {
                    Toast.makeText(activity, "Có lỗi trong quá trình xử lý", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void processLiabilityInfo(Student.LiabilityInfo liabilityInfo) {
        B.txtSession.setText(liabilityInfo.getTerm());
        B.txtCost.setText(liabilityInfo.getLiability());
    }

}