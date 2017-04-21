package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bangzhu on 2017/4/9.
 */

public abstract class LazyLoadFragment extends Fragment {
    /**
     *  控件是否初始化完成
     */
    private boolean isViewCreated;

    /**
     *  数据是否加载完毕
     */
    private boolean isLoadDataCompleted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        initView(view);
        isViewCreated = true;
        return view;
    }

    public abstract int getLayout();
    public abstract void initView(View view);
    public abstract void loadDatas();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            loadData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getUserVisibleHint()) {
            loadData();
        }
    }

    public void loadData() {
        isLoadDataCompleted = true;
        loadDatas();
    }
}
