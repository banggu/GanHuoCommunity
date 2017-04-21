package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/8.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Article> mArticleList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Article> list) {
        super(fm);
        mArticleList = list;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", mArticleList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mArticleList.size();
    }
}
