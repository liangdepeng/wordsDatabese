package com.example.administrator.words;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.qmuiteam.qmui.util.QMUIViewHelper;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-22
 * <p>
 * Summary: View 视图 动画
 */
public class ViewAnimationUtils extends QMUIViewHelper {

    /**
     * View 出现 动画 透明度渐变
     */
    public static void ViewShowForAlpha(final View view) {
        if (view == null) return;
        view.setAlpha(0f);
        view.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f).setDuration(1200);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = ((float) animation.getAnimatedValue());
                if (view != null) {
                    view.setAlpha(animatedValue);
                }
            }
        });
        valueAnimator.start();
    }

    /**
     * View 消失 动画 透明度渐变
     */
    public static void ViewDismissForAlpha(final View view) {
        if (view == null) return;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(500);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = ((float) animation.getAnimatedValue());
                if (view != null) {
                    view.setAlpha(animatedValue);
                    if (animatedValue == 0f) {
                        view.setVisibility(View.GONE);
                    }
                }
            }
        });
        valueAnimator.start();
    }
}
