package com.bvu.assistant.view.fragments.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentRulesBinding;
import com.bvu.assistant.viewmodel.adapters.RulesPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RulesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RulesFragment extends Fragment {
    FragmentRulesBinding B;


    public RulesFragment() {
        // Required empty public constructor
    }

    public static RulesFragment newInstance(String param1, String param2) {
        return new RulesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        B = DataBindingUtil.inflate(inflater, R.layout.fragment_rules, container, false);

        return B.getRoot();
    }


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


}