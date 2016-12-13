package com.scnu.bangzhu.ganhuocommunity.module.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.scnu.bangzhu.ganhuocommunity.R;
import com.scnu.bangzhu.ganhuocommunity.module.main.MainActivity;
import com.scnu.bangzhu.ganhuocommunity.module.register.RegisterActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * Created by chenjianbang on 2016/12/12.
 */
public class LoginActivity extends Activity implements LoginView, View.OnClickListener{
    private TextView mGotoRegister;
    private EditText mUsername, mPassword;
    private Button mLogin;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initBmob();
        initView();
        setListener();
    }

    private void initBmob() {
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("65789f2ad582523a923ae8e17be67be3")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }

    private void initView() {
        mGotoRegister = (TextView) findViewById(R.id.goto_register_textView);
        mUsername = (EditText) findViewById(R.id.username_login_editText);
        mPassword = (EditText) findViewById(R.id.password_login_editText);
        mLogin = (Button) findViewById(R.id.login_button);
        mPresenter = new LoginPresenterImpl(this);
    }

    private void setListener() {
        mGotoRegister.setOnClickListener(this);
        mLogin.setOnClickListener(this);
    }

    private void gotoRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
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
    public void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.goto_register_textView :
                gotoRegister();
                break;

            case R.id.login_button :
                mPresenter.login(mUsername.getText().toString(), mPassword.getText().toString());
                break;
        }
    }
}
