package com.dpdp.base_moudle.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;


/**
 * 弱提示
 */

public class ToastUtil {

    private static Context appContext;

    public static void init(Context appCtx) {
        appContext = appCtx;
    }

    public static void showMsg(CharSequence msg) {
        showMsg(null, msg);
    }

    @Deprecated
    public static void showMsg(Context context, CharSequence msg) { //显示信息的方法，传参：界面信息，要显示的信息
        try {
            Toast toast = Toast.makeText(appContext, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}