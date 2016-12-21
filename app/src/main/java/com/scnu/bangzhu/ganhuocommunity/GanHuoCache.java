package com.scnu.bangzhu.ganhuocommunity;

import android.content.Context;

import java.io.Serializable;

/**
 *  全局缓存类，缓存账号和 Context
 */
public class GanHuoCache implements Serializable{
    private static Context context;

    private static String account;

    public static void clear() {
        account = null;
    }

    public static String getAccount() {
        return account;
    }

    public static void setAccount(String account) {
        GanHuoCache.account = account;
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        GanHuoCache.context = context.getApplicationContext();
    }
}
