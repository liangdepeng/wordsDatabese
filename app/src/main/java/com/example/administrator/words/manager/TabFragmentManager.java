package com.example.administrator.words.manager;

import android.util.SparseArray;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.administrator.words.R;
import com.example.administrator.words.activity.MainActivity;
import com.example.administrator.words.fragments.HomeFragment;
import com.example.administrator.words.fragments.InputFragment;
import com.example.administrator.words.fragments.ReciteFragment;
import com.example.administrator.words.fragments.SelfFragment;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-03
 * <p>
 * Summary: app底部 tabBar 管理类 从activity部分解耦 抽离出来 便于管理 实现复杂的业务逻辑
 */
public class TabFragmentManager implements RadioGroup.OnCheckedChangeListener {

    private final MainActivity mainActivity;
    private final int fragmentId;

    private final HomeFragment mHomeFragment;
    private final InputFragment mInputFragment;
    private final ReciteFragment mReciteFragment;
    private final SelfFragment mSelfFragment;

    private static final int TAB_HOME_WORDS = 0;
    private static final int TAB_INPUT_WORDS = 1;
    private static final int TAB_RECITE_WORDS = 2;
    private static final int TAB_SELF_WORDS = 3;

    private static final int TAB_SIZE = 4;

    private final SparseArray<Fragment> mFragments;
    private final SparseArray<RadioButton> mTabs;

    private Fragment lastFragment;
    private Fragment currentFragment;


    public TabFragmentManager(@NonNull MainActivity mainActivity, int fragmentId) {
        this.mainActivity = mainActivity;
        this.fragmentId = fragmentId;
        mFragments = new SparseArray<>();
        mTabs = new SparseArray<>();

        mHomeFragment = new HomeFragment();
        mInputFragment = new InputFragment();
        mReciteFragment = new ReciteFragment();
        mSelfFragment = new SelfFragment();


        // 用选中的tab id 保存 对应实例 可以避免 if else 判断语句 逻辑清晰
        mFragments.put(R.id.radio_btn_home, mHomeFragment);
        mFragments.put(R.id.radio_btn_input, mInputFragment);
        mFragments.put(R.id.radio_btn_recite, mReciteFragment);
        mFragments.put(R.id.radio_btn_self, mSelfFragment);

        RadioButton radioBtnHome = mainActivity.findViewById(R.id.radio_btn_home);
        RadioButton radioBtnInput = mainActivity.findViewById(R.id.radio_btn_input);
        RadioButton radioBtnRecite = mainActivity.findViewById(R.id.radio_btn_recite);
        RadioButton radioBtnSelf = mainActivity.findViewById(R.id.radio_btn_self);
        mTabs.put(TAB_HOME_WORDS,radioBtnHome);
        mTabs.put(TAB_INPUT_WORDS, radioBtnInput);
        mTabs.put(TAB_RECITE_WORDS, radioBtnRecite);
        mTabs.put(TAB_SELF_WORDS, radioBtnSelf);

        RadioGroup radioGroup = (RadioGroup) mainActivity.findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        shownFragment(group, checkedId);
    }

    private void shownFragment(RadioGroup group, int checkedId) {
        if (mainActivity.isFinishing() || mainActivity.isDestroyed()) {
            return;
        }

        // 开启事务
        FragmentTransaction fragmentTransaction = mainActivity.getSupportFragmentManager().beginTransaction();

        // last 上一个显示的fragment 如果是第一次进来则为空 会去添加
        if (lastFragment == null) {
            currentFragment = mFragments.get(checkedId);
            fragmentTransaction.add(fragmentId, currentFragment);
            currentFragment.setUserVisibleHint(true);
        } else {
            // last 上一个显示的fragment 不为空则隐藏 然后获取当前要显示的
            fragmentTransaction.hide(lastFragment);
            lastFragment.setUserVisibleHint(false);
            currentFragment = mFragments.get(checkedId);
            // 如果 当前fragment 还没有被添加 要添加 已经添加了就直接显示
            if (currentFragment.isAdded()) {
                fragmentTransaction.show(currentFragment);
            } else {
                fragmentTransaction.add(fragmentId, currentFragment);
            }
        }
        // 设置当前显示的fragment 为 下一个的lastfragment
        lastFragment = currentFragment;
        // 提交事务
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 切换 tab
     *
     * @param index ↑↑↑
     * @see #TAB_INPUT_WORDS 输入
     * @see #TAB_RECITE_WORDS 背诵
     * @see #TAB_SELF_WORDS 个人
     */
    public void switchTab(int index) {
        if (index >= 0) {
            mTabs.get(index).setChecked(true);
        }
    }
}
