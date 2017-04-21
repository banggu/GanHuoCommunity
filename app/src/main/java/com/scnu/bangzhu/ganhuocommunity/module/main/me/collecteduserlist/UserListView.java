package com.scnu.bangzhu.ganhuocommunity.module.main.me.collecteduserlist;

import com.scnu.bangzhu.ganhuocommunity.model.MyUser;
import com.scnu.bangzhu.ganhuocommunity.model.MyUserFlag;

import java.util.List;

/**
 * Created by bangzhu on 2017/3/27.
 */

public interface UserListView {
    void refreshUserList(List<MyUserFlag> userList);


    void updateSubscribeSuccess();
}
