package com.example.administrator.words;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.dpdp.base_moudle.utils.LogUtils;
import com.dpdp.base_moudle.utils.ToastUtil;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class WordApplication extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
        context = getApplicationContext();
    }

    private void init() {
        ToastUtil.init(this);
        LogUtils.init(this);
    }

    public static Context getAppContext() {
        return context;
    }
}
