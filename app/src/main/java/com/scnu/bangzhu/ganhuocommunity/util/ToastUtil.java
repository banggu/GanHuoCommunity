package com.scnu.bangzhu.ganhuocommunity.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by bangzhu on 2017/2/19.
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context, String content) {
        if(toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
