package com.example.s331361mappe1.animation_handler;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.example.s331361mappe1.R;

public  class ClickAnimation {

    // Får inn et view og context for å gi viewet et klikk effekt
    public static void animateClick(View v, Context context) {
        MediaPlayer mP = MediaPlayer.create(context, R.raw.click);
        try {
            if (mP.isPlaying()) {
                mP.stop();
                mP.release();
                mP = MediaPlayer.create(context, R.raw.click);
            } mP.start();
        } catch(Exception e) { e.printStackTrace(); }
        v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_item));

    }
}
