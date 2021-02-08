package com.example.administrator.words.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dpdp.base_moudle.base.BaseFragment;
import com.dpdp.base_moudle.base.SingleCallback;
import com.dpdp.base_moudle.utils.ArrayUtils;
import com.dpdp.base_moudle.utils.AsyncTaskUtil;
import com.dpdp.base_moudle.utils.AppConstants;
import com.dpdp.base_moudle.store.SpUtils;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.dpdp.base_moudle.dialog.XPopupUtil;
import com.example.administrator.words.MyWordsReceiver;
import com.example.administrator.words.R;
import com.example.administrator.words.Word;
import com.example.administrator.words.database.WordDataBaseDao;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;


/**
 * 背诵 子页面
 */
public class ReciteFragment extends BaseFragment implements View.OnClickListener, SingleCallback<Intent> {

    private TextView tvWord, tvTranslate;
    private ConstraintLayout loadingCl;
    private Button buttonComplete, buttonNotComplete, buttonNext;
    private View collectLl;
    private LikeButton collectLikeBtn;
    private ArrayList<Word> words;
    int i = 0;   //背诵数量
    private MyWordsReceiver wordsReceiver;

    @Override
    protected String getResetTag() {
        return ReciteFragment.class.getSimpleName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recite, container, false);
    }

    @Override
    public CharSequence getPageTitle() {
        return "单词背诵";
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerReceiver();
        tvWord = view.findViewById(R.id.recite_tv_word);
        loadingCl = view.findViewById(R.id.loading_cl);
        loadingCl.setVisibility(View.VISIBLE);
        tvTranslate = view.findViewById(R.id.recite_tv_translate);
        buttonComplete = view.findViewById(R.id.recite_btn_renshi);
        buttonNotComplete = view.findViewById(R.id.recite_btn_burenshi);
        buttonNext = view.findViewById(R.id.recite_btn_next);
        collectLl = view.findViewById(R.id.collect_ll);
        collectLikeBtn = view.findViewById(R.id.collect_button);
        setListener();

        if (SpUtils.getBoolean(mContext, AppConstants.DATA_BASE_INIT, false)) {
            queryWords();
        }
    }

    private void registerReceiver() {
        wordsReceiver = new MyWordsReceiver(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyWordsReceiver.ACTION_DATA_BASE_INIT_FINISHED);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(wordsReceiver,intentFilter);
    }

    public void queryWords() {
        AsyncTaskUtil.startTask(new AsyncTaskUtil.AsyncSimpleListener<ArrayList<Word>>() {
            @Override
            public ArrayList<Word> doWork() {
                return WordDataBaseDao.getInstance().queryAllWords(null);
            }

            @Override
            public void onComplete(ArrayList<Word> o) {
                words = o;
                String word = null;
                try {
                    word = words.get(0).word;
                    tvWord.setText(word);
                    collectLikeBtn.setLiked(words.get(0).isCollect);
                    loadingCl.setVisibility(View.GONE);
                } catch (Exception e) {
                    ToastUtil.showMsg(getActivity(), "单词库中没有数据");
                }
            }
        }, "query_words");
    }

    private void setListener() {
        buttonNext.setOnClickListener(this);
        buttonNotComplete.setOnClickListener(this);
        buttonComplete.setOnClickListener(this);

        collectLl.setOnClickListener(this);
        collectLikeBtn.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                // ToastUtil.showMsg("liked   " + collectLikeBtn.isLiked());
                WordDataBaseDao.getInstance().updateWordIsCollect(tvWord.getText().toString().trim(), true);
                ToastUtil.showMsg("收藏成功");
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                // ToastUtil.showMsg("unliked   " + collectLikeBtn.isLiked());
                WordDataBaseDao.getInstance().updateWordIsCollect(tvWord.getText().toString().trim(), false);
                ToastUtil.showMsg("取消收藏");
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.collect_ll) {
            collectLikeBtn.setLiked(!collectLikeBtn.isLiked());
        } else if (id == R.id.recite_btn_renshi) {//点击认识，删除单词，跳入下一个单词

            if (ArrayUtils.isEmpty(words)) {
                ToastUtil.showMsg("单词库为空 或 被删除");
                return;
            }

            WordDataBaseDao.getInstance().updateWordIsComplete(tvWord.getText().toString(), true);
            ToastUtil.showMsg("单词已标记为 已完成，继续加油噢~");
        } else if (id == R.id.recite_btn_burenshi) {//点击不认识，显示翻译


            if (ArrayUtils.isEmpty(words)) {
                ToastUtil.showMsg("单词库为空 或 被删除");
                return;
            }
            XPopupUtil.showTipPopup(mContext, "", "确定不再想一想，要直接查看答案", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    tvTranslate.setText(words.get(i).translate);
                }
            });
        } else if (id == R.id.recite_btn_next) {//点击下一个，显示下一个单词

            if (ArrayUtils.isEmpty(words)) {
                ToastUtil.showMsg("单词库为空 或 被删除");
                return;
            }

            if (i >= words.size() - 1)    //如果是最后一个
            {
                XPopupUtil.showTipPopup(mContext, "提示", "你很厉害，已经是最后一个单词了，确定是否重新开始", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        i = 0;
                        tvWord.setText(words.get(i).word);
                        tvTranslate.setText("");
                        collectLikeBtn.setLiked(words.get(i).isCollect);
                    }
                });
            } else {
                i++;
                collectLikeBtn.setLiked(words.get(i).isCollect);
                tvWord.setText(words.get(i).word);
                tvTranslate.setText("");
            }
        }
    }

    @Override
    public void callback(Intent intent) {
        if (intent != null && MyWordsReceiver.ACTION_DATA_BASE_INIT_FINISHED.equals(intent.getAction())) {
            queryWords();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(wordsReceiver);
    }
}
