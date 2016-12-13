package com.scnu.bangzhu.ganhuocommunity.module.register;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class RegisterPresenterImpl implements RegisterPresenter, RegisterModel.OnRegisterFinishedListener{
    private RegisterView mView;
    private RegisterModel mModel;

    public RegisterPresenterImpl(RegisterView view) {
        mView = view;
        mModel = new RegisterModelImpl();
    }

    @Override
    public void register(String usernamem, String passwrod, String email) {
        mModel.register(usernamem, passwrod, email, this);
    }

    @Override
    public void onRegisterSuccess() {
        mView.navigateToLogin();
    }

    @Override
    public void onRegisterFailture() {

    }
}
