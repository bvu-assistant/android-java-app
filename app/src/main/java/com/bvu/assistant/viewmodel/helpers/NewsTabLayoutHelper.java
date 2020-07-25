package com.bvu.assistant.viewmodel.helpers;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.bvu.assistant.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

public class NewsTabLayoutHelper {

    @SuppressLint("ResourceType")
    public static void setBadgeNumber(@NotNull TabLayout tabLayout, int tabItemIndex, int badgeNumber, int[] badgeColorComponents) {
        if (badgeNumber <= 0) {
            removeBadgeNumber(tabLayout, tabItemIndex);
            return;
        }

        BadgeDrawable badgeDrawable = tabLayout.getTabAt(tabItemIndex).getOrCreateBadge();
        badgeDrawable.setBackgroundColor(Color.rgb(badgeColorComponents[0], badgeColorComponents[1], badgeColorComponents[2]));
        badgeDrawable.setMaxCharacterCount(3);
        badgeDrawable.setHorizontalOffset(-6);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(badgeNumber);
    }

    public static void removeBadgeNumber(@NotNull TabLayout tabLayout, int tabItemIndex) {
        BadgeDrawable badgeDrawable = tabLayout.getTabAt(tabItemIndex).getOrCreateBadge();
        badgeDrawable.setVisible(false);
    }

}
