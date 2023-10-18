package com.skylite.animator.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

/**
 * Class contains methods for animate TextView according task
 */
public class AnimationUtil {

    private final AnimatorSet animatorSet;
    private final TextView textView;

    public AnimationUtil(TextView textView) {
        this.textView = textView;
        animatorSet = new AnimatorSet();
    }

    /**
     * Start animation according task
     */
    public void startTextViewAnimation(float initialY, int duration) {
        DisplayMetrics displayMetrics = this.textView.getResources().getDisplayMetrics();

        // init animation objects
        ObjectAnimator downAnimation = ObjectAnimator.ofFloat(textView, View.Y, initialY, displayMetrics.heightPixels - TextViewTransformationUtil.getNavigationBarHeight(this.textView.getContext()));
        ObjectAnimator upAnimation = ObjectAnimator.ofFloat(textView, View.Y, displayMetrics.heightPixels - TextViewTransformationUtil.getNavigationBarHeight(this.textView.getContext()), 0);
        ObjectAnimator returnAnimation = ObjectAnimator.ofFloat(textView, View.Y, 0, initialY);

        // setup animation sequence
        animatorSet.setDuration(duration);
        animatorSet.playSequentially(downAnimation, upAnimation, returnAnimation);
        animatorSet.start();

        // loop the animation
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }

        });
    }

    public AnimatorSet getAnimationSet() {
        return this.animatorSet;
    }
}
