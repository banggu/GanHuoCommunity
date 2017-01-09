package com.scnu.bangzhu.ganhuocommunity.module.home.coursecommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.Article;
import com.scnu.bangzhu.ganhuocommunity.module.home.ArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.module.home.HotArticleListAdapter;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityPresenter;
import com.scnu.bangzhu.ganhuocommunity.module.home.pccommunity.PCCommunityPresenterImpl;
import com.scnu.bangzhu.ganhuocommunity.module.main.ArticleDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by chenjianbang on 2017/1/9.
 */
public class CourseCommunityFragment extends Fragment implements CourseCommunityView, SwipeRefreshLayout.OnRefreshListener {
    //下拉刷新与加载相关变量
    public static final int STATE_REFRESH = 0;
    public static final int STATE_MORE = 1;
    private int mLimit = 10;
    private int mCurPage = 1;
    private int mLastVisibleItemPosition = 0;
    //视图相关
    private View mView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mArticleListView;
    private List<Article> mArticleList;
    private ArticleListAdapter mArticleListAdapter;
    private CourseCommunityPresenter mPresenter;
    private ListView mHotArticleListView;
    private List<Article> mHotArticleList;
    private HotArticleListAdapter mHotArticleListAdapter;
    private LinearLayout mLoadingData;

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
        mArticleListAdapter = new ArticleListAdapter(getActivity(), mArticleList);
        mPresenter = new CourseCommunityPresenterImpl(this);
    }

    private void bindView() {
        mArticleListView.setAdapter(mArticleListAdapter);
        mPresenter.loadArticleList(mCurPage, mLimit, STATE_REFRESH);
        bindListViewHeader();
        mPresenter.loadHotArticleList();
    }

    private void bindListViewHeader() {
        ViewGroup header = (ViewGroup) getActivity().getLayoutInflater().inflate(R.layout.layout_listview_header, mArticleListView, false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) getResources().getDimension(R.dimen.listview_header_height));
        header.setLayoutParams(params);

        mHotArticleListView = (ListView) header.findViewById(R.id.hotarticlelist_listview);
        mHotArticleList = new ArrayList<>();
        mHotArticleListAdapter = new HotArticleListAdapter(getActivity(), mHotArticleList);
        mHotArticleListView.setAdapter(mHotArticleListAdapter);

        mArticleListView.addHeaderView(header, null, false);
    }

    private void setListener() {
        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article article = mArticleList.get(i-1);
                if(article != null) {
                    bindRelation(article);
                    gotoArticleDetails(article.getContent());
                }
            }
        });

        mHotArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article article = mHotArticleList.get(i);
                if(article != null) {
                    bindRelation(article);
                    gotoArticleDetails(article.getContent());
                }
            }
        });

        mArticleListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滑动到最后一行
                if(firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    //向上滑动
                    if(firstVisibleItem > mLastVisibleItemPosition) {
                        mLoadingData.setVisibility(View.VISIBLE);
                        mPresenter.loadArticleList(mCurPage, mLimit, STATE_MORE);
                    }
                }
                if (firstVisibleItem < mLastVisibleItemPosition) {
                    //向下滑动
                }
                mLastVisibleItemPosition = firstVisibleItem;
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    /////////////////////
    private void bindRelation(Article a) {
        BmobUser user = BmobUser.getCurrentUser();
        final Article article = new Article();
        article.setObjectId(a.getObjectId());
        //将当前用户添加到Post表中的likes字段值中，表明当前用户喜欢该帖子
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(user);
        //多对多关联指向`post`的`likes`字段
        article.setLikes(relation);
        article.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    Log.i("HZWING","多对多关联添加成功");
                    todo(article);
                }else{
                    Log.i("HZWING","失败："+e.getMessage());
                }
            }

        });
    }

    private void todo(Article a) {
        BmobQuery<BmobUser> query = new BmobQuery<BmobUser>();
        final Article article = new Article();
        article.setObjectId(a.getObjectId());
        //likes是Post表中的字段，用来存储所有喜欢该帖子的用户
        query.addWhereRelatedTo("likes", new BmobPointer(article));
        query.findObjects(new FindListener<BmobUser>() {

            @Override
            public void done(List<BmobUser> object,BmobException e) {
                if(e==null){
                    Log.i("bmob","查询个数："+object.size());
                    updateArticle(article, object.size());
                }else{
                    Log.i("bmob","失败："+e.getMessage());
                }
            }

        });
    }

    private void updateArticle(Article a, int num) {
        Article article = new Article();
        article.setLikesCount(num);
        article.update(a.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Log.i("smile", "update success");
            }
        });
    }
    //////////////

    private void gotoArticleDetails(String articleContent) {
        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
        intent.putExtra("articleContent", articleContent);
        startActivity(intent);
    }

    @Override
    public void showNetworkError() {

    }

    @Override
    public void refreshArticleList(int curPage, int actionType, List<Article> list) {
        mLoadingData.setVisibility(View.GONE);
        mCurPage = curPage;
        if(actionType == STATE_REFRESH) {
            mArticleList.clear();
        }
        mArticleList.addAll(list);
        mArticleListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void refreshHotArticleList(List<Article> list) {
        mHotArticleList.clear();
        mHotArticleList.addAll(list);
        mHotArticleListAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        mPresenter.loadArticleList(mCurPage, mLimit, STATE_REFRESH);
        mPresenter.loadHotArticleList();
    }

}
