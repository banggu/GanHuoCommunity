package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.scnu.bangzhu.ganhuocommunity.R;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/23.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> mFragmentList;
    private String[] mTitleArr;

    public TabFragmentAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
        mTitleArr = context.getResources().getStringArray(R.array.home_tab_title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitleArr[position];
    }
}
