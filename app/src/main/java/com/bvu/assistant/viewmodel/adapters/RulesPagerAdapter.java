package com.bvu.assistant.viewmodel.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bvu.assistant.view.fragments.news.NewsCommonFragment;


public class RulesPagerAdapter extends FragmentPagerAdapter {

    public static final String[] PAGE_TITLES = new String[] {
            "Quy Định Đào tạo",
            "Biểu mẫu SV",
            "Quy định Công tác SV",
            "Các Hướng Dẫn" };


    public RulesPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NewsCommonFragment.newInstance(position + 7);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }
}
