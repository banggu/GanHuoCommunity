package com.scnu.bangzhu.ganhuocommunity.module.register;

import com.scnu.bangzhu.ganhuocommunity.R;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class RegisterPresenterImpl implements RegisterPresenter {
    public static final int USER_NAME_ERROR = 4001;
    public static final int PASSWORD_ERROR = 4002;
    public static final int REGISTER_ERROR = 4003;

    private RegisterView mView;
    private RegisterModel mModel;

    public RegisterPresenterImpl(RegisterView view) {
        mView = view;
        mModel = new RegisterModelImpl(this);
    }

    @Override
    public void register(String usernamem, String passwrod, String email) {
        mModel.register(usernamem, passwrod, email);
    }

    @Override
    public void registerSuccess() {
        mView.navigateToLogin();
    }

    @Override
    public void registerFailtrue(int errType) {
        switch (errType) {
            case USER_NAME_ERROR :
                mView.showUsernameError();
                break;
            case PASSWORD_ERROR:
                mView.showPasswordError();
                break;
            default:
                mView.showRegisterError(R.string.register_error);
                break;
        }
    }
}
