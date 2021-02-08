package com.example.administrator.words.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dpdp.base_moudle.base.BaseFragment;
import com.example.administrator.words.adapter.HomeAdapter;
import com.example.administrator.words.databinding.FragmentHomeBinding;

/**
 * Created by ldp.
 * <p>
 * Date: 2021-02-06
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding homeBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return homeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeBinding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        // homeBinding.recyclerView.setItemAnimator();
        homeBinding.recyclerView.setAdapter(new HomeAdapter(mContext));
    }


    @Override
    protected String getResetTag() {
        return HomeFragment.class.getSimpleName();
    }
}
