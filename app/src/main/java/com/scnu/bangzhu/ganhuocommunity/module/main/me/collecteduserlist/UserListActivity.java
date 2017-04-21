package com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bangzhu on 2017/3/27.
 */

public class UserListActivity extends BaseActivity implements UserListView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private ImageView mBack;
    private TextView mEditTitle, mEditList, mFinishEditList;
    private SwipeRefreshLayout mRefreshLayout;
    private ListView mUserListView;
    private LinearLayout mCanclePanel;
    private TextView mCancleEdit;

    private List<MyUserFlag> mUserList;
    private UserListAdapter mUserListAdapter;
    private UserListPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        init();
        initView();
        bindView();
        setContent();
        setListener();
    }

    private void init() {
        mPresenter = new UserListPresenterImpl(this);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.edit_header_back);
        mEditTitle = (TextView) findViewById(R.id.edit_header_author);
        mEditList = (TextView) findViewById(R.id.edit_textview);
        mFinishEditList = (TextView) findViewById(R.id.finish_edit_textview);
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.user_list_swiperefresh);
        mUserListView = (ListView) findViewById(R.id.user_list_listView);
        mCanclePanel = (LinearLayout) findViewById(R.id.cancle_panel);
        mCancleEdit = (TextView) findViewById(R.id.cancle_edit_textview);
    }

    private void bindView() {
        mUserList = new ArrayList<>();
        mUserListAdapter = new UserListAdapter(this, mUserList, false);
        mUserListView.setAdapter(mUserListAdapter);
    }

    private void setContent() {
        mEditTitle.setText(getResources().getString(R.string.my_follow));
        mPresenter.loadUserList();
    }

    private void setListener() {
        mRefreshLayout.setOnRefreshListener(this);
        mEditList.setOnClickListener(this);
        mEditList.setOnClickListener(this);
        mCancleEdit.setOnClickListener(this);
        mFinishEditList.setOnClickListener(this);
    }

    private void editUserList() {
        mEditList.setVisibility(View.GONE);
        mFinishEditList.setVisibility(View.VISIBLE);
        mCanclePanel.setVisibility(View.VISIBLE);
        mUserListAdapter = new UserListAdapter(this, mUserList, true);
        mUserListView.setAdapter(mUserListAdapter);
        mUserListAdapter.notifyDataSetInvalidated();
    }

    private void cancleEditUserList() {
        mEditList.setVisibility(View.VISIBLE);
        mFinishEditList.setVisibility(View.GONE);
        mCanclePanel.setVisibility(View.GONE);
        mUserListAdapter = new UserListAdapter(this, mUserList, false);
        mUserListView.setAdapter(mUserListAdapter);
        mUserListAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void refreshUserList(List<MyUserFlag> userList) {
        mUserList.clear();
        mUserList.addAll(userList);
        mUserListAdapter.notifyDataSetInvalidated();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateSubscribeSuccess() {
        mEditList.setVisibility(View.VISIBLE);
        mFinishEditList.setVisibility(View.GONE);
        mCanclePanel.setVisibility(View.GONE);
        mPresenter.loadUserList();
        mUserListAdapter = new UserListAdapter(this, mUserList, false);
        mUserListView.setAdapter(mUserListAdapter);
        mUserListAdapter.notifyDataSetInvalidated();
    }

    @Override
    public void onRefresh() {

    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, UserListActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_edit_textview:
                mPresenter.updateSubscribe(mUserListAdapter);
                break;
            case R.id.edit_textview:
                editUserList();
                break;
            case R.id.cancle_edit_textview:
                cancleEditUserList();
                break;
        }
    }


}
