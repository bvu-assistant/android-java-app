package com.bvu.assistant.utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class TabLayoutHelper {

    public static void reFlipTabBarItems(@NotNull TabLayout tabLayout) {
        LinearLayout tabs = ((LinearLayout) tabLayout.getChildAt(0));

        for (int position = 0; position < tabs.getChildCount(); position++) {
            LinearLayout item = ((LinearLayout) tabs.getChildAt(position));
            item.setRotationX(180);
        }
    }

    public static void setDefaultSelectedTabItem(@NotNull TabLayout tabLayout, int tabItemIndex) {
        Objects.requireNonNull(tabLayout.getTabAt(tabItemIndex)).select();
    }



    @SuppressLint("ResourceType")
    public static void setBadgeNumber(@NotNull TabLayout tabLayout, int tabItemIndex, int badgeNumber) {
        if (badgeNumber <= 0) {
            removeBadgeNumber(tabLayout, tabItemIndex);
            return;
        }

        BadgeDrawable badgeDrawable = tabLayout.getTabAt(tabItemIndex).getOrCreateBadge();
        badgeDrawable.setBackgroundColor(Color.rgb(227, 0, 23));
        badgeDrawable.setMaxCharacterCount(3);
        badgeDrawable.setVerticalOffset(8);
        badgeDrawable.setHorizontalOffset(6);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(badgeNumber);
    }

    public static void removeBadgeNumber(@NotNull TabLayout tabLayout, int tabItemIndex) {
        BadgeDrawable badgeDrawable = tabLayout.getTabAt(tabItemIndex).getOrCreateBadge();
        badgeDrawable.setVisible(false);
    }

}
