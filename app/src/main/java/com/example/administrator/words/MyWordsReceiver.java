package com.example.administrator.words;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.dpdp.base_moudle.base.SingleCallback;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-24
 * <p>
 * Summary: 本地广播 高效的实现 基本的通信通知功能
 */
public class MyWordsReceiver extends BroadcastReceiver {

    /**
     * 数据库 初始化完成
     */
    public static final String ACTION_DATA_BASE_INIT_FINISHED = "com.example.ldp.words.data_base_init_finished";
    /**
     * 重新 设置数据库
     */
    public static final String ACTION_DATA_BASE_DATA_RE_SET = "com.example.ldp.words.data.reset";

    /**
     * 结果回调
     */
    private final SingleCallback<Intent> callback;

    public MyWordsReceiver(SingleCallback<Intent> callback) {
        this.callback = callback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (callback != null) {
            callback.callback(intent);
        }
    }
}
