package com.scnu.bangzhu.ganhuocommunity.module.main.me;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist.UserListActivity;
import com.scnu.bangzhu.ganhuocommunity.module.main.me.userarticlelist.UserArticleListActivity;

/**
 * Created by bangzhu on 2016/12/5.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private RelativeLayout mMyColletedArticle, mMyReadArticle, mMyCollectedUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_me, container,false);
        initView();
        initEvent();
        return mView;
    }

    private void initView() {
        mMyColletedArticle = (RelativeLayout) mView.findViewById(R.id.my_collect_rl);
        mMyReadArticle = (RelativeLayout) mView.findViewById(R.id.my_read_rl);
        mMyCollectedUser = (RelativeLayout) mView.findViewById(R.id.my_collected_rl);
    }

    private void initEvent() {
        mMyColletedArticle.setOnClickListener(this);
        mMyReadArticle.setOnClickListener(this);
        mMyCollectedUser.setOnClickListener(this);
    }

    private void gotoUserArticleListActivity(String actionType) {
        UserArticleListActivity.startMe(getActivity(), actionType);
    }

    private void gotoUserListActivity() {
        UserListActivity.startMe(getActivity());
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
        }
    }

}
