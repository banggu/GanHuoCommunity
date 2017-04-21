package com.scnu.bangzhu.ganhuocommunity.module.main.me;

import android.app.Activity;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.login.LoginActivity;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist.UserListActivity;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist.UserArticleListActivity;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.usercreatedarticle.UserCreatedArticleActivity;

import cn.bmob.v3.BmobUser;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private RelativeLayout mMyColletedArticle, mMyReadArticle, mMyCollectedUser, mCreatedArticle;
    private TextView mLogout;
    private AlertDialog mAlertDialog;
    private AlertDialog.Builder mBuilder;
    private OnLogoutListener mOnLogoutListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me, container,false);
        initView();
        initEvent();
        return mView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mOnLogoutListener = (OnLogoutListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    private void initView() {
        mMyColletedArticle = (RelativeLayout) mView.findViewById(R.id.my_collect_rl);
        mMyReadArticle = (RelativeLayout) mView.findViewById(R.id.my_read_rl);
        mMyCollectedUser = (RelativeLayout) mView.findViewById(R.id.my_collected_rl);
        mCreatedArticle = (RelativeLayout) mView.findViewById(R.id.my_created_rl);
        mLogout = (TextView) mView.findViewById(R.id.logout_textview);
    }

    private void initEvent() {
        mMyColletedArticle.setOnClickListener(this);
        mMyReadArticle.setOnClickListener(this);
        mMyCollectedUser.setOnClickListener(this);
        mCreatedArticle.setOnClickListener(this);
        mLogout.setOnClickListener(this);
    }

    private void gotoUserArticleListActivity(String actionType) {
        UserArticleListActivity.startMe(getActivity(), actionType);
    }

    private void gotoUserListActivity() {
        UserListActivity.startMe(getActivity());
    }

    private void gotoUserCreatedArticleActivity() {
        UserCreatedArticleActivity.startMe(getActivity());
    }

    private void logout() {
        mAlertDialog = null;
        mBuilder = new AlertDialog.Builder(getActivity());
        mAlertDialog = mBuilder.setMessage("是否退出登录？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
//                        BmobUser.logOut();   //清除缓存用户对象
//                        LoginActivity.startMe(getActivity());
//                        getActivity().finish();
                        Uri uri = Uri.parse("logout");
                        mOnLogoutListener.onLogout(uri);
                    }
                })
                .create();
        mAlertDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_collect_rl:
                gotoUserArticleListActivity("collected");
                break;
            case R.id.my_read_rl:
                gotoUserArticleListActivity("read");
                break;
            case R.id.my_collected_rl:
                gotoUserListActivity();
                break;
            case R.id.my_created_rl:
                gotoUserCreatedArticleActivity();
                break;
            case R.id.logout_textview:
                logout();
                break;
        }
    }

    public interface OnLogoutListener {
        public void onLogout(Uri logoutUri);
    }
}
