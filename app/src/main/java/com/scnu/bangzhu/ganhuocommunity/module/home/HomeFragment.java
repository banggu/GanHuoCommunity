package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity.CourseCommunityFragment;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class HomeFragment extends Fragment {
    private View mView;
    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private FragmentPagerAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        bindView();
        return mView;
    }

    private void initView() {
        mViewPager = (ViewPager) mView.findViewById(R.id.home_content_viewPager);
    }

    private void bindView() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new PCCommunityFragment());
        mFragmentList.add(new CourseCommunityFragment());
        mFragmentList.add(new TabFragment());
        mFragmentList.add(new TabFragment());

        mPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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
                String[] titleArr = getResources().getStringArray(R.array.home_tab_title);
                return titleArr[position];
            }
        };
        mViewPager.setAdapter(mPagerAdapter);
    }
}
