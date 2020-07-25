package com.bvu.assistant.animation;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


public class MyTransformer {

    public enum Transformer {
        Fading,
        Zooming,
        Flipping,
        ThreeDFlipping
    }


    public static ViewPager.PageTransformer fadeTransformer;
    {
        fadeTransformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                position = Math.abs(Math.abs(position - 1));
                page.setAlpha(position);
            }
        };
    }


    public static ViewPager.PageTransformer flipTransformer;
    {
        flipTransformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                position = Math.abs(Math.abs(position - 1));
                page.setAlpha(position);
            }
        };
    }

}
