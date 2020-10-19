package com.bvu.assistant.ui.main.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bvu.assistant.R;
import com.bvu.assistant.databinding.FragmentNewsBinding;
import com.bvu.assistant.utils.NewsTabLayoutHelper;
import com.bvu.assistant.data.model.interfaces.NewsFragmentBadger;
import com.google.android.material.tabs.TabLayout;



public class NewsFragment extends Fragment implements NewsFragmentBadger {
    FragmentNewsBinding B;


    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(String param1, String param2) {
        return new NewsFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        B = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_news,
            container,
            false
        );

        return B.getRoot();
    }


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
    public void setBadge(int tabIndex, int badgeCount, int[] rgb) {
        NewsTabLayoutHelper.setBadgeNumber(B.newsTabLayout, tabIndex, badgeCount, rgb);
    }
}