package com.scnu.bangzhu.ganhuocommunity.module.main.hotnews;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.module.home.ArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.widget.CustomListView;
import com.scnu.bangzhu.ganhuocommunity.widget.CustomSwipeRefreshLayout;
import com.scnu.bangzhu.ganhuocommunity.widget.CustomViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class HotArticleFragment extends Fragment implements HotArticleView, SwipeRefreshLayout.OnRefreshListener {
    private View mView;
    private SwipeRefreshLayout mRefreshLayout;
    private LinearLayout mDotGroup;
    private ListView mHotArticle;
    private List<Article> mHotArticleList;
    private List<Article> mArticleList;
    private ArticleListAdapter mArticleListAdapter;
    private MyFragmentPagerAdapter mViewPagerAdapter;
    private LinearLayout mLoadingData;
    private ViewPager mViewPager;
    private HotArticlePresenter mPresenter;
    //ViewPager当前位置
    private int mViewPagerCurItem = 0;
    //用于实现轮播自动切换
    private ScheduledExecutorService mScheduledExecutorService;
    private ScheduledFuture<?> mBeeperHandle;
    //最小滑动距离
    private int mTouchSlop;
    //上一次的位置
    private int mLastX;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_hot_module, container, false);
        initView();
        bindView();
        initEvent();
        setContent();
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
        startAutoScroll();
    }

    private void initView() {
//        mHeader = LayoutInflater.from(getActivity()).inflate(R.layout.layout_listview_header_viewpager, null);
        mViewPager = (ViewPager) mView.findViewById(R.id.hot_article_viewpager);
        mRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.hot_news_swiperefresh);
        mDotGroup = (LinearLayout) mView.findViewById(R.id.ll_dotgroup);
        mHotArticle = (ListView) mView.findViewById(R.id.hot_article_lisetview);
        mLoadingData = (LinearLayout) mView.findViewById(R.id.loading_data_ll);
//        mHotArticle.addHeaderView(mHeader);

        mPresenter = new HotArticlePresenterImpl(this);
        mTouchSlop = ViewConfiguration.get(getActivity()).getScaledTouchSlop();
    }

    private void bindView() {
        mHotArticleList = new ArrayList<>();
        mViewPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), mHotArticleList);

        mArticleList = new ArrayList<>();
        mArticleListAdapter = new ArticleListAdapter(getActivity(), mArticleList);

        mViewPager.setAdapter(mViewPagerAdapter);
        mHotArticle.setAdapter(mArticleListAdapter);
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(this);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mLastX = (int) motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        int x = (int) motionEvent.getX();
                        int deltaX = x - mLastX;
                        if(Math.abs(deltaX) > mTouchSlop) {
                            mBeeperHandle.cancel(true);
                            startAutoScroll();
                        }
                        break;
                }
                return false;
            }
        });
    }

    private void setContent() {
        mPresenter.loadHotArticle();
        mPresenter.loadArticle();
        initDotGroup();
    }

    //初始化点列表视图
    private void initDotGroup(){
        for(int i=0;i<4;i++){
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(0, 0, 20, 0);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.point);
            mDotGroup.addView(imageView);
        }
        ((ImageView)mDotGroup.getChildAt(mViewPagerCurItem)).setImageResource(R.drawable.point_selected);
    }

    //开启轮播自动滚动
    private void startAutoScroll(){
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
//        mScheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);
        mBeeperHandle =
                mScheduledExecutorService.scheduleWithFixedDelay(new ViewPagerTask(), 5, 5, TimeUnit.SECONDS);

    }

    @Override
    public void hideHeader() {

    }

    @Override
    public void refreshHotArticle(List<Article> list) {
        if(list == null || list.size() ==0) {
            return;
        }
        mHotArticleList.clear();
        mHotArticleList.addAll(list);
        mViewPagerAdapter.notifyDataSetChanged();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshArticle(List<Article> list) {
        if(list == null || list.size() == 0) {
            mViewPager.setVisibility(View.GONE);
            return;
        }
        mArticleList.clear();
        mArticleList.addAll(list);
        mArticleListAdapter.notifyDataSetInvalidated();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadHotArticle();
        mPresenter.loadArticle();
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(final int position) {
            ImageView iv1 = (ImageView) mDotGroup.getChildAt(position);
            ImageView iv2 = (ImageView) mDotGroup.getChildAt(mViewPagerCurItem);
            if(iv1 != null){
                iv1.setImageResource(R.drawable.point_selected);
            }
            if(iv2 != null){
                iv2.setImageResource(R.drawable.point);
            }
            mViewPagerCurItem = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class ViewPagerTask implements Runnable {

        @Override
        public void run() {
            mViewPagerCurItem = (mViewPagerCurItem+1)%4;
            Message msg = new Message();
            msg.what = 123;
            mHandler.sendMessage(msg);
        }
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 123:
                    mViewPager.setCurrentItem(mViewPagerCurItem);
                    ((ImageView)mDotGroup.getChildAt(mViewPagerCurItem)).setImageResource(R.drawable.point_selected);
                    int prev = mViewPagerCurItem -1;
                    if(prev<0)
                        prev = 3;
                    ((ImageView)mDotGroup.getChildAt(prev)).setImageResource(R.drawable.point);
                    break;
            }
        }
    };
}
