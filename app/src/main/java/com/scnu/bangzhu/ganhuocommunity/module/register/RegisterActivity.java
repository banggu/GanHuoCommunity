package com.scnu.bangzhu.ganhuocommunity.module.register;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.login.LoginActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class RegisterActivity extends BaseActivity implements RegisterView, View.OnClickListener{
    private TextView mGotoLogin;
    private EditText mUsername, mPassword, mEmail;
    private Button mRegister;
    private RegisterPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setListeners();
    }

    private void initView() {
        mGotoLogin = (TextView) findViewById(R.id.goto_login_textView);
        mUsername = (EditText) findViewById(R.id.username_editText);
        mPassword = (EditText) findViewById(R.id.password_editText);
        mEmail = (EditText) findViewById(R.id.email_editText);
        mRegister = (Button) findViewById(R.id.register_button);

        mPresenter = new RegisterPresenterImpl(this);
    }

    private void setListeners() {
        mGotoLogin.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    @Override
    public void showUsernameError() {
        mUsername.setHint(getResources().getString(R.string.user_name_error));
        mUsername.requestFocus();
    }

    @Override
    public void showPasswordError() {
        mPassword.setHint(getResources().getString(R.string.password_error));
        mPassword.requestFocus();
    }

    @Override
    public void showRegisterError(int strId) {
        showToast(getResources().getString(strId));
    }

    @Override
    public void setUsername(String username) {
        mUsername.setText(username);
    }

    @Override
    public void setPassword(String password) {
        mPassword.setText(password);
    }

    @Override
    public void setEmail(String email) {
        mEmail.setText(email);
    }

    @Override
    public void navigateToLogin() {
        showToast("注册成功！");
        LoginActivity.startMe(this);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.goto_login_textView :
                navigateToLogin();
                break;
            case R.id.register_button :
                mPresenter.register(mUsername.getText().toString(), mPassword.getText().toString(), mEmail.getText().toString());
                break;
        }
    }

    public static void startMe(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
