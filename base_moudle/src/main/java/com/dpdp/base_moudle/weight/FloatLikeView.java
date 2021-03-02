package com.dpdp.base_moudle.weight;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.dpdp.base_moudle.R;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-23
 * <p>
 * Summary: 点赞爱心动画
 * <p>
 * 固定宽高
 * 包含一个子view 或者没有 要放在最下面 动效爱心往上飘
 * <p>
 * @see #setLikeDrawables(int...) 设置 对应的 图片
 * @see #clickLikeView() 点击调用 开始动画
 */
public class FloatLikeView extends FrameLayout {

    private ArrayList<Drawable> mLikeDrawables;
    private Random mRandom;
    private final Context context;
    private int mLikePicWidth = 0;
    private int mLikePicHeight = 0;
    private LayoutParams mLikePicLayoutParams;
    private int mLikePicBottomMargin = 0;
    private int mChildHeight = 0;
    private int mWidth;
    private int mHeight;

    public FloatLikeView(@NonNull Context context) {
        this(context, null);
    }

    public FloatLikeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatLikeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        // 存放 要显示的 效果
        mLikeDrawables = new ArrayList<>();
        // 设置一个默认的爱心
        mLikeDrawables.add(ContextCompat.getDrawable(context, R.drawable.ui_default_heart));
        // 产生随机数 随机选择 出现的图形
        mRandom = new Random();
    }

    /**
     * 设置 动画飘动的资源文件
     */
    public void setLikeDrawables(int... drawableIds) {
        if (drawableIds != null && drawableIds.length > 0) {
            mLikeDrawables.clear();
            for (int drawableId : drawableIds) {
                mLikeDrawables.add(ContextCompat.getDrawable(context, drawableId));
            }
        }
    }

    /**
     * 点击调用 产生动效
     */
    public void clickLikeView() {

        if (mLikePicWidth == 0 || mLikePicHeight == 0 || mLikePicLayoutParams == null) {
            // 获取 点赞的 view 尺寸 ，这边定一个 统一尺寸
            mLikePicWidth = mLikeDrawables.get(0).getIntrinsicWidth();
            mLikePicHeight = mLikeDrawables.get(0).getIntrinsicHeight();

            mLikePicLayoutParams = new LayoutParams(mLikePicWidth, mLikePicHeight);
            mLikePicLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            mLikePicLayoutParams.bottomMargin = mLikePicBottomMargin;
        }

        ImageView likeIv = new ImageView(context);
        likeIv.setImageDrawable(mLikeDrawables.get(mRandom.nextInt(mLikeDrawables.size())));
        likeIv.setLayoutParams(mLikePicLayoutParams);
        addView(likeIv);
        addAnimationStart(likeIv);
    }


    private void addAnimationStart(ImageView likeIv) {
        //----------------------------------- 出现 的动画-----------------------------------
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(likeIv, "alpha", 0.5f, 1f);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(likeIv, "scaleX", 0.6f, 1f);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(likeIv, "scaleY", 0.6f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleXAnimator, scaleYAnimator);
        animatorSet.setDuration(200);
        animatorSet.setTarget(likeIv);

        //------------------------ 路径移动动画  基于 三阶贝塞儿曲线-------------------------
        PathEvaluator pathEvaluator = new PathEvaluator(getControlPointF(1), getControlPointF(2));
        // 设置 起点
        //   PointF startPointF = new PointF((float) (mWidth - mLikePicWidth) / 2, mHeight-mLikePicBottomMargin);
        PointF startPointF = new PointF((float) (mWidth - mLikePicWidth) / 2, mHeight - mLikePicBottomMargin - mLikePicHeight);
        // 设置 终点
        PointF endPointF = new PointF((float) mWidth / 2 + (mRandom.nextBoolean() ? 1 : -1) * mRandom.nextInt(100), 0);
        ValueAnimator valueAnimator = ValueAnimator.ofObject(pathEvaluator, startPointF, endPointF);
        valueAnimator.setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (likeIv == null) return;
                PointF pointF = (PointF) animation.getAnimatedValue();
                likeIv.setX(pointF.x);
                likeIv.setY(pointF.y);
                likeIv.setAlpha(1f - animation.getAnimatedFraction());
            }
        });
        valueAnimator.setTarget(likeIv);

        AnimatorSet animator = new AnimatorSet();
        animator.setTarget(likeIv);
        animator.playSequentially(animatorSet, valueAnimator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(likeIv);
            }
        });
        animator.start();

    }

    /**
     * 随机产生 三阶贝赛尔曲线 中间两个控制点
     *
     * @param value 控制点
     */
    private PointF getControlPointF(int value) {
        PointF pointF = new PointF();
        pointF.x = (float) mWidth / 2 - mRandom.nextInt(100);
        pointF.y = mRandom.nextInt((mHeight - mLikePicBottomMargin - mLikePicHeight) / value);
        return pointF;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();

        // 包含一个子view 或者没有 要放在最下面
        if (mChildHeight == 0 && childCount > 0) {
            View child = getChildAt(0);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            mChildHeight = child.getMeasuredHeight();
            mLikePicBottomMargin = mChildHeight / 2;
        }

        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();

        // WHHLog.e("FloatLikeView", "onMeasure     getHeight " + getHeight() + "    getWidth " + getWidth() + "    getMeasuredHeight " + getMeasuredHeight() + "   getMeasuredWidth " + getMeasuredWidth() + "  mLikePicBottomMargin  " + mLikePicBottomMargin);

    }

    private static class PathEvaluator implements TypeEvaluator<PointF> {

        private final PointF point01;
        private final PointF point02;

        public PathEvaluator(PointF point01, PointF point02) {
            this.point01 = point01;
            this.point02 = point02;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {

            float change = 1.0f - fraction;
            PointF pointF = new PointF();


            // 三阶贝塞儿曲线
            pointF.x = (float) Math.pow(change, 3) * startValue.x
                    + 3 * (float) Math.pow(change, 2) * fraction * point01.x
                    + 3 * change * (float) Math.pow(fraction, 2) * point02.x
                    + (float) Math.pow(fraction, 3) * endValue.x;
            pointF.y = (float) Math.pow(change, 3) * startValue.y
                    + 3 * (float) Math.pow(change, 2) * fraction * point01.y
                    + 3 * change * fraction * fraction * point02.y
                    + (float) Math.pow(fraction, 3) * endValue.y;

            // 二阶贝塞儿曲线
//            resultPointF.x = (float) Math.pow(leftTime, 2) * startValue.x + 2 * fraction * leftTime * ctrlPointF1.x
//                    + ((float) Math.pow(fraction, 2)) * endValue.x;
//            resultPointF.y = (float) Math.pow(leftTime, 2) * startValue.y + 2 * fraction * leftTime * ctrlPointF1.y
//                    + ((float) Math.pow(fraction, 2)) * endValue.y;

            return pointF;
        }
    }


}
