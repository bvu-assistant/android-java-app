package com.bvu.assistant.ui.main.home.functions.roadmap;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentAttendanceTableRowViewBinding;
import com.bvu.assistant.databinding.FragmentRoadmapBinding;
import com.bvu.assistant.databinding.FragmentRoadmapTableViewBinding;
import com.bvu.assistant.databinding.FragmentRoadmapTermViewBinding;
import com.bvu.assistant.ui.base.BaseFragment;

import java.util.List;

public class RoadmapFragment extends BaseFragment<FragmentRoadmapBinding, RoadmapFragmentViewModel> {
    private final String ssid;

    public RoadmapFragment(String ssid) {
        this.ssid = ssid;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_roadmap;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VM = ViewModelProviders.of(this).get(RoadmapFragmentViewModel.class);
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



        VM.getRoadmapInfo(ssid).observe(getViewLifecycleOwner(), new Observer<Student.RoadmapInfo>() {
            @Override
            public void onChanged(Student.RoadmapInfo roadmapInfo) {
                B.refresher.setRefreshing(false);
                B.refresher.setEnabled(false);

                if (roadmapInfo != null)
                    processRoadmapInfo(roadmapInfo);
                else {
                    Toast.makeText(activity, "Có lỗi trong quá trình xử lý", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    private void processRoadmapInfo(Student.RoadmapInfo roadmapInfo) {
        for (Student.RoadmapInfo.Term term : roadmapInfo.getTerms())
            B.container.addView(getTableView(term));
    }

    private View getTableView(Student.RoadmapInfo.Term term) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentRoadmapTermViewBinding termB = DataBindingUtil.inflate(inflater, R.layout.fragment_roadmap_term_view, null, false);


        termB.txtTermName.setText(term.getTerm());
        termB.termCollapseTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termB.collapser.isExpanded()) {
                    termB.collapser.collapse(true);
                }
                else {
                    termB.collapser.expand(true);
                }
            }
        });


        termB.tablesContainer.addView(getTableView(term.getRequiredSubjects(), false));
        if (term.getElectiveSubjects() != null)
            termB.tablesContainer.addView(getTableView(term.getElectiveSubjects(), true));

        return termB.getRoot();
    }

    private View getTableView(List<Student.RoadmapInfo.Subject> electiveSubjects, boolean elective) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentRoadmapTableViewBinding tableB = DataBindingUtil.inflate(inflater, R.layout.fragment_roadmap_table_view, null, false);
        tableB.txtTableTitle.setText(elective ? "Các môn học Tự chọn": "Các môn học Bắt buộc");

        for (Student.RoadmapInfo.Subject subject : electiveSubjects) {
            FragmentAttendanceTableRowViewBinding rowB = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_table_row_view, null, false);

            rowB.txtSubjectId.setText(subject.getSubjectId());
            rowB.txtSubjectName.setText(subject.getSubjectName());
            rowB.txtCredits.setText(String.valueOf(subject.getTotalCredits()));
            rowB.txtExcusedAbsence.setText(String.valueOf(subject.getTheoryLessons()));
            rowB.txtUnExcusedAbsence.setText(String.valueOf(subject.getPracticeLessons()));

            tableB.tableLayout.addView(rowB.getRoot());
        }


        return tableB.getRoot();
    }

}