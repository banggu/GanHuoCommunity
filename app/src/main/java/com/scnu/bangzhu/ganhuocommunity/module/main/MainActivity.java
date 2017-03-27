package com.scnu.bangzhu.ganhuocommunity.module.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.addarticle.AddArticleActivity;
import com.scnu.bangzhu.ganhuocommunity.module.home.HomeFragment;
import com.scnu.bangzhu.ganhuocommunity.module.main.hotnews.HotArticleFragment;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.MeFragment;
import com.scnu.bangzhu.ganhuocommunity.module.main.messagenotification.MessageFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private FloatingActionButton mAddArticle;
    private ImageView mHomeIcon, mHotNewsIcon, mMsgIcon, mMeIcon;
    private HomeFragment mHomeFragment;
    private HotArticleFragment mHotArticleFragment;
    private MeFragment mMeFragememt;
    private MessageFragment mMsgFragment;
    private long mExitTime;
    private int mCurPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setFragmentSelect(mCurPage);
    }

    private void initView() {
        mAddArticle = (FloatingActionButton) findViewById(R.id.write_article_fab);
        mHomeIcon = (ImageView) findViewById(R.id.home_imageView);
        mHotNewsIcon = (ImageView) findViewById(R.id.hot_imageView);
        mMsgIcon = (ImageView) findViewById(R.id.bell_imageView);
        mMeIcon = (ImageView) findViewById(R.id.me_imageView);
    }

    private void setListener() {
        mAddArticle.setOnClickListener(this);
        mHomeIcon.setOnClickListener(this);
        mHotNewsIcon.setOnClickListener(this);
        mMsgIcon.setOnClickListener(this);
        mMeIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        resetIcons();
       switch(view.getId()) {
           case R.id.write_article_fab:
               gotoAddArticle();
               break;
           case R.id.home_imageView :
               setFragmentSelect(0);
               break;
           case R.id.hot_imageView :
               setFragmentSelect(1);
               break;
           case R.id.bell_imageView :
               setFragmentSelect(2);
               break;
           case R.id.me_imageView :
               setFragmentSelect(3);
               break;
       }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            if(mCurPage == 0) {
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    Toast.makeText(getApplicationContext(), "再按一次退出程序",
                            Toast.LENGTH_SHORT).show();
                    mExitTime = System.currentTimeMillis();
                } else {
                    System.exit(0);
                }
            } else {
                resetIcons();
                setFragmentSelect(0);
            }
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void gotoAddArticle() {
        Intent intent = new Intent(this, AddArticleActivity.class);
        startActivity(intent);
    }

    private void setFragmentSelect(int index) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTrasaction = fm.beginTransaction();
        hideFragment(fragmentTrasaction);
        mCurPage = index;

        switch(index) {
            case 0 :
                if(mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    fragmentTrasaction.add(R.id.content_frameLayout, mHomeFragment);
                } else {
                    fragmentTrasaction.show(mHomeFragment);
                }
                mHomeIcon.setImageResource(R.drawable.ic_home_pressed);
                break;
            case 1 :
                if(mHotArticleFragment == null) {
                    mHotArticleFragment = new HotArticleFragment();
                    fragmentTrasaction.add(R.id.content_frameLayout, mHotArticleFragment);
                } else {
                    fragmentTrasaction.show(mHotArticleFragment);
                }
                mHotNewsIcon.setImageResource(R.drawable.ic_hotnews_pressed);
                break;
            case 2 :
                if(mMsgFragment == null) {
                    mMsgFragment = new MessageFragment();
                    fragmentTrasaction.add(R.id.content_frameLayout, mMsgFragment);
                } else {
                    fragmentTrasaction.show(mMsgFragment);
                }
                mMsgIcon.setImageResource(R.drawable.ic_bell_pressed);
                break;
            case 3 :
                if(mMeFragememt == null) {
                    mMeFragememt = new MeFragment();
                    fragmentTrasaction.add(R.id.content_frameLayout, mMeFragememt);
                } else {
                    fragmentTrasaction.show(mMeFragememt);
                }
                mMeIcon.setImageResource(R.drawable.ic_me_pressed);
                break;
        }
        fragmentTrasaction.commit();
    }

    private void hideFragment(FragmentTransaction ft) {
        if(mHomeFragment != null) {
            ft.hide(mHomeFragment);
        }
        if(mHotArticleFragment != null) {
            ft.hide(mHotArticleFragment);
        }
        if(mMsgFragment != null) {
            ft.hide(mMsgFragment);
        }
        if(mMeFragememt != null) {
            ft.hide(mMeFragememt);
        }
    }

    private void resetIcons() {
        mHomeIcon.setImageResource(R.drawable.ic_home);
        mHotNewsIcon.setImageResource(R.drawable.ic_hotnews);
        mMsgIcon.setImageResource(R.drawable.ic_bell);
        mMeIcon.setImageResource(R.drawable.ic_me);
    }
}
