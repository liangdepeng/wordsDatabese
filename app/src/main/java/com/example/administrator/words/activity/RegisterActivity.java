package com.example.administrator.words.activity;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.BaseActivity;
import com.example.administrator.words.R;
import com.example.administrator.words.databinding.ActivityRegisterUserBinding;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-19
 * <p>
 * Summary:
 */
public class RegisterActivity extends BaseActivity {

    private ActivityRegisterUserBinding userBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userBinding = ActivityRegisterUserBinding.inflate(LayoutInflater.from(this));
        setContentView(R.layout.activity_register_user);
        initView();
    }

    private void initView() {

    }

    @Override
    protected String getRetTag() {
        return RegisterActivity.class.getSimpleName();
    }
}
