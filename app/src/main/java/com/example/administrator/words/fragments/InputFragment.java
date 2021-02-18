package com.example.administrator.words.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dpdp.base_moudle.base.BaseFragment;
import com.dpdp.base_moudle.dialog.XPopupUtil;
import com.dpdp.base_moudle.utils.StringUtils;
import com.dpdp.base_moudle.utils.ToastUtil;
import com.example.administrator.words.R;
import com.example.administrator.words.database.WordDataBaseDao;
import com.lxj.xpopup.interfaces.OnConfirmListener;


/**
 * 单词录入 子页面
 */
public class InputFragment extends BaseFragment {

    private EditText editTextWord, editTextTranslate;
    private View inputLl;

    @Override
    protected String getResetTag() {
        return InputFragment.class.getSimpleName();
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_input, container, false);
    }

    @Override
    public CharSequence getPageTitle() {
        return "单词录入";
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextWord = view.findViewById(R.id.input_et_words);
        editTextTranslate = view.findViewById(R.id.input_et_translate);
        inputLl = view.findViewById(R.id.input_ll);
        Button button = view.findViewById(R.id.input_btn);

//        View titleBar = view.findViewById(R.id.title_bar);
//        ViewGroup.LayoutParams layoutParams = titleBar.getLayoutParams();
//        layoutParams.height = StatusBarUtil.getHeight(mContext) + (int) StatusBarUtil.dip2px(50f);
//        titleBar.setLayoutParams(layoutParams);

        //点击录入按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addWordToDataBase();
            }
        });
    }

    private void addWordToDataBase() {
        // 录入单词

        final String word = editTextWord.getText().toString();
        String translate = editTextTranslate.getText().toString();

        if (StringUtils.contentIsNullOrEmpty(word, translate)) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.anmi_shake);
            inputLl.startAnimation(animation);
            ToastUtil.showMsg("输入内容不能为空！");
            return;
        }

        if (word.endsWith("ion")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("ness")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("ment")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("ty")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("acy")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("ance")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("ence")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (word.endsWith("ice")) {
            translate = "n. " + editTextTranslate.getText().toString();
        } else if (translate.endsWith("的")) {
            translate = "adj. " + editTextTranslate.getText().toString();
        } else if (translate.endsWith("地")) {
            translate = "adv. " + editTextTranslate.getText().toString();
        } else {
            translate = editTextTranslate.getText().toString();
        }
        if (word.isEmpty() || editTextTranslate.getText().toString().isEmpty()) {
            ToastUtil.showMsg("单词或翻译不能为空");
        } else {
            final String finalTranslate = translate;
            XPopupUtil.showTipPopup(mContext, "提示", "确定录入单词 " + word + ": " + finalTranslate + "  吗？ 录入之后不可修改！！！", new OnConfirmListener() {
                @Override
                public void onConfirm() {
                    showCustomLoadingDialog();
                    // 录入单词
                    WordDataBaseDao.getInstance().insertWord(word, finalTranslate);
                    editTextTranslate.setText("");
                    editTextWord.setText("");
                    editTextWord.requestFocus();
                    editTextWord.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismissCustomLoadingDialog();
                            ToastUtil.showMsg(mActivity, "录入成功");
                        }
                    }, 1000);
                }
            });

        }
    }
}
