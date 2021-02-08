package com.example.administrator.words.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.administrator.words.R;
import com.example.administrator.words.Word;
import com.dpdp.base_moudle.base.BaseViewHolder;
import com.dpdp.base_moudle.base.ListViewBaseAdapter;
import com.dpdp.base_moudle.base.SingleCallback;

import java.util.List;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class WordListAdapter extends ListViewBaseAdapter<Word> {

    private SingleCallback<Word> singleCallback;

    public WordListAdapter(Context context, List<Word> list) {
        super(context, list);
    }

    public void setDeleteCallback(SingleCallback<Word> singleCallback) {
        this.singleCallback = singleCallback;
    }

    @Override
    protected void onBindItemData(BaseViewHolder viewHolder, int position, final Word item) {
        viewHolder.setText(R.id.id, String.valueOf(position+1))
                .setText(R.id.word, item.word)
                .setText(R.id.translate, item.translate)
                .setTextColor(R.id.word, item.isComplete ? ContextCompat.getColor(context, R.color.color_333333) : Color.RED)
                .setOnClickListener(R.id.delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (singleCallback != null) {
                            singleCallback.callback(item);
                        }
                    }
                });
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.list_item;
    }

}
