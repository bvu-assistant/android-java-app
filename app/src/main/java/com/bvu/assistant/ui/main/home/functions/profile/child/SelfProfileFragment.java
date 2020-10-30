package com.bvu.assistant.ui.main.home.functions.profile.child;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentSelfProfileBinding;

import org.w3c.dom.Text;


public class SelfProfileFragment extends Fragment {
    private final Student.Profile profileInfo;
    private FragmentSelfProfileBinding B;


    public SelfProfileFragment(Student.Profile profile) {
        this.profileInfo = profile;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        B = DataBindingUtil.inflate(inflater, R.layout.fragment_self_profile, container, false);
        return B.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enableLongClickOnItemContent();
        assignItemContentsValue();
    }



    private void assignItemContentsValue() {
        B.txtFullName.setText(profileInfo.getName());
        B.txtClassName.setText(profileInfo.getLearningStatus().getClassName());
        B.txtEducatingType.setText(profileInfo.getLearningStatus().getEducatingType());
        B.txtDepartment.setText(profileInfo.getLearningStatus().getDepartment());
        B.txtMajor.setText(profileInfo.getLearningStatus().getMajor());
        B.txtMainMajor.setText(profileInfo.getLearningStatus().getMainMajor());

        B.txtDOB.setText(profileInfo.getPersonalProfile().getBirth().getDate());
        B.txtGender.setText(profileInfo.getLearningStatus().getGender());
        B.txtPhone.setText(profileInfo.getPersonalProfile().getContact().getPhone());
        B.txtAddress.setText(profileInfo.getPersonalProfile().getContact().getAddress().getContacting());
    }

    private void enableLongClickOnItemContent() {
        int rowsCounter = B.tableLayout.getChildCount();

        for (int i = 0; i < rowsCounter; ++i) {
            View row = B.tableLayout.getChildAt(i);

            if (row instanceof TableRow) {
                TextView txtContent = (TextView) ((TableRow) row).getChildAt(1);
                txtContent.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        copyItemContent(txtContent.getText().toString());
                        return true;
                    }
                });
            }
        }
    }

    private void copyItemContent(String content) {
        ClipboardManager manager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("content", content));

        Toast.makeText(getContext(), "Đã sao chép \"" + content + "\"", Toast.LENGTH_SHORT).show();
    }

}
