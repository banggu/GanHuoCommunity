package com.scnu.bangzhu.ganhuocommunity.model;

import cn.bmob.v3.BmobUser;

/**
 * Created by bangzhu on 2017/3/14.
 */

public class MyUser extends BmobUser {
    private String userAvatar;

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;

    }
}
