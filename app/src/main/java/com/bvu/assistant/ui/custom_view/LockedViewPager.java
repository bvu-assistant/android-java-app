package com.bvu.assistant.ui.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bvu.assistant.R;


public class LockedViewPager extends ViewPager {
    private boolean swipeable;

    public LockedViewPager(Context context) {
        super(context);
    }

    public LockedViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.swipeable = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.swipeable) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.swipeable) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setSwipeable(boolean swipeable) {
        this.swipeable = swipeable;
    }

}
