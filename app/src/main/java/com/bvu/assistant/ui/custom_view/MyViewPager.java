package com.bvu.assistant.ui.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.bvu.assistant.R;


public class MyViewPager extends ViewPager {
    private boolean isDisabledSwiping;


    public MyViewPager(@NonNull Context context) {
        super(context);
        isDisabledSwiping = false;
    }


    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray ta = context.getTheme().obtainStyledAttributes(
            attrs,
            R.styleable.MyViewPager,
            0,
            0
        );


        try {
            this.isDisabledSwiping = ta.getBoolean(R.styleable.MyViewPager_allowSwiping, true);
        }
        catch (Exception e) {

        }
        finally {
            ta.recycle();
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.isDisabledSwiping;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.isDisabledSwiping;
    }



    public void setDisabledSwiping(boolean value) {
        this.isDisabledSwiping = value;
    }

    public boolean getDisabledSwiping() {
        return this.isDisabledSwiping;
    }

}
