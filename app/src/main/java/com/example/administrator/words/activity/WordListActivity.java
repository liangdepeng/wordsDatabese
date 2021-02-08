package com.example.administrator.words.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.dpdp.base_moudle.base.BaseActivity;
import com.dpdp.base_moudle.base.SingleCallback;
import com.dpdp.base_moudle.dialog.XPopupUtil;
import com.dpdp.base_moudle.utils.AsyncTaskUtil;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.example.administrator.words.R;
import com.example.administrator.words.Word;
import com.example.administrator.words.adapter.WordListAdapter;
import com.example.administrator.words.database.WordDataBaseDao;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;

/**
 * 单词列表
 */
public class WordListActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_base);
        setTitle("单词列表", true);
        initWordList();
    }

    private ArrayList<Word> words;

    private void initWordList() {

        AsyncTaskUtil.startTask(new AsyncTaskUtil.AsyncSimpleListener<ArrayList<Word>>() {
            @Override
            public void onPreExecute() {
                showCustomLoadingDialog();
            }

            @Override
            public void onComplete(ArrayList<Word> o) {
                words = o;
                dismissCustomLoadingDialog();
                final ListView listView = (ListView) findViewById(R.id.list);
                final WordListAdapter wordListAdapter = new WordListAdapter(WordListActivity.this, words);
                wordListAdapter.setDeleteCallback(new SingleCallback<Word>() {
                    @Override
                    public void callback(final Word data) {
                        if (!data.isComplete) {
                            XPopupUtil.showSinglePopup(WordListActivity.this, "提示", "单词还没有背会，不能删除噢");
                            return;
                        }

                        XPopupUtil.showTipPopup(WordListActivity.this, "提示", "确定要删除 " + data.word + " 这个单词吗", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                showCustomLoadingDialog();
                                listView.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        WordDataBaseDao.getInstance().deleteWord(data.word);
                                        words.remove(data);
                                        wordListAdapter.notifyDataSetChanged();
                                        dismissCustomLoadingDialog();
                                        ToastUtil.showMsg(data.word + " 删除成功！");
                                    }
                                }, 2000);
                            }
                        });

                    }
                });
                listView.setAdapter(wordListAdapter);
            }

            @Override
            public ArrayList<Word> doWork() {
                return WordDataBaseDao.getInstance().queryAllWords(null);
            }
        }, "word_list_all");

    }

    @Override
    protected String getRetTag() {
        return WordListActivity.class.getSimpleName();
    }
}
