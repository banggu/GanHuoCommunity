package com.scnu.bangzhu.ganhuocommunity.module.splash;

import android.os.Bundle;

import com.scnu.bangzhu.ganhuocommunity.BaseActivity;
import com.scnu.bangzhu.ganhuocommunity.module.login.LoginActivity;

/**
 * Created by bangzhu on 2017/4/7.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginActivity.startMe(this);
        finish();
    }
}
