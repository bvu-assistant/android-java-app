package com.bvu.assistant.ui.main.home.functions.award;

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
import com.bvu.assistant.databinding.FragmentAwardBinding;
import com.bvu.assistant.databinding.FragmentAwardBonusItemLayoutBinding;
import com.bvu.assistant.databinding.FragmentAwardCardLayoutBinding;
import com.bvu.assistant.ui.base.BaseFragment;

import java.util.List;


public class AwardFragment extends BaseFragment<FragmentAwardBinding, AwardFragmentViewModel> {
    private final String ssid;

    public AwardFragment(String ssid) {
        this.ssid = ssid;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_award;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VM = ViewModelProviders.of(this).get(AwardFragmentViewModel.class);
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
                    processAwardInfo(exercisingScores);
                }
                else {
                    Toast.makeText(activity, "Có lỗi trong quá trình xử lý", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void processAwardInfo(List<Student.ExercisingScore> exercisingScores) {
        for (Student.ExercisingScore es : exercisingScores)
            if (es.getBonus() != null && es.getBonus().size() > 0)
                B.container.addView(getAwardCardView(es));
    }

    private View getAwardCardView(Student.ExercisingScore es) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        FragmentAwardCardLayoutBinding cardB = DataBindingUtil.inflate(inflater, R.layout.fragment_award_card_layout, null, false);

        cardB.txtTermName.setText(es.getTerm());
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

        for (Student.ExercisingScore.Bonus bonus : es.getBonus()) {
            FragmentAwardBonusItemLayoutBinding bonusB = DataBindingUtil.inflate(inflater, R.layout.fragment_award_bonus_item_layout, null, false);

            bonusB.txtDate.setText(bonus.getDate());
            bonusB.txtBonusTitle.setText(bonus.getGained());
            bonusB.txtBonusContent.setText(bonus.getDetails());

            cardB.tableContainer.addView(bonusB.getRoot());
        }

        return cardB.getRoot();
    }

}