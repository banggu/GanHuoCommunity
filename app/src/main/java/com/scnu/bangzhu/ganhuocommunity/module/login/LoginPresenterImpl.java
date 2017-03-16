package com.scnu.bangzhu.ganhuocommunity.module.login;

import com.scnu.bangzhu.ganhuocommunity.R;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class LoginPresenterImpl implements LoginPresenter {
    public static final int USER_NAME_ERR = 3001;
    public static final int PASSWORD_ERR = 3002;
    public static final int LOGIN_ERR = 3003;

    private LoginView mView;
    private LoginModel mModel;

    public LoginPresenterImpl(LoginView view) {
        mView = view;
        mModel = new LoginModelImpl(this);
    }

    @Override
    public void login(String usernamem, String passwrod) {
        mModel.login(usernamem, passwrod);
    }

    @Override
    public void loginSuccess() {
        mView.navigateToMain();
    }

    @Override
    public void loginFailtrue(int errType) {
        switch (errType) {
            case USER_NAME_ERR:
                mView.showUsernameError();
                break;
            case PASSWORD_ERR:
                mView.showPasswordError();
                break;
            default:
                mView.showLoginError(R.string.login_error);
                break;
        }
    }
}
