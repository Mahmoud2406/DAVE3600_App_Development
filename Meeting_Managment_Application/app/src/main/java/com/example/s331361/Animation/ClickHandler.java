package com.example.s331361.Animation;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.s331361.R;

public class ClickHandler {

     public static void animateClick (ImageView v,final Context context) {
        v.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:   {
                        final Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha);
                        v.startAnimation(animation);
                        break;
                    }
                }


                return false;
            }

        });
    }

}
