package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.bangzhu.ganhuocommunity.R;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class HotNewsFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_hot_module, container,false);
        return mView;
    }
}
