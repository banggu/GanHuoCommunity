package com.scnu.bangzhu.ganhuocommunity.module.login;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public interface LoginView {
    void showUsernameError();
    void showPasswordError();
    void showLoginError(int contentId);
    void setUsername(String username);
    void setPassword(String password);
    void navigateToMain();
}
