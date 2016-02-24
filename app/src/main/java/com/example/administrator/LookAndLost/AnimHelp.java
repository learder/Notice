package com.example.administrator.LookAndLost;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by 颜厥共 on 2016/2/24.
 * email:644613693@qq.com
 */
public class AnimHelp {

    private static final int DELAY = 138;

    /**
     * 飞入动画
     * @param view
     */
    public static void flyInAnim(final View view){
        view.setAlpha(0);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                        R.anim.slide_in_right);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {
                        view.setAlpha(1);
                    }


                    @Override public void onAnimationEnd(Animation animation) {}


                    @Override public void onAnimationRepeat(Animation animation) {}
                });
                view.startAnimation(animation);
            }
        },DELAY);
    }

    public static void bottonInAnim(final View view){
        view.setAlpha(0);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                        R.anim.slide_in_botton);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {
                        view.setAlpha(1);
                    }


                    @Override public void onAnimationEnd(Animation animation) {}


                    @Override public void onAnimationRepeat(Animation animation) {}
                });
                view.startAnimation(animation);
            }
        },DELAY);
    }

    public static void alphaOutAnim(final View view){
        view.setAlpha(0);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                        R.anim.alpha_1_to_0);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {
//                        view.setAlpha(1);
                    }


                    @Override public void onAnimationEnd(Animation animation) {}


                    @Override public void onAnimationRepeat(Animation animation) {}
                });
                view.startAnimation(animation);
            }
        },DELAY);
    }

}
