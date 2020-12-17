package com.example.googlemaps.Animation;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.googlemaps.R;

public class AnimationClick {
    public static void AnimateView(Context context, View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.animationclick));
    }
}
