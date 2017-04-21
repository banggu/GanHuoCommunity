package com.scnu.bangzhu.ganhuocommunity.module.register;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public interface RegisterView {
    void showUsernameError();
    void showPasswordError();
    void showRegisterError(int strId);
    void setUsername(String username);
    void setPassword(String password);
    void setEmail(String email);
    void navigateToLogin();
}
