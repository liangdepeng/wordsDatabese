package com.example.administrator.words.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dpdp.base_moudle.base.BaseFragment;
import com.dpdp.base_moudle.dialog.XPopupUtil;
import com.dpdp.base_moudle.store.SpUtils;
import com.dpdp.base_moudle.utils.AppConstants;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.example.administrator.words.MyWordsReceiver;
import com.example.administrator.words.R;
import com.example.administrator.words.activity.BigImageActivity;
import com.example.administrator.words.activity.MainActivity;
import com.example.administrator.words.database.WordDataBaseDao;
import com.example.administrator.words.helper.JumpPageHelper;
import com.lxj.xpopup.interfaces.OnConfirmListener;


/**
 * 个人中心 子页面
 */
public class SelfFragment extends BaseFragment {

    private TextView resetWords;
    private TextView deleteWords;

    @Override
    protected String getResetTag() {
        return SelfFragment.class.getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_self, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final View userLogoIv = view.findViewById(R.id.user_logo_iv);
        userLogoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  startActivity(new Intent(mActivity, TestBindActivity.class));
                BigImageActivity.Companion.startActivity((MainActivity) mActivity, userLogoIv, getString(R.string.image_translation));
            }
        });
        // 单词库
        TextView textViewWordDataBase = view.findViewById(R.id.self_tv_words);
        // 解释说明
        TextView textViewExplain = view.findViewById(R.id.self_tv_explain);
        // 重置单词库
        resetWords = view.findViewById(R.id.reset_words);
        // 删除 单词库
        deleteWords = view.findViewById(R.id.delete_words);
        // 收藏列表
        TextView collectWords = view.findViewById(R.id.collect_words);
        // 趣味翻译
        TextView translateWords = view.findViewById(R.id.trans_words);
        // 点击单词库
        textViewWordDataBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpPageHelper.jumpWordListActivity(mContext);
            }
        });
        collectWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpPageHelper.jumpCollectListActivity(mContext);
            }
        });
        translateWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpPageHelper.jumpTranslateActivity(mContext);
            }
        });
        //点击软件说明
        textViewExplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JumpPageHelper.jumpExplainActivity(mContext);

//                String infoText = getString(R.string.text_part_1) + "\n" +
//                        getString(R.string.text_part_2) + "\n" +
//                        getString(R.string.text_part_3) + "\n" +
//                        getString(R.string.text_part_4) + "\n" +
//                        getString(R.string.text_part_5) + "\n" +
//                        getString(R.string.text_part_6);
//                XPopupUtil.showSinglePopup(mContext, "软件说明", infoText);
            }
        });

        resetWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XPopupUtil.showTipPopup(mContext, "提示", "确定清空所有进度，重置单词库，此操作不可逆，谨慎操作", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        WordDataBaseDao.getInstance().deleteAll();
                        SpUtils.putBoolean(mActivity, AppConstants.DATA_BASE_INIT, false);
                        showCustomLoadingDialog();
                        Intent intent = new Intent();
                        intent.setAction(MyWordsReceiver.ACTION_DATA_BASE_DATA_RE_SET);
                        LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
                        resetWords.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismissCustomLoadingDialog();
                                ToastUtil.showMsg("重置成功！");
                            }
                        }, 1500);
                    }
                });
            }
        });

        deleteWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XPopupUtil.showTipPopup(mActivity, "警告", "此操作将删除全部单词", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        WordDataBaseDao.getInstance().deleteAll();
                        SpUtils.putBoolean(mActivity, AppConstants.DATA_BASE_INIT, false);
                        showCustomLoadingDialog();
                        deleteWords.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dismissCustomLoadingDialog();
                                ToastUtil.showMsg("清理成功！");
                            }
                        }, 2000);
                    }
                });
            }
        });
    }
}
