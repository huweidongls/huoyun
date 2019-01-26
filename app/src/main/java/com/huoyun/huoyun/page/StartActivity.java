package com.huoyun.huoyun.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.huoyun.huoyun.R;
import com.huoyun.huoyun.util.SpUtils;

import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity {

    private Context context = StartActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initData();

    }

    /**
     * 判断本地有信息跳main，无信息跳登录
     */
    private void initData() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent();
                String uid = SpUtils.getLoginInfo(context);
                if(TextUtils.isEmpty(uid)){
                    intent.setClass(context, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    intent.setClass(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);

    }
}
