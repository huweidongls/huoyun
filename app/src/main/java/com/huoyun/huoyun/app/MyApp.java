package com.huoyun.huoyun.app;

import android.app.Activity;
import android.app.Application;

import com.huoyun.huoyun.util.Constant;
import com.huoyun.huoyun.util.FTPTimeCount;
import com.vise.xsnow.http.ViseHttp;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/11 0011.
 */

public class MyApp extends Application {
    private List<Activity> mList = new LinkedList<Activity>();
    private static MyApp instance;

    // 修改密码获取验证码倒计时
    public static FTPTimeCount ftptimecount;

    public MyApp() {
    }
    public synchronized static MyApp getInstance() {
        if (null == instance) {
            instance = new MyApp();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ViseHttp.init(this);
        ViseHttp.CONFIG()
                //配置请求主机地址
                .baseUrl(Constant.BASE_URL);

        ftptimecount = new FTPTimeCount(60000, 1000);

//        IWXAPI mWxApi = WXAPIFactory.createWXAPI(this, ShareKey.WX_ID, true);
//        mWxApi.registerApp(ShareKey.WX_ID);

    }

    // add Activity
    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }
}
