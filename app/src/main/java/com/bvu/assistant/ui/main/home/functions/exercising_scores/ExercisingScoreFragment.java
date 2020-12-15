package com.bvu.assistant.ui.main.home.functions.exercising_scores;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.Student;
import com.bvu.assistant.databinding.FragmentExercisingScoreBinding;
import com.bvu.assistant.databinding.FragmentExercisingScoresCardViewBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.bvu.assistant.ui.base.BaseViewModel;

import java.util.List;

public class ExercisingScoreFragment extends BaseFragment<FragmentExercisingScoreBinding, ExercisingScoreFragmentViewModel> {
    private final String ssid;

    public ExercisingScoreFragment(String ssid) {
        this.ssid = ssid;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_exercising_score;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VM = ViewModelProviders.of(this).get(ExercisingScoreFragmentViewModel.class);
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


        VM.getExercisingScores(ssid).observe(getViewLifecycleOwner(), new Observer<List<Student.ExercisingScore>>() {
            @Override
            public void onChanged(List<Student.ExercisingScore> exercisingScores) {
                B.refresher.setRefreshing(false);
                B.refresher.setEnabled(false);

                if (exercisingScores != null) {
                    processExercisingScores(exercisingScores);
                }
                else {
                    Toast.makeText(activity, "Có lỗi trong quá trình xử lý", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void processExercisingScores(List<Student.ExercisingScore> exercisingScores) {
        for (Student.ExercisingScore es : exercisingScores)
            B.container.addView(getCardView(es));
    }

    @SuppressLint("DefaultLocale")
    private View getCardView(Student.ExercisingScore es) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentExercisingScoresCardViewBinding cardB = DataBindingUtil.inflate(inflater, R.layout.fragment_exercising_scores_card_view, null, false);

        cardB.txtTermName.setText(es.getTerm());
        cardB.txtScores.setText(String.format("Điểm: %.2f", es.getRank().getPoints()));
        cardB.txtRank.setText(String.format("Xếp loại: %s", es.getRank().getRank()));

        cardB.termCollapseTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardB.collapser.isExpanded()) {
                    cardB.collapser.collapse(true);
                }
                else {
                    cardB.collapser.expand(true);
                }
            }
        });

        return cardB.getRoot();
    }

}