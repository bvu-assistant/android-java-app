package com.bvu.assistant.ui.main.news;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.bvu.assistant.BR;
import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentNewsBinding;
import com.bvu.assistant.ui.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;



public class NewsFragment extends BaseFragment<FragmentNewsBinding, NewsFragmentViewModel> {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getChildFragmentManager(), 0);
        ViewPager viewPager = B.newsViewPager;
        viewPager.setAdapter(newsPagerAdapter);
        viewPager.setOffscreenPageLimit(6);

        TabLayout tabLayout = B.newsTabLayout;
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(1).select();
    }



    @Override
    public int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    public int getBindingVariables() {
        return BR.viewModel;
    }
}