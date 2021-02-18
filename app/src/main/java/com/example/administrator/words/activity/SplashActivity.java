package com.example.administrator.words.activity;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dpdp.base_moudle.base.BaseActivity;
import com.dpdp.base_moudle.image.GlideUtils;
import com.dpdp.base_moudle.interfaces.CustomAnimatorListener;
import com.example.administrator.words.R;
import com.example.administrator.words.helper.JumpPageHelper;

/**
 * 欢迎 界面
 */
public class SplashActivity extends BaseActivity {

    private TextView button;
    private EditText editText;
    private ImageView loadingIv;
    private ImageView welcomeIv;
    private LinearLayout guideLl;

    @Override
    protected String getRetTag() {
        return SplashActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.main_et_username);
        loadingIv = (ImageView) findViewById(R.id.loading_iv);
        guideLl = (LinearLayout) findViewById(R.id.guide_ll);
        welcomeIv = (ImageView) findViewById(R.id.welcome_iv);


        button = (TextView) findViewById(R.id.main_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpPageHelper.jumpMainActivity(SplashActivity.this,1);
                SplashActivity.this.finish();
            }
        });

        // 滑稽 gif
        //Glide.with(this).load(R.drawable.ui_loading).into(loadingIv);
        GlideUtils.load(this,R.drawable.ui_loading,loadingIv);
        // 欢迎 gif
       // Glide.with(this).load(R.drawable.welcome).into(welcomeIv);
        GlideUtils.load(this,R.drawable.welcome,welcomeIv);

        splashWelcomeAnimator();

    }

    /**
     * 渐变动画
     */
    private void splashWelcomeAnimator() {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 0f).setDuration(800);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = ((float) animation.getAnimatedValue());
                guideLl.setAlpha(animatedValue);
            }
        });
        valueAnimator.addListener(new CustomAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animation) {
                guideLl.setVisibility(View.GONE);
            }
        });
        guideLl.postDelayed(new Runnable() {
            @Override
            public void run() {
                valueAnimator.start();
            }
        }, 200);
    }
}
