package com.example.administrator.words.activity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.ui.BaseActivity;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.example.administrator.words.ViewAnimationUtils;
import com.example.administrator.words.databinding.ActivityLoginInBinding;
import com.qmuiteam.qmui.util.QMUIDirection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary:
 */
public class LoginActivity extends BaseActivity {

    private ActivityLoginInBinding loginInBinding;
    public static final String PASS = "pass";//通过
    public static final String CANCEL = "cancel";//取消
    private WebView webview;
    private int animationState = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginInBinding = ActivityLoginInBinding.inflate(LayoutInflater.from(this));
        setContentView(loginInBinding.getRoot());
        initView();

    }

    private void initView() {
        initWebView();
        loginInBinding.testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testYanzhengma();
            }
        });
        loginInBinding.animationTestTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationState == 0) {
                    ViewAnimationUtils.slideIn(loginInBinding.spaceView, 2000, null, true, QMUIDirection.LEFT_TO_RIGHT);
                    animationState = 1;
                } else {
                    ViewAnimationUtils.slideOut(loginInBinding.spaceView, 2000, null, true, QMUIDirection.RIGHT_TO_LEFT);
                    animationState = 0;
                }
            }
        });
    }

    public void testYanzhengma() {
        webview.loadUrl("https://v.vaptcha.com/app/android.html?vid=6033219e26af12b134e54f77&scene=1&lang=zh-CN&offline_server=https://www.vaptchadowntime.com/dometime");
        ViewAnimationUtils.ViewShowForAlpha(loginInBinding.cardView);
    }

    private void initWebView() {
        webview = loginInBinding.webView;
        //设置webview颜色为透明
        webview.setBackgroundColor(Color.TRANSPARENT);
        webview.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        // 禁止缓存加载，以确保可获取最新的验证图片。
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置不使用默认浏览器，而直接使用WebView组件加载页面。
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loginInBinding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loginInBinding.progressBar.setVisibility(View.GONE);
            }
        });
        // 设置WebView组件支持加载JavaScript。
        webview.getSettings().setJavaScriptEnabled(true);
        // 建立JavaScript调用Java接口的桥梁。
        webview.addJavascriptInterface(new vaptchaInterface(), "vaptchaInterface");
    }

    public class vaptchaInterface {
        @JavascriptInterface
        public void signal(String json) {
            //json格式{signal:"",data:""}
            //signal: pass (通过) ； cancel（取消）
            try {
                JSONObject jsonObject = new JSONObject(json);
                String signal = jsonObject.getString("signal");
                if (PASS.equals(signal)) {//通过
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMsg("验证通过");
                            //   webview.setVisibility(View.GONE);
                            ViewAnimationUtils.ViewDismissForAlpha(loginInBinding.cardView);
                        }
                    });
                } else if (CANCEL.equals(signal)) {//取消
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showMsg("验证取消");
                            // webview.setVisibility(View.GONE);
                            ViewAnimationUtils.ViewDismissForAlpha(loginInBinding.cardView);
                        }
                    });
                } else {//其他html页面返回的状态参数
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String getRetTag() {
        return LoginActivity.class.getSimpleName();
    }

}
