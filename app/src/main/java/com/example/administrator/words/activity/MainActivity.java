package com.example.administrator.words.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dpdp.base_moudle.base.BaseActivity;
import com.dpdp.base_moudle.base.SingleCallback;
import com.dpdp.base_moudle.store.SpUtils;
import com.dpdp.base_moudle.utils.AppConstants;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.example.administrator.words.MyWordsReceiver;
import com.example.administrator.words.R;
import com.example.administrator.words.database.WordDataBaseDao;
import com.example.administrator.words.manager.TabFragmentManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

/**
 * 主界面 等比缩放
 */
public class MainActivity extends BaseActivity implements SingleCallback<Intent> {

    public WordDataBaseDao dataBaseDao;
    private AsyncTask<Object, Integer, Integer> task;
    private MyWordsReceiver wordsReceiver;
    private TabFragmentManager tabFragmentManager;

    @Override
    protected String getRetTag() {
        return MainActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        // 注册本地广播 实现 fragment 和 activity 通信
        registerReceiver();
        // 初始化 底部 tab 页面
        initTabBar();
        // 初始化 数据库
        dataBaseDao = WordDataBaseDao.getInstance();

        // 首次安装 要初始化数据库
        if (!SpUtils.getBoolean(this, AppConstants.DATA_BASE_INIT, false)) {
            setWordInitDataBase();
            ToastUtil.showMsg("数据读取开始");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (isFinishing() || isDestroyed()) {
            return;
        }
        if (intent != null) {
            int pageIndex = intent.getIntExtra(AppConstants.PAGE_MAIN_ACTIVITY_INDEX, 0);
            int subIndex = intent.getIntExtra(AppConstants.PAGE_MAIN_ACTIVITY_SUB_INDEX, -1);
            jumpIndexPage(pageIndex);
        }
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyWordsReceiver.ACTION_DATA_BASE_DATA_RE_SET);
        wordsReceiver = new MyWordsReceiver(this);
        LocalBroadcastManager.getInstance(this).registerReceiver(wordsReceiver, intentFilter);
    }

    private void setWordInitDataBase() {
        task = new MyTask(MainActivity.this);
        task.execute("start");
    }

    private void initTabBar() {
        tabFragmentManager = new TabFragmentManager(this, R.id.first_fl);
        tabFragmentManager.switchTab(1);
    }


    public void jumpIndexPage(int index) {
        if (tabFragmentManager != null) {
            tabFragmentManager.switchTab(index);
        }
    }

    private long lastTimeMills = 0;

    @Override
    public void onBackPressed() {
        long currentTimeMills = System.currentTimeMillis();
        long waitTimeMills = 2000;
        if (currentTimeMills - lastTimeMills < waitTimeMills) {
            Runtime.getRuntime().exit(0);
        } else {
            lastTimeMills = currentTimeMills;
            ToastUtil.showMsg("两秒之内再按一次退出app");
        }
    }

    @Override
    public void showLoadingDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMax(100);
            progressDialog.setTitle("提示");
            progressDialog.setMessage("请稍后，数据库初始化...");
        }
        progressDialog.show();
        progressDialog.setProgress(0);
    }

    @Override
    public void callback(Intent intent) {
        if (intent != null && MyWordsReceiver.ACTION_DATA_BASE_DATA_RE_SET.equals(intent.getAction())) {
            setWordInitDataBase();
        }
    }

    /**
     * 后台线程 开启数据库 写入 10W + 单词数据
     * <p>
     * sql 文件 被修改过一点 ，读取每一行 进行修改 修改为数据库的结构然后 插入  sqlite 数据库
     */
    private static class MyTask extends AsyncTask<Object, Integer, Integer> {

        /**
         * 持有外部activity 的弱引用 防止内存泄露 便于gc回收
         */
        private final WeakReference<MainActivity> weakReference;
        /**
         * 计时
         */
        private long startTimeMills;

        public MyTask(MainActivity mainActivity) {
            weakReference = new WeakReference<>(mainActivity);
        }

        /**
         * 工作线程 -- 子线程
         */
        @Override
        protected Integer doInBackground(Object... objects) {
            // return FileUtils.readAssetsFile(weakReference.get());

            long l = System.currentTimeMillis();
            int count = 0;
            try {

                // 读取sql文件
                InputStream inputStream = weakReference.get().getAssets().open("words_105k.sql");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String sqlLine = "";

                while ((sqlLine = bufferedReader.readLine()) != null) {
                    //为了匹配数据库字段进行增减 删除转义字符 纠正数据库 表字段
                    sqlLine = sqlLine.substring(0, sqlLine.length() - 2) + ",'0','0')";
                    sqlLine = sqlLine.replace("\\'", "");

                    // 写入数据库
                    weakReference.get().dataBaseDao.insertBySql(sqlLine);
                    count++;

                    // 因数据过于庞大 每100次进行 进度更新
                    if (count % 100 == 0) {
                        int dev = (int) (count / 103976f * 100);
                        publishProgress(dev);
                    }

                    if (count > 10000) {
                        break;
                    }
                }
                long l1 = System.currentTimeMillis();
                Log.e("TIME_SQL_READ_100000---", (l1 - l) / 1000 + "秒");
                inputStream.close();
                inputStreamReader.close();
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return count;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            startTimeMills = System.currentTimeMillis();
            weakReference.get().showLoadingDialog();
        }

        @Override
        protected void onPostExecute(Integer count) {
            super.onPostExecute(count);
            long endTimeMills = System.currentTimeMillis();
            weakReference.get().dismissDialog();
            SpUtils.putBoolean(weakReference.get(), AppConstants.DATA_BASE_INIT, true);
            Intent intent = new Intent();
            intent.setAction(MyWordsReceiver.ACTION_DATA_BASE_INIT_FINISHED);
            LocalBroadcastManager.getInstance(weakReference.get()).sendBroadcast(intent);
            ToastUtil.showMsg(count + "条 数据 已读取完成,共花费  " + (endTimeMills - startTimeMills) / 1000 + "  秒");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (weakReference.get().progressDialog != null) {
                weakReference.get().progressDialog.setProgress(values[0]);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true);
        }
        if (wordsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(wordsReceiver);
        }
    }
}
