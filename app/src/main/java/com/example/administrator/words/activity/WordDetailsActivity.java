package com.example.administrator.words.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.ui.BaseActivity;
import com.dpdp.base_moudle.store.AppConstants;
import com.example.administrator.words.Word;
import com.example.administrator.words.databinding.ActivityWordDetailsLayoutBinding;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-08
 * <p>
 * Summary: todo 单词详情
 */
public class WordDetailsActivity extends BaseActivity {

    /**
     * viewBinding
     */
    private ActivityWordDetailsLayoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWordDetailsLayoutBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            Word word = intent.getParcelableExtra(AppConstants.WORD_ITEM_DETAIL_DATA);
            binding.wordTv.setText(word != null ? word.word : "数据异常~");
            binding.translationTv.setText(word != null ? word.translate : "数据异常~");
        }
    }

}
