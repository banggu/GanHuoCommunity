package com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;
import com.scnu.bangzhu.ganhuocommunity.module.home.ArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.module.main.articledetail.ArticleDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangzhu on 2017/3/26.
 */

public class UserArticleListActivity extends BaseActivity implements UserArticleListView, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mArticleList;
    private List<Article> mUserArticleList;
    private ArticleListAdapter mAdapter;
    private String mActionType;
    private PageModel mPageModel = new PageModel(0, 0, 10, 0);
    private UserArticleListPresenter mPresenter;
    //ListView 中最后一个可见的列表项下标
    private int mLastVisibleItemPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        init();
        initView();
        bindView();
        initEvent();
        setContent();
    }

    private void init() {
        Intent intent = getIntent();
        mActionType = intent.getStringExtra("actionType");
        mPresenter = new UserArticleListPresenterImpl(this);
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.article_list_swiperefresh);
        mArticleList = (ListView) findViewById(R.id.article_list_listView);
    }

    private void bindView() {
        mUserArticleList = new ArrayList<>();
        mAdapter = new ArticleListAdapter(this, mUserArticleList);
        mArticleList.setAdapter(mAdapter);
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(this);
        mArticleList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滑动到最后一行
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    //向上滑动
                    if(firstVisibleItem > mLastVisibleItemPosition) {

                        if(mPageModel.curPage < mPageModel.pageNum - 1) {
                            mPageModel.actionType = 1;
                            mPresenter.loadArticleList(mActionType, mPageModel);
                        } else {//若为最后一页，则不加载数据

                        }
                    }
                }
                if (firstVisibleItem < mLastVisibleItemPosition) {
                    //向下滑动
                }
                mLastVisibleItemPosition = firstVisibleItem;
            }
        });

        mArticleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ArticleDetailsActivity.startMe(UserArticleListActivity.this, mUserArticleList.get(i));
            }
        });
    }

    private void setContent() {
        mPresenter.getPageNum(mActionType, mPageModel);
        mPresenter.loadArticleList(mActionType, mPageModel);
    }

    @Override
    public void onRefresh() {
        mPageModel.actionType = 0;
        mPresenter.loadArticleList(mActionType, mPageModel);
    }

    @Override
    public void refreshArticle(List<Article> list) {
        if(mPageModel.actionType == 0) {
            mUserArticleList.clear();
        }
        mUserArticleList.addAll(list);
        if (mPageModel.actionType == 1) {
            mArticleList.setSelection(mUserArticleList.size()-1);
        }
        mAdapter.notifyDataSetInvalidated();
        mRefreshLayout.setRefreshing(false);
    }

    public static void startMe(Context context, String actionType) {
        Intent intent = new Intent(context, UserArticleListActivity.class);
        intent.putExtra("actionType", actionType);
        context.startActivity(intent);
    }
}
