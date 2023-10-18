package com.skylite.animator.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.skylite.animator.R;

public class MainActivity extends AppCompatActivity {

    private AnimationUtil animationUtil;
    private ConstraintLayout constraint_layout;
    private TextView text_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default method calls
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial setup method calls
        setupDefaultColors();
        initViews();
        initViewListener();
        initMembers();
    }

    private void initMembers() {
        animationUtil = new AnimationUtil(text_label);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initViewListener() {
        constraint_layout.setOnTouchListener((view, event) -> {
            if (animationUtil.getAnimationSet().isRunning()) {
                animationUtil.getAnimationSet().cancel();
            }

            TextViewTransformationUtil.changeTextColor(text_label);

            float x = event.getX();
            float y = event.getY();
            text_label.setX(x);
            text_label.setY(y);

            animationUtil.startTextViewAnimation(y, 5000);
            return false;
        });

        text_label.setOnClickListener(v -> animationUtil.getAnimationSet().pause());
    }

    /**
     * Setup colors for status bar, background, and navigation bar. Setup day theme.
     */
    private void setupDefaultColors() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // Off status bar
        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigation_button_color, null)); // Change color of navigation bar
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO); // Off dark theme
    }

    /**
     * Wiring layout views with activity code
     */
    private void initViews() {
        constraint_layout = findViewById(R.id.constraint_layout);
        text_label = findViewById(R.id.text_label);
    }

}