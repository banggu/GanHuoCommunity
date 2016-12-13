package com.scnu.bangzhu.ganhuocommunity.module.register;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class RegisterModelImpl implements RegisterModel{
    @Override
    public void register(String username, String password, String email, final OnRegisterFinishedListener listener) {
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                //注册成功
                if(e == null) {
                    listener.onRegisterSuccess();
                } else {
                    listener.onRegisterFailture();
                }
            }
        });
    }
}
