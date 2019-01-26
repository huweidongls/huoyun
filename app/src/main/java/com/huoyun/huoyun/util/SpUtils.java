package com.huoyun.huoyun.util;

import android.content.Context;

import com.vise.xsnow.cache.SpCache;

/**
 * Created by Administrator on 2018/10/26.
 */

public class SpUtils {

    private static SpCache spCache;
    public static String PRO_LOGIN_INFO = "login_info";

    public static String getLoginInfo(Context context){
        spCache = new SpCache(context);
        return spCache.get(PRO_LOGIN_INFO, "");
    }

    public static void setLoginInfo(Context context, String data){
        spCache = new SpCache(context);
        spCache.put(PRO_LOGIN_INFO, data);
    }

    public static void clear(Context context){
        spCache = new SpCache(context);
        spCache.clear();
    }

}
