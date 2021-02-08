package com.example.administrator.words.activity;

import android.os.Bundle;
import com.dpdp.base_moudle.base.BaseActivity;
import com.example.administrator.words.R;

/**
 * 解释说明
 */
public class ExplainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explain);
        setTitle("软件说明",true);
    }

    @Override
    protected String getRetTag() {
        return ExplainActivity.class.getSimpleName();
    }
}
