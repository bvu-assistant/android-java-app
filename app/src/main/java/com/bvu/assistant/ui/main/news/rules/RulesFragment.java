package com.bvu.assistant.ui.main.news.rules;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;

import com.bvu.assistant.R;
import com.bvu.assistant.data.model.interfaces.MainActivityChildFragmentGainer;
import com.bvu.assistant.databinding.FragmentRulesBinding;
import com.bvu.assistant.ui.base.BaseFragment;


public class RulesFragment extends BaseFragment<FragmentRulesBinding, RulesFragmentViewModel> {


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RulesPagerAdapter adapter = new RulesPagerAdapter(getChildFragmentManager(), 0);
        B.rulesViewPager.setAdapter(adapter);
        B.rulesViewPager.setOffscreenPageLimit(3);

        B.rulesTabBar.setupWithViewPager(B.rulesViewPager);
        B.rulesTabBar.getTabAt(1).select();
        setTabMargins();
    }

    private void setTabMargins() {
        ViewGroup tabs = (ViewGroup) B.rulesTabBar.getChildAt(0);

        for (int i = 0; i < tabs.getChildCount(); i++) {
            View tab = tabs.getChildAt(i);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();

            layoutParams.setMarginStart(30);
            layoutParams.setMarginEnd(20);
            layoutParams.setMarginEnd(20);

            tab.setLayoutParams(layoutParams);
            B.rulesTabBar.requestLayout();
        }
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_rules;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }

}