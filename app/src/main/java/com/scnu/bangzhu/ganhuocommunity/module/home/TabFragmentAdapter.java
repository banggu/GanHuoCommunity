package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.R;

import java.util.List;

/**
 * Created by chenjianbang on 2016/12/23.
 */
public class TabFragmentAdapter extends FragmentStatePagerAdapter{
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    public TabFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentList = fragmentList;
        mTitleList = titleList;
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
        return mTitleList.get(position);
    }
}
