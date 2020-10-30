package com.bvu.assistant.ui.main.home.functions.profile.child;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FamilyRelativeinfoLayoutBinding;
import com.bvu.assistant.databinding.FragmentFamilyProfileBinding;


public class FamilyProfileFragment extends Fragment {
    private final Student.Profile profileInfo;
    private FragmentFamilyProfileBinding B;


    public FamilyProfileFragment(Student.Profile profile) {
        this.profileInfo = profile;
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        B = DataBindingUtil.inflate(inflater, R.layout.fragment_family_profile, container, false);
        return B.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (Student.Profile.FamilyProfile.Relative r: profileInfo.getFamilyProfile()) {
            FamilyRelativeinfoLayoutBinding layoutBinding;
            layoutBinding = DataBindingUtil.inflate(getActivity().getLayoutInflater(), R.layout.family_relativeinfo_layout, null, false);

            layoutBinding.txtRelation.setText(r.getRelation());
            layoutBinding.txtFullName.setText(r.getName());
            layoutBinding.txtJob.setText(r.getJob());
            layoutBinding.txtNationality.setText(r.getNationality());
            layoutBinding.txtPhone.setText(r.getPhone());
            layoutBinding.txtYOB.setText(r.getDateOfBirth());

            B.linearLayout.addView(layoutBinding.getRoot());
        }


        enableLongClickOnItemContent();
    }



    private void enableLongClickOnItemContent() {
        int itemsCounter = B.linearLayout.getChildCount();

        //  duyệt qua các TableLayout
        for (int i = 0; i < itemsCounter; ++i) {
            TableLayout item = (TableLayout)B.linearLayout.getChildAt(i);

            //  duyệt qua các item trong TableLayout
            for (int j = 0, rowsCounter = item.getChildCount(); j < rowsCounter; ++j) {
                View row = item.getChildAt(j);

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
    }

    private void copyItemContent(String content) {
        ClipboardManager manager = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        manager.setPrimaryClip(ClipData.newPlainText("content", content));

        Toast.makeText(getContext(), "Đã sao chép \"" + content + "\"", Toast.LENGTH_SHORT).show();
    }

}
