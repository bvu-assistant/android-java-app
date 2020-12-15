package com.bvu.assistant.ui.main.home.functions.office365;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentOffice365Binding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;


public class Office365Fragment extends BaseFragment<FragmentOffice365Binding, BaseViewModel> {
    private final Student.Profile.PersonalProfile.BVU365 officeInfo;

    public Office365Fragment(Student.Profile.PersonalProfile.BVU365 officeInfo) {
        this.officeInfo = officeInfo;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_office365;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        B.txtEmail.setText(String.format("%s", officeInfo.getMail()));
        B.txtPassword.setText(String.format("%s", officeInfo.getPassword()));


        B.txtEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyValue(((TextView)v).getText());
                return true;
            }
        });

        B.txtPassword.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                copyValue(((TextView)v).getText());
                return true;
            }
        });
    }

    private void copyValue(CharSequence text) {
        ClipboardManager clipboardManager = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(ClipData.newPlainText("data", text.toString()));
        Toast.makeText(activity, String.format("Đã sao chép: %s", text.toString()), Toast.LENGTH_SHORT).show();
    }

}