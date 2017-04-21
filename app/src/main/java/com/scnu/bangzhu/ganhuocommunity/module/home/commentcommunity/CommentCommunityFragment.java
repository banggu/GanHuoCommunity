package com.scnu.bangzhu.ganhuocommunity.module.home.commentcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.model.PageModel;
import com.scnu.bangzhu.ganhuocommunity.module.main.articledetail.ArticleDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenjianbang on 2016/12/26.
 */
public class CommentCommunityFragment extends Fragment implements CommentCommunityView, SwipeRefreshLayout.OnRefreshListener {
    //下拉刷新与加载相关变量
    public static final int STATE_REFRESH = 0;
    public static final int STATE_MORE = 1;
    private  int mPageNum = 0;
    private int mLimit = 10;
    private int mCurPage = 1;
    private int mLastVisibleItemPosition = 0;
    //视图相关
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mArticleListView;
    private List<Article> mArticleList;
    private CommentListAdapter mArticleListAdapter;
    private CommentCommunityPresenter mPresenter;

    private LinearLayout mLoadingData;
    private PageModel mPageModel = new PageModel(0, 0, 10, 0);
    private boolean isBottom = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_pccommunity, container, false);
        initView();
        bindView();
        setListener();
        return mView;
    }

    private void initView() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.swiperefresh);
        mArticleListView = (ListView) mView.findViewById(R.id.pc_news_listView);
        mLoadingData = (LinearLayout) mView.findViewById(R.id.loading_data_linearlayout);

        mArticleList = new ArrayList<>();
        mArticleListAdapter = new CommentListAdapter(getActivity(), mArticleList);
        mPresenter = new CommentCommunityPresenterImpl(this);
    }

    private void bindView() {
        mArticleListView.setAdapter(mArticleListAdapter);
        mPresenter.queryTotalPageNum(mPageModel);
        mPresenter.loadArticleList(mPageModel);
    }

    private void setListener() {
        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article article = mArticleList.get(i);
                if(article != null) {
                    gotoArticleDetails(article);
                }
            }
        });

        mArticleListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (i == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (isBottom) {
                        // 下载更多数据
                        mLoadingData.setVisibility(View.VISIBLE);
                        if(mPageModel.curPage < mPageModel.pageNum - 1) {
                            mPageModel.actionType = 1;
                            mPresenter.loadArticleList(mPageModel);
                        } else {//若为最后一页，则不加载数据
                            mLoadingData.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滑动到最后一行
//                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
//                    //向上滑动
//                    if(firstVisibleItem > mLastVisibleItemPosition) {
//                        mLoadingData.setVisibility(View.VISIBLE);
//                        if(mCurPage < mPageNum) {
//                            mPresenter.loadArticleList(mCurPage, mLimit, STATE_MORE);
//                        } else {
//                            mLoadingData.setVisibility(View.GONE);
//                        }
//                    }
//                }
//                if (firstVisibleItem < mLastVisibleItemPosition) {
//                  //向下滑动
//                }
//                mLastVisibleItemPosition = firstVisibleItem;

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    isBottom = true;
                }else{
                    isBottom = false;
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    private void gotoArticleDetails(Article article) {
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("article", article);
        startActivity(intent);
    }

    @Override
    public void showDataLoading() {

    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void setPageNum(int pageNum) {
        mPageNum = pageNum;
    }

    @Override
    public void refreshArticleList(List<Article> list) {
        mLoadingData.setVisibility(View.GONE);
        if(mPageModel.actionType == STATE_REFRESH) {
            mArticleList.clear();
        }
        mArticleList.addAll(list);
        if (mPageModel.actionType == STATE_MORE) {
            mArticleListView.setSelection(mArticleList.size()-1);
        }
        mArticleListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPageModel.actionType = STATE_REFRESH;
        mPresenter.loadArticleList(mPageModel);
    }
}
