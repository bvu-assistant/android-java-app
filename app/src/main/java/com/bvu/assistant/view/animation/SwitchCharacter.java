package com.bvu.assistant.animation;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.bvu.assistant.R;


public class SwitchCharacter  {
    public Animation anim;

    public SwitchCharacter(Context context) {
        anim = AnimationUtils.loadAnimation(context, R.anim.zoom_anim);
    }

}
