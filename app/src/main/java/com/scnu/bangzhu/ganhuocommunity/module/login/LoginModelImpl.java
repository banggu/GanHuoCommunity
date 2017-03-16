package com.scnu.bangzhu.ganhuocommunity.module.login;

import android.text.TextUtils;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class LoginModelImpl implements LoginModel{
    private LoginPresenter mPresenter;

    public LoginModelImpl(LoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void login(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            mPresenter.loginFailtrue(LoginPresenterImpl.USER_NAME_ERR);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mPresenter.loginFailtrue(LoginPresenterImpl.PASSWORD_ERR);
            return;
        }
        BmobUser user = new BmobUser();
        user.setUsername(username);
        user.setPassword(password);
        user.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if(e==null){
                    mPresenter.loginSuccess();
                    //通过BmobUser user = BmobUser.getCurrentUser()获取登录成功后的本地用户信息
                    //如果是自定义用户对象MyUser，可通过MyUser user = BmobUser.getCurrentUser(MyUser.class)获取自定义用户信息
                }else{
                    mPresenter.loginFailtrue(LoginPresenterImpl.LOGIN_ERR);
                }
            }
        });
    }
}
