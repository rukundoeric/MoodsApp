package com.moodsapp.prestein.moodsapp.util;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;

public class AnimationUtils {
    public void ApplyAnimation(Activity context, View view, int animation){
        Animation an = android.view.animation.AnimationUtils.loadAnimation(context.getBaseContext(), animation);
        view.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
