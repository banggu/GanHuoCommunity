package com.scnu.bangzhu.ganhuocommunity.module.register;

import android.text.TextUtils;

import com.scnu.bangzhu.ganhuocommunity.config.Contants;
import com.scnu.bangzhu.ganhuocommunity.model.MyUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class RegisterModelImpl implements RegisterModel{
    private RegisterPresenter mPresenter;

    public RegisterModelImpl(RegisterPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void register(String username, String password, String email) {
        if(TextUtils.isEmpty(username)) {
            mPresenter.registerFailtrue(RegisterPresenterImpl.USER_NAME_ERROR);
            return;
        }

        if(TextUtils.isEmpty(password)) {
            mPresenter.registerFailtrue(RegisterPresenterImpl.PASSWORD_ERROR);
            return;
        }

        if(TextUtils.isEmpty(email)) {
            email = "";
        }

//        BmobUser user = new BmobUser();
        MyUser user = new MyUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setUserAvatar(Contants.USER_AVATAR);
        user.signUp(new SaveListener<BmobUser>() {
            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                //注册成功
                if(e == null) {
                    mPresenter.registerSuccess();
                } else {
                    mPresenter.registerFailtrue(RegisterPresenterImpl.REGISTER_ERROR);
                }
            }
        });
    }
}
