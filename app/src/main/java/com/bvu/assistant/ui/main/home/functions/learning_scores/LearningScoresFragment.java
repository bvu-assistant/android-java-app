package com.bvu.assistant.ui.main.home.functions.learning_scores;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentLearningScoresBinding;
import com.bvu.assistant.databinding.FragmentLearningScoresTableRowViewBinding;
import com.bvu.assistant.databinding.FragmentLearningScoresTermTableViewBinding;
import com.bvu.assistant.databinding.FragmentLearningScoresTermViewBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;

public class LearningScoresFragment extends BaseFragment<FragmentLearningScoresBinding, BaseViewModel> {
    private final Student.LearningScores learningScores;

    public LearningScoresFragment(Student.LearningScores learningScores) {
        this.learningScores = learningScores;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_learning_scores;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        processLearningScores(learningScores);
    }



    private void processLearningScores(Student.LearningScores learningScores) {
        for (Student.LearningScores.Term t : learningScores.getTerms())
            B.container.addView(getTermView(t));
    }

    private View getTermView(Student.LearningScores.Term t) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentLearningScoresTermViewBinding termB = DataBindingUtil.inflate(inflater, R.layout.fragment_learning_scores_term_view, null, false);

        termB.txtTermName.setText(t.getTermName());
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

        FragmentLearningScoresTermTableViewBinding tableB = DataBindingUtil.inflate(inflater, R.layout.fragment_learning_scores_term_table_view, null, false);
        for (Student.LearningScores.Subject s : t.getSubjects()) {
            tableB.tableLayout.addView(getTableRowView(s));
        }

        termB.tableContainer.addView(tableB.getRoot());
        return termB.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private View getTableRowView(Student.LearningScores.Subject s) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentLearningScoresTableRowViewBinding rowB = DataBindingUtil.inflate(inflater, R.layout.fragment_learning_scores_table_row_view, null, false);

        rowB.txtSubjectId.setText(s.getClassName().split(" -")[0].replace("0101", ""));
        rowB.txtSubjectName.setText(s.getName());
        rowB.txtCredits.setText(s.getCredits() + "");
        rowB.txtTotalScores.setText(s.getAveragePoint() != null ? s.getAveragePoint() + "" : "-");

        return rowB.getRoot();
    }

}