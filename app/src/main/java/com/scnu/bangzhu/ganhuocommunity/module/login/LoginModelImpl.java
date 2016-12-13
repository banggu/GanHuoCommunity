package com.scnu.bangzhu.ganhuocommunity.module.login;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class LoginModelImpl implements LoginModel{
    @Override
    public void login(String username, String password, final OnLoginFinishedListener listener) {
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    listener.onLoginSuccess();
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    listener.onLoginFailture();
                }
            }
        });
    }
}
