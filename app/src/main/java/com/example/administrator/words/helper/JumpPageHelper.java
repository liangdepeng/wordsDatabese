package com.example.administrator.words.helper;

import android.content.Context;
import android.content.Intent;

import com.dpdp.base_moudle.utils.AppConstants;
import com.example.administrator.words.activity.MainActivity;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-04
 * <p>
 * Summary: 页面跳转帮助类
 */
public class JumpPageHelper {

    /**
     * {@link #jumpMainActivity(Context, int, int)}
     */
    public static void jumpMainActivity(Context context, int index) {
        jumpMainActivity(context, index, -1);
    }

    /**
     * 跳转首页 指定 tab 页面
     *
     * @param context 上下文
     * @param index tab 下标
     * @param subIndex tab 内如果有二级页面 则为 二级页面的下标 没有请用 {@link #jumpMainActivity(Context, int)}
     */
    public static void jumpMainActivity(Context context, int index, int subIndex) {
        context.startActivity(new Intent(context, MainActivity.class)
                .putExtra(AppConstants.PAGE_MAIN_ACTIVITY_INDEX, index)
                .putExtra(AppConstants.PAGE_MAIN_ACTIVITY_SUB_INDEX, subIndex));
    }

}
