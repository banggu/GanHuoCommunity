package com.scnu.bangzhu.ganhuocommunity.module.login;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginFinishedListener{
    private LoginView mView;
    private LoginModel mModel;

    public LoginPresenterImpl(LoginView view) {
        mView = view;
        mModel = new LoginModelImpl();
    }

    @Override
    public void login(String usernamem, String passwrod) {
        mModel.login(usernamem, passwrod, this);
    }

    @Override
    public void onLoginSuccess() {
        mView.navigateToMain();
    }

    @Override
    public void onLoginFailture() {

    }
}
