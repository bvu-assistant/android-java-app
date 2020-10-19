package com.bvu.assistant.ui.main.news;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bvu.assistant.ui.main.news.common.NewsCommonFragment;
import com.bvu.assistant.ui.main.news.rules.RulesFragment;
import com.bvu.assistant.ui.main.news.saved.SavedNewsFragment;


public class NewsPagerAdapter extends FragmentPagerAdapter {
    public static final String[] PAGE_TITLES = new String[] {
            "Đã lưu", "Tin chính", "Tin Sinh viên", "Quy định - Biểu mẫu",
            "Khen thưởng", "Hoạt động", "Sau Đại học" };


    public NewsPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new SavedNewsFragment();
            }

            case 3: {
                return new RulesFragment();
            }

            default: {
                return NewsCommonFragment.newInstance(position);
            }
        }
    }


    @Override
    public int getCount() {
        return 7;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return PAGE_TITLES[position];
    }

}
