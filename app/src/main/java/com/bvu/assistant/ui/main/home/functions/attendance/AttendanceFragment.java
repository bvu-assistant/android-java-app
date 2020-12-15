package com.bvu.assistant.ui.main.home.functions.attendance;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentAttendanceBinding;
import com.bvu.assistant.databinding.FragmentAttendanceTableRowViewBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;


public class AttendanceFragment extends BaseFragment<FragmentAttendanceBinding, BaseViewModel> {
    private final List<Student.AttendanceInfo> attendanceInfo;

    public AttendanceFragment(List<Student.AttendanceInfo> attendanceInfo) {
        this.attendanceInfo = attendanceInfo;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_attendance;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        for (Student.AttendanceInfo ai : attendanceInfo) {
            B.tableLayout.addView(getTableRowView(ai));
        }
    }

    @SuppressLint("SetTextI18n")
    private View getTableRowView(Student.AttendanceInfo ai) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentAttendanceTableRowViewBinding rowB = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_table_row_view, null, false);

        rowB.txtSubjectId.setText(ai.getSubjectId());
        rowB.txtSubjectName.setText(ai.getSubjectName());
        rowB.txtCredits.setText(ai.getCredits().toString());
        rowB.txtExcusedAbsence.setText(ai.getExcusedAbsences().toString());
        rowB.txtUnExcusedAbsence.setText(ai.getUnExcusedAbsences().toString());

        return rowB.getRoot();
    }

}