//package com.example.administrator.words.activity;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.util.Log;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import androidx.localbroadcastmanager.content.LocalBroadcastManager;
//
//import com.dpdp.base_moudle.base.BaseActivity;
//import com.dpdp.base_moudle.base.SingleCallback;
//import com.dpdp.base_moudle.utils.Constants;
//import com.dpdp.base_moudle.store.SpUtils;
//import com.dpdp.base_moudle.utils.ToastUtil;
//import com.example.administrator.words.MyResetWordsReceiver;
//import com.example.administrator.words.R;
//import com.example.administrator.words.database.WordDataBaseDao;
//import com.example.administrator.words.fragments.InputFragment;
//import com.example.administrator.words.fragments.ReciteFragment;
//import com.example.administrator.words.fragments.SelfFragment;
//import com.example.administrator.words.manager.TabFragmentManager;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.lang.ref.WeakReference;
//
////import androidx.fragment.app.FragmentgetSupportFragmentManager().beginTransaction();
//
///**
// * 主界面
// */
//public class MainActivityStore extends BaseActivity implements SingleCallback<Object> {
//
//    private InputFragment fragmentInput;
//    private ReciteFragment fragmentRecite;
//    private SelfFragment fragmentSelf;
//    public WordDataBaseDao dataBaseDao;
//    private AsyncTask<Object, Integer, Integer> task;
//
//    private MyResetWordsReceiver wordsReceiver;
//    private RadioButton reciteBtn;
//    private TabFragmentManager tabFragmentManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_frist);
////        Button buttonInput = (Button) findViewById(R.id.frist_btn_input);
////        Button buttonRecite = (Button) findViewById(R.id.frist_btn_recite);
////        Button buttonSelf = (Button) findViewById(R.id.frist_btn_self);
//
//        // 注册本地广播 实现 fragment 和 activity 通信
//        registerReceiver();
//
//        //     getSupportFragmentManager().beginTransaction() = getSupportFragmentManager().begingetSupportFragmentManager().beginTransaction()();
//
//        initTabBar();
//
//        ImageView bgV = (ImageView) findViewById(R.id.background_11);
//        bgV.setImageAlpha(40);
//
//        TextView name = (TextView) findViewById(R.id.name);
//        reciteBtn = findViewById(R.id.radio_btn_recite);
//
////        fragmentInput = new InputFragment();
////        fragmentRecite = new ReciteFragment();
////        fragmentSelf = new SelfFragment();
//
//        // dbOpenHelper = new DBOpenHelper(MainActivity.this, "tb_dict", null, 1);
//        // DBOpenHelper dbOpenHelper = DBOpenHelper.getInstance();
//
//
//        // 若果没有添加过数据库 要去添加数据库
////        if (!SpUtils.getBoolean(this, DataHelper.isWrited, false)) {
////
//////            showCustomLoadingDialog();
//////
//////            DataHelper dataHelper = new DataHelper(this);
//////            dataHelper.writeWords();
//////
//////            button_self.postDelayed(new Runnable() {
//////                @Override
//////                public void run() {
//////                    dismissCustomLoadingDialog();
//////                }
//////            }, 3000);
////        }
//
//        //获取ID
//        Bundle buddle = getIntent().getExtras();
//        name.setText(buddle.getString("name"));
////        //点击录入按钮
////        buttonInput.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                getSupportFragmentManager().beginTransaction().replace(R.id.first_fl, fragmentInput).commitAllowingStateLoss();
////            }
////        });
////
////        //点击背诵按钮
////        buttonRecite.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                getSupportFragmentManager().beginTransaction().replace(R.id.first_fl, fragmentRecite).commitAllowingStateLoss();
////
////            }
////        });
////
////        //点击个人按钮
////        buttonSelf.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                getSupportFragmentManager().beginTransaction().replace(R.id.first_fl, fragmentSelf).commitAllowingStateLoss();
////            }
////        });
//
//        //默认录入界面
//     //   getSupportFragmentManager().beginTransaction().add(R.id.first_fl, fragmentRecite).commitAllowingStateLoss();
//
//        dataBaseDao = WordDataBaseDao.getInstance();
//
//        if (!SpUtils.getBoolean(this, Constants.DATA_BASE_INIT, false)) {
//            setWordInitDataBase();
//            ToastUtil.showMsg("数据读取开始");
//        }
//    }
//
//    private void registerReceiver() {
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(Constants.DATA_RE_SET);
//        wordsReceiver = new MyResetWordsReceiver(this);
//        LocalBroadcastManager.getInstance(this).registerReceiver(wordsReceiver, intentFilter);
//    }
//
//    private void setWordInitDataBase() {
//        task = new MyTask(MainActivityStore.this);
//        task.execute("start");
//    }
//
//    private void initTabBar() {
//        tabFragmentManager = new TabFragmentManager(this, R.id.first_fl);
//        tabFragmentManager.switchTab(1);
//
////        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
////        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
////            @SuppressLint("NonConstantResourceId")
////            @Override
////            public void onCheckedChanged(RadioGroup group, int checkedId) {
////                switch (checkedId) {
////                    case R.id.radio_btn_input:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.first_fl, fragmentInput).commitAllowingStateLoss();
////                        break;
////                    case R.id.radio_btn_recite:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.first_fl, fragmentRecite).commitAllowingStateLoss();
////                        break;
////                    case R.id.radio_btn_self:
////                        getSupportFragmentManager().beginTransaction().replace(R.id.first_fl, fragmentSelf).commitAllowingStateLoss();
////                        break;
////                    default:
////                        break;
////                }
////            }
////        });
//    }
//
//    private long lastTimeMills = 0;
//
//    @Override
//    public void onBackPressed() {
//        long currentTimeMills = System.currentTimeMillis();
//        long waitTimeMills = 2000;
//        if (currentTimeMills - lastTimeMills < waitTimeMills) {
//            Runtime.getRuntime().exit(0);
//        } else {
//            lastTimeMills = currentTimeMills;
//            ToastUtil.showMsg("两秒之内再按一次退出app");
//        }
//    }
//
//    @Override
//    public void showLoadingDialog() {
//        if (progressDialog == null) {
//            progressDialog = new ProgressDialog(this);
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.setMax(100);
//            progressDialog.setTitle("提示");
//            progressDialog.setMessage("请稍后，数据库初始化...");
//        }
//        progressDialog.show();
//        progressDialog.setProgress(0);
//    }
//
//    @Override
//    public void callback(Object data) {
//        if (data instanceof Intent) {
//            Intent intent = (Intent) data;
//            if (intent != null && Constants.DATA_RE_SET.equals(intent.getAction())) {
//                setWordInitDataBase();
//            }
//        }
//
//    }
//
//    /**
//     * 后台线程 开启数据库 写入 10W + 单词数据
//     * <p>
//     * sql 文件 被修改过一点 ，读取每一行 进行修改 修改为数据库的结构然后 插入  sqlite 数据库
//     */
//    private static class MyTask extends AsyncTask<Object, Integer, Integer> {
//
//        /**
//         * 持有外部activity 的弱引用 防止内存泄露 便于gc回收
//         */
//        private final WeakReference<MainActivityStore> weakReference;
//        /**
//         * 计时
//         */
//        private long startTimeMills;
//
//        public MyTask(MainActivityStore mainActivity) {
//            weakReference = new WeakReference<>(mainActivity);
//        }
//
//        /**
//         * 工作线程 -- 子线程
//         */
//        @Override
//        protected Integer doInBackground(Object... objects) {
//            // return FileUtils.readAssetsFile(weakReference.get());
//
//            long l = System.currentTimeMillis();
//            int count = 0;
//            try {
//
//                // 读取sql文件
//                InputStream inputStream = weakReference.get().getAssets().open("words_105k.sql");
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//                String sqlLine = "";
//
//                while ((sqlLine = bufferedReader.readLine()) != null) {
//                    //为了匹配数据库字段进行增减 删除转义字符 纠正数据库 表字段
//                    sqlLine = sqlLine.substring(0, sqlLine.length() - 2) + ",'0','0')";
//                    sqlLine = sqlLine.replace("\\'", "");
//
//                    // 写入数据库
//                    weakReference.get().dataBaseDao.insertBySql(sqlLine);
//                    count++;
//
//                    // 因数据过于庞大 每100次进行 进度更新
//                    if (count % 100 == 0) {
//                        int dev = (int) (count / 103976f * 100);
//                        publishProgress(dev);
//                    }
//
//                    if (count>10000){
//                        break;
//                    }
//                }
//                long l1 = System.currentTimeMillis();
//                Log.e("TIME_SQL_READ_100000---", (l1 - l) / 1000 + "秒");
//                inputStream.close();
//                inputStreamReader.close();
//                bufferedReader.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return count;
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            startTimeMills = System.currentTimeMillis();
//            weakReference.get().showLoadingDialog();
//        }
//
//        @Override
//        protected void onPostExecute(Integer count) {
//            super.onPostExecute(count);
//            long endTimeMills = System.currentTimeMillis();
//            weakReference.get().dismissDialog();
//            SpUtils.putBoolean(weakReference.get(), Constants.DATA_BASE_INIT, true);
//
//            if (weakReference.get().reciteBtn.isChecked()) {
//              //  weakReference.get().fragmentRecite.queryWords();
//            }
//
//            ToastUtil.showMsg(count + "条 数据 已读取完成,共花费  " + (endTimeMills - startTimeMills) / 1000 + "  秒");
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            if (weakReference.get().progressDialog != null) {
//                weakReference.get().progressDialog.setProgress(values[0]);
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
//            task.cancel(true);
//        }
//        if (wordsReceiver != null) {
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(wordsReceiver);
//        }
//    }
//}
