package com.skylite.animator.controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.widget.TextView;

/**
 * Class contains methods that change appearance of TextView
 * @
 */
public class TextViewTransformationUtil {

    /**
     * Change color of text according task
     *
     * @param text_label view, that need to modify
     */
    public static void changeTextColor(TextView text_label) {
        if (text_label.getResources().getConfiguration().getLocales().get(0).getLanguage().equals("ru")) {
            text_label.setTextColor(Color.BLUE);
        } else {
            text_label.setTextColor(Color.RED);
        }
    }

    /**
     * Method returns height of navigation bar
     *
     * @param context for request context resources
     * @return height of navigation bar
     */
    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        @SuppressLint({"InternalInsetResource", "DiscouragedApi"}) int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


}
