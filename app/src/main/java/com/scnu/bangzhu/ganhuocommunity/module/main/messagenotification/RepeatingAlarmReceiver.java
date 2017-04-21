package com.scnu.bangzhu.ganhuocommunity.module.main.messagenotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by bangzhu on 2017/3/25.
 */

public class RepeatingAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Log.i("alarm", "receiver alarm");
        Intent intent1 = new Intent(context, NotifyMessageService.class);
        context.startService(intent1);
    }
}
