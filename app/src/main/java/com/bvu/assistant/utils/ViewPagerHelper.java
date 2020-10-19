package com.bvu.assistant.utils;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import org.jetbrains.annotations.NotNull;

public class ViewPagerHelper {

    @SuppressLint("ClickableViewAccessibility")
    public static void disableSwiping(@NotNull ViewPager viewPager) {
        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
    }

}
