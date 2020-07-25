package com.bvu.assistant.viewmodel.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bvu.assistant.view.fragments.notification.CalendarNotificationFragment;
import com.bvu.assistant.view.fragments.notification.NewsNotificationFragment;
import com.bvu.assistant.view.fragments.notification.WarningFragment;

public class NotificationPagerAdapter extends FragmentPagerAdapter {

    public NotificationPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0: {
                fragment = new CalendarNotificationFragment();
                break;
            }

            case 1: {
                fragment = new NewsNotificationFragment();
                break;
            }

            case 2: {
                fragment = new WarningFragment();
                break;
            }
        }

        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return position == 0? "Lịch biểu": position == 1? "Tin tức": "Cảnh báo";
    }

}
