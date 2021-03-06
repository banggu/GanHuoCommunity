package com.scnu.bangzhu.ganhuocommunity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.scnu.bangzhu.ganhuocommunity.util.ToastUtil;

/**
 * Created by chenjianbang on 2016/12/15.
 */
public class BaseActivity extends FragmentActivity implements BaseView{
    private static final String EXITACTION = "action.exit";

    private ExitReceiver mExitReceiver = new ExitReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EXITACTION);
        registerReceiver(mExitReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mExitReceiver);
    }

    @Override
    public void setPresenter(Object presenter) {

    }

    class ExitReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            BaseActivity.this.finish();
        }
    }

    public void showToast(String content) {
        ToastUtil.showToast(this, content);
    }
}
