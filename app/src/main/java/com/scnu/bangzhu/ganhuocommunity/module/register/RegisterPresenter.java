package com.scnu.bangzhu.ganhuocommunity.module.register;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public interface RegisterPresenter {
    void register(String usernamem, String passwrod, String email);

    void registerSuccess();

    void registerFailtrue(int errType);
}
