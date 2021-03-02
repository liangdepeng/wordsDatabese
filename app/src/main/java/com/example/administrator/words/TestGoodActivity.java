package com.example.administrator.words;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.ui.BaseActivity;
import com.example.administrator.words.databinding.ActivityGoodTestLayoutBinding;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-23
 * <p>
 * Summary:
 * <p>
 */
public class TestGoodActivity extends BaseActivity {

    private ActivityGoodTestLayoutBinding layoutBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layoutBinding = ActivityGoodTestLayoutBinding.inflate(LayoutInflater.from(this));
        setContentView(layoutBinding.getRoot());

        layoutBinding.praise01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutBinding.likeBtn01.addLikeView();
            }
        });

        layoutBinding.praise02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutBinding.likeBtn02.addLikeView();
            }
        });
    }
}
