package com.dpdp.base_moudle.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dpdp.base_moudle.R;
import com.dpdp.base_moudle.interfaces.IBaseView;
import com.dpdp.base_moudle.utils.LogUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary: BaseActivity Activity 基类
 */
public class BaseActivity extends AppCompatActivity implements IBaseView {

    protected String tag = getRetTag();

    protected ProgressDialog progressDialog;
    private View backV;
    private View backRealV;
    private TextView titleTv;
    private LoadingPopupView loadingPopupView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(tag,"-----onCreate-----");
    }

    public void setTitle(CharSequence title, boolean isShowBackIcon) {
        if (getWindow().getDecorView().findViewById(R.id.title_bar) == null) {
            throw new RuntimeException("界面未添加 title 布局 或调用时机不正确 ");
        }

        backV = findViewById(R.id.back_v);
        backRealV = findViewById(R.id.back_real_v);
        titleTv = findViewById(R.id.title_tv);

        if (backRealV != null && backV != null) {
            backV.setVisibility(isShowBackIcon ? View.VISIBLE : View.GONE);
            backRealV.setVisibility(isShowBackIcon ? View.VISIBLE : View.GONE);
            if (isShowBackIcon) {
                backV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }

        if (titleTv != null) {
            titleTv.setText(title);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtils.e(tag,"-----onStart-----");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e(tag,"-----onResume-----");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.e(tag,"-----onPause-----");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.e(tag,"-----onStop-----");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.e(tag,"-----onSaveInstanceState-----");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtils.e(tag,"-----onRestoreInstanceState-----");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.e(tag,"-----onNewIntent-----");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtils.e(tag,"-----onRestart-----");
    }

    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载中...");
            progressDialog.setTitle("提示");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissDialog() {
        if (progressDialog == null)
            return;

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showCustomLoadingDialog() {
        if (loadingPopupView == null) {
            loadingPopupView = new XPopup.Builder(this).asLoading("加载中");
        }
        loadingPopupView.show();
    }

    @Override
    public void dismissCustomLoadingDialog() {
        if (loadingPopupView != null) {
            loadingPopupView.delayDismiss(100);
        }
    }

    @Override
    public CharSequence getPageTitle() {
        return "";
    }

    @Override
    protected void onDestroy() {
        LogUtils.e(tag,"-----onDestroy-----");
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        super.onDestroy();
    }

    protected String getRetTag() {
        return BaseActivity.class.getSimpleName();
    }
}
