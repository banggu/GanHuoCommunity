package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.bangzhu.ganhuocommunity.R;

/**
 * Created by chenjianbang on 2016/12/23.
 */
public class TabFragment extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_tab, container, false);
        return mView;
    }
}
