package com.scnu.bangzhu.ganhuocommunity.module.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity.CommentCommunityFragment;
import com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity.CourseCommunityFragment;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityFragment;
import com.scnu.bangzhu.ganhuocommunity.module.home.schoolnewscommunity.SchoolNewsCommunityFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class HomeFragment extends Fragment {
    private View mView;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<Fragment> mFragmentList;
    private String[] mTitleList;
    private TabFragmentAdapter mPagerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        bindView();
        return mView;
    }

    private void initView() {
        mTabLayout = (TabLayout) mView.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) mView.findViewById(R.id.home_content_viewPager);
    }

    private void bindView() {
        mTitleList = getResources().getStringArray(R.array.home_tab_title);
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new PCCommunityFragment());
        mFragmentList.add(new CourseCommunityFragment());
        mFragmentList.add(new SchoolNewsCommunityFragment());
        mFragmentList.add(new CommentCommunityFragment());

        mPagerAdapter = new TabFragmentAdapter(getChildFragmentManager(), mFragmentList, Arrays.asList(mTitleList));
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setTabsFromPagerAdapter(mPagerAdapter);
    }
}
