package com.example.administrator.words.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.ui.BaseActivity;
import com.example.administrator.words.databinding.ActivityLoginInBinding;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary:
 */
public class LoginActivity extends BaseActivity {

    private ActivityLoginInBinding loginInBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginInBinding = ActivityLoginInBinding.inflate(LayoutInflater.from(this));
        setContentView(loginInBinding.getRoot());
        initView();

    }

    private void initView() {

    }

    @Override
    protected String getRetTag() {
        return LoginActivity.class.getSimpleName();
    }

}
