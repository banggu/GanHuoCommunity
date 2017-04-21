package com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist;

import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/27.
 */

public interface UserListPresenter {
    void loadUserList();

    void loadUserListSuccess(List<MyUserFlag> userList);

    void loadUserListFailtrue();

    void updateSubscribe(UserListAdapter adapter);

    void updateSubscribeSuccess();

    void updateSubscribeFailtrue();
}
