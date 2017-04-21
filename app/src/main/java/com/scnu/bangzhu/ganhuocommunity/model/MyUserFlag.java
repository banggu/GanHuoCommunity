package com.scnu.bangzhu.ganhuocommunity.model;

/**
 * Created by bangzhu on 2017/3/27.
 */

public class MyUserFlag {
    public MyUser user;
    public boolean flag = true;

    public MyUserFlag(MyUser user, boolean flag) {
        this.user = user;
        this.flag = flag;
    }
}
