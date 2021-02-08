package com.example.administrator.words.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.BaseActivity;
import com.dpdp.base_moudle.base.BaseViewHolder;
import com.dpdp.base_moudle.base.ListViewBaseAdapter;
import com.example.administrator.words.R;
import com.example.administrator.words.Word;
import com.example.administrator.words.database.WordDataBaseDao;
import com.dpdp.base_moudle.utils.AsyncTaskUtil;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.dpdp.base_moudle.dialog.XPopupUtil;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import java.util.ArrayList;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-16
 * <p>
 * Summary:
 */
public class CollectListActivity extends BaseActivity {

    private ListView collectListView;
    private ArrayList<Word> allWords;
    private ListViewBaseAdapter<Word> adapter;

    @Override
    protected String getRetTag() {
        return CollectListActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        setTitle("收藏列表", true);
        collectListView = findViewById(R.id.collect_listview);
        initData();

    }

    private void initData() {
      //  showCustomLoadingDialog();
        AsyncTaskUtil.startTask(new AsyncTaskUtil.AsyncSimpleListener<ArrayList<Word>>(){
            @Override
            public void onPreExecute() {
                showCustomLoadingDialog();
            }

            @Override
            public void onComplete(ArrayList<Word> o) {
                allWords = o;
                dismissCustomLoadingDialog();
                initListView();
            }

            @Override
            public ArrayList<Word> doWork() {
                return  WordDataBaseDao.getInstance().queryAllWords(new WordDataBaseDao.ICheckWords() {
                    @Override
                    public boolean checkWordIsNeed(Word word) {
                        if (word != null) {
                            return word.isCollect;
                        }
                        return false;
                    }
                });
            }
        },"word_list_collect");
    }

    private void initListView() {
        adapter = new ListViewBaseAdapter<Word>(this, allWords) {

            @Override
            protected void onBindItemData(BaseViewHolder viewHolder, int position, final Word item) {
                viewHolder.setText(R.id.word_tv, item.word)
                        .setText(R.id.translate_tv, item.translate)
                        .setText(R.id.index_tv, (position + 1) + " .")
                        .setOnClickListener(R.id.un_like_btn, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                XPopupUtil.showTipPopup(CollectListActivity.this, "提示", "确定要取消收藏 " + item.word + " 这个单词吗？取消之后可以在背诵界面继续收藏噢~", new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        allWords.remove(item);
                                        WordDataBaseDao.getInstance().updateWordIsCollect(item.word, false);
                                        adapter.notifyDataSetChanged();
                                        ToastUtil.showMsg("取消收藏成功");
                                    }
                                });
                            }
                        });
            }

            @Override
            protected int getItemLayoutResId() {
                return R.layout.item_collect_list;
            }
        };
        collectListView.postDelayed(new Runnable() {
            @Override
            public void run() {
                collectListView.setAdapter(adapter);
                dismissCustomLoadingDialog();
            }
        }, 200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AsyncTaskUtil.cancelTask("word_list_collect");
    }
}
