package com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.ArticleFlag;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist.UserListActivity;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist.UserListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangzhu on 2017/4/1.
 */

public class UserCreatedArticleActivity extends BaseActivity implements UserCreatedArticleView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private TextView mTopTitle, mEdit, mFinishEdit, mCancleEdit;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mArticleListView;
    private LinearLayout mCanclePanel;
    private List<ArticleFlag> mArticleFlagList;
    private ArticleListEditableAdapter mAdapter;
    private UserCreatedArticlePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        initView();
        bindView();
        initEvent();
        setContent();
    }

    private void initView() {
        mTopTitle = (TextView) findViewById(R.id.edit_header_author);
        mEdit = (TextView) findViewById(R.id.edit_textview);
        mFinishEdit = (TextView) findViewById(R.id.finish_edit_textview);
        mCancleEdit = (TextView) findViewById(R.id.cancle_edit_textview);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.user_list_swiperefresh);
        mArticleListView = (ListView) findViewById(R.id.user_list_listView);
        mCanclePanel = (LinearLayout) findViewById(R.id.cancle_panel);

        mPresenter = new UserCreatedArticlePresenterImpl(this);
    }

    private void bindView() {
        mArticleFlagList = new ArrayList<>();
        mAdapter = new ArticleListEditableAdapter(this, mArticleFlagList, false);
        mArticleListView.setAdapter(mAdapter);
    }

    private void initEvent() {
        mRefreshLayout.setOnRefreshListener(this);
        mEdit.setOnClickListener(this);
        mFinishEdit.setOnClickListener(this);
        mCancleEdit.setOnClickListener(this);
    }

    private void setContent() {
        mTopTitle.setText("我发表的文章");
        mPresenter.loadArticleList();
    }

    private void editArticleList() {
        mEdit.setVisibility(View.GONE);
        mFinishEdit.setVisibility(View.VISIBLE);
        mCanclePanel.setVisibility(View.VISIBLE);
        mAdapter = new ArticleListEditableAdapter(this, mArticleFlagList, true);
        mArticleListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetInvalidated();
    }

    private void cancleEditArticleList() {
        mEdit.setVisibility(View.VISIBLE);
        mFinishEdit.setVisibility(View.GONE);
        mCanclePanel.setVisibility(View.GONE);
        mAdapter = new ArticleListEditableAdapter(this, mArticleFlagList, false);
        mArticleListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void refreshUserList(List<ArticleFlag> articleFlagList) {
        mArticleFlagList.clear();
        mArticleFlagList.addAll(articleFlagList);
        mAdapter.notifyDataSetInvalidated();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateSubscribeSuccess() {
        mEdit.setVisibility(View.VISIBLE);
        mFinishEdit.setVisibility(View.GONE);
        mCanclePanel.setVisibility(View.GONE);
        mPresenter.loadArticleList();
        mAdapter = new ArticleListEditableAdapter(this, mArticleFlagList, false);
        mArticleListView.setAdapter(mAdapter);
        mAdapter.notifyDataSetInvalidated();
    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, UserCreatedArticleActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_textview:
                editArticleList();
                break;
            case R.id.finish_edit_textview:
                mPresenter.updateSubscribe(mAdapter);
                break;
            case R.id.cancle_edit_textview:
                cancleEditArticleList();
                break;
        }
    }
}
