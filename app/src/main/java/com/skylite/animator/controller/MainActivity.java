package com.skylite.animator.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.skylite.animator.R;


public class MainActivity extends AppCompatActivity {

    private ConstraintLayout constraint_layout;
    private TextView text_label;
    private int navigationBarOffset = 0;
    AnimatorSet animatorSet = new AnimatorSet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default method calls
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial setup method calls
        setupDefaultColors();
        initViews();

        navigationBarOffset = getNavigationBarHeight(this);

        initViewListener();

    }


    @SuppressLint("ClickableViewAccessibility")
    private void initViewListener() {
        constraint_layout.setOnTouchListener((view, event) -> {
            if (animatorSet.isRunning()) {
                animatorSet.cancel();
            }
            changeTextColor();
            float x = event.getX();
            float y = event.getY();
            text_label.setX(x);
            text_label.setY(y);
            startTextViewAnimation(y);
            return false;
        });

        text_label.setOnClickListener(v -> {
            if (animatorSet.isRunning()) {
                animatorSet.cancel();
            }
            Log.d("setOnClickListener", "setOnClickListener");
        });
    }

    /**
     * Setup colors for status bar, background, and navigation bar& Setup day theme.
     */
    private void setupDefaultColors() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Off status bar
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigation_button_color, null)); // Change color of navigation bar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Off dark theme
    }

    /**
     * Change text color by click
     */
    private void changeTextColor() {
        if (this.getResources().getConfiguration().getLocales().get(0).getLanguage().equals("ru")) {
            text_label.setTextColor(Color.BLUE);
        } else {
            text_label.setTextColor(Color.RED);
        }
    }

    /**
     * Start animation according task
     */
    private void startTextViewAnimation(float initialY) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();

        ObjectAnimator downAnimation = ObjectAnimator.ofFloat(text_label, View.Y, initialY, displayMetrics.heightPixels - navigationBarOffset);
        ObjectAnimator upAnimation = ObjectAnimator.ofFloat(text_label, View.Y, displayMetrics.heightPixels - navigationBarOffset, 0);
        ObjectAnimator returnAnimation = ObjectAnimator.ofFloat(text_label, View.Y, 0, initialY);

        animatorSet.setDuration(10000);
        animatorSet.playSequentially(downAnimation, upAnimation, returnAnimation);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);

            }
        });
    }

    /**
     * Wiring layout views with activity code
     */
    private void initViews() {
        constraint_layout = findViewById(R.id.constraint_layout);
        text_label = findViewById(R.id.text_label);
    }

    public int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        @SuppressLint({"InternalInsetResource", "DiscouragedApi"}) int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}