package com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class PCCommunityFragment extends Fragment implements PCCommunityView, SwipeRefreshLayout.OnRefreshListener{
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mArticleListView;
    private List<Article> mArticleList;
    private PCNewsListAdapter mArticleListAdapter;
    private PCCommunityPresenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_pccommunity, container, false);
        initView();
        bindView();
        return mView;
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefresh);
        mArticleListView = (ListView) mView.findViewById(R.id.pc_news_listView);

        mArticleList = new ArrayList<>();
        mArticleListAdapter = new PCNewsListAdapter(getActivity(), mArticleList);
        mPresenter = new PCCommunityPresenterImpl(this);
    }

    private void bindView() {
        mArticleListView.setAdapter(mArticleListAdapter);
        mPresenter.loadArticleList();
    }

    @Override
    public void showDataLoading() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void refreshArticleList(List<Article> list) {
        mArticleList.addAll(list);
        mArticleListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshHotArticleList(List<Article> list) {

    }

    @Override
    public void onRefresh() {
        mPresenter.loadArticleList();

    }
}
