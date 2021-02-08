package com.dpdp.base_moudle.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dpdp.base_moudle.R;


/**
 * Created by ldp.
 * <p>
 * Date: 2021-01-09
 * <p>
 * Summary: Fragment 基类
 */
public class BaseFragment extends Fragment implements IBaseView {

    private final String fragmentTag = getResetTag();

    /**
     * Activity 上下文
     */
    protected Activity mActivity;
    /**
     * Context 上下文
     */
    protected Context mContext;
    private TextView titleTv;
    private View backV;
    private View backRealIv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(fragmentTag, fragmentTag + "  onCreate(@Nullable Bundle savedInstanceState)");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e(fragmentTag, fragmentTag + "  onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        Log.e(fragmentTag, fragmentTag + "  onAttach(Activity activity)");
        this.mActivity = activity;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(fragmentTag, fragmentTag + "  onAttach(Context context)");
        this.mContext = context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        boolean lastUserVisibleHint = getUserVisibleHint();
        if (lastUserVisibleHint != isVisibleToUser) {

        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e(fragmentTag, fragmentTag + " key:  onHiddenChanged    value: " +hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(fragmentTag, fragmentTag + "  onActivityCreated(@Nullable Bundle savedInstanceState)");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(fragmentTag, fragmentTag + "  onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(fragmentTag, fragmentTag + "  onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(fragmentTag, fragmentTag + "  onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(fragmentTag, fragmentTag + "  onStop()");
    }

    @Override
    public void onDestroyView() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissDialog();
        }
        super.onDestroyView();
        Log.e(fragmentTag, fragmentTag + "  onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(fragmentTag, fragmentTag + "  onDestroy()");
    }

    @Override
    public void onDetach() {
        Log.e(fragmentTag, fragmentTag + "   onDetach()");
        super.onDetach();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(fragmentTag, fragmentTag + "  onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)");
        initTitleBar(view);
    }

    private void initTitleBar(View view) {
        if (view.findViewById(R.id.title_bar) == null)
            return;
        titleTv = view.findViewById(R.id.title_tv);
        backV = view.findViewById(R.id.back_v);
        backRealIv = view.findViewById(R.id.back_real_v);
        setTitle(getPageTitle());
    }

    public void setTitle(CharSequence title) {
        if (titleTv != null) {
            titleTv.setText(title);
        }
    }


    @Override
    public void showLoadingDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showLoadingDialog();
        }
    }

    @Override
    public void dismissDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissDialog();
        }
    }

    @Override
    public void showCustomLoadingDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).showCustomLoadingDialog();
        }
    }

    @Override
    public void dismissCustomLoadingDialog() {
        if (mActivity instanceof BaseActivity) {
            ((BaseActivity) mActivity).dismissCustomLoadingDialog();
        }
    }

    @Override
    public CharSequence getPageTitle() {
        return "";
    }


    protected String getResetTag() {
        return BaseFragment.class.getSimpleName();
    }
}
