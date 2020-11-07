package com.bvu.assistant.utils;

import android.content.Context;
import android.content.res.Resources;

public class StatusBarUtils {

    public static int getHeight(Context context) {
        int height;

        Resources resourceGetter = context.getResources();
        int idStatusBarHeight = resourceGetter.getIdentifier( "status_bar_height", "dimen", "android");


        if (idStatusBarHeight > 0) {
            height = resourceGetter.getDimensionPixelSize(idStatusBarHeight);
        } else {
            height = 0;
        }


        return height;
    }
}
