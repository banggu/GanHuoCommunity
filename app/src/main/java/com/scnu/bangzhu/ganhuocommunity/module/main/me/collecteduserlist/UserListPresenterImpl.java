package com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist;

import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/27.
 */

public class UserListPresenterImpl implements UserListPresenter {
    private UserListView mView;
    private UserListModel mModel;

    public UserListPresenterImpl(UserListView view) {
        mView = view;
        mModel = new UserListModelImpl(this);
    }

    @Override
    public void loadUserList() {
        mModel.loadUserList();
    }

    @Override
    public void loadUserListSuccess(List<MyUserFlag> userList) {
        mView.refreshUserList(userList);
    }

    @Override
    public void loadUserListFailtrue() {

    }

    @Override
    public void updateSubscribe(UserListAdapter adapter) {
        mModel.updateSubscribe(adapter);
    }

    @Override
    public void updateSubscribeSuccess() {
        mView.updateSubscribeSuccess();
    }

    @Override
    public void updateSubscribeFailtrue() {

    }
}
