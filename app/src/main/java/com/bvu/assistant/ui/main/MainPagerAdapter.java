package com.bvu.assistant.ui.main;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.bvu.assistant.R;
import com.bvu.assistant.ui.main.connecting.ConnectingFragment;
import com.bvu.assistant.ui.main.calendar.CalendarFragment;
import com.bvu.assistant.ui.main.home.HomeFragment;
import com.bvu.assistant.ui.main.news.NewsFragment;
import com.bvu.assistant.ui.main.settings.SettingsFragment;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;


public class MainPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private Resources resources;
    private final String[] titleList = {
        "main_tabbar_first_title",
        "main_tabbar_second_title",
        "main_tabbar_third_title",
        "main_tabbar_fourth_title",
        "main_tabbar_fifth_title"
    };


    public MainPagerAdapter(FragmentManager fm, int behavior, @NotNull Context context) {
        super(fm, behavior);
        this.context = context;
        this.resources = context.getResources();
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position) {
            case 0: {
                fragment = new ConnectingFragment();
                break;
            }

            case 1: {
                fragment = new CalendarFragment();
                break;
            }

            case 2: {
                fragment = new HomeFragment();
                break;
            }

            case 3: {
                fragment = new NewsFragment();
                break;
            }

            case 4: {
                fragment = new SettingsFragment();
                break;
            }
        }

        assert fragment != null;
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }


//    @Nullable
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return resources.getString(getStringResIdByString(this.titleList[position]));
//    }

    private int getStringResIdByString(String stringId) {
        int stringResId = -1;

        try {
            Class c = R.string.class;
            Field f = c.getDeclaredField(stringId);
            stringResId = f.getInt(null);
        }
        catch (Exception e) {
            Toast.makeText(this.context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
        finally {
            return stringResId;
        }
    }

}
