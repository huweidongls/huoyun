package com.huoyun.huoyun.util;

/**
 * Created by Administrator on 2018/7/13.
 */

import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import com.huoyun.huoyun.R;
import com.huoyun.huoyun.page.ForgetPwdActivity;


public class FTPTimeCount extends CountDownTimer {

    private SpannableString msp = null;
    private int timeNum = 60;
    private ForgetPwdActivity activity;

    public int getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(int timeNum) {
        this.timeNum = timeNum;
    }

    public ForgetPwdActivity getActivity() {
        return activity;
    }

    public void setActivity(ForgetPwdActivity activity) {
        this.activity = activity;
    }

    public FTPTimeCount(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    /**
     * 倒计时中
     */
    @Override
    public void onTick(long millisUntilFinished) {
        activity.getCode_btn().setEnabled(
                false);
        int timeInt = (int) (millisUntilFinished / 1000);
        setTimeNum(timeInt);

        // 创建一个 SpannableString对象
        msp = new SpannableString("重新发送" + timeInt + "秒");

        activity.getCode_btn().setTextColor(activity.getResources().getColor(R.color.black_333333));
        activity.getCode_btn().setText(msp);
    }

    /**
     * 倒计时结束
     */
    @Override
    public void onFinish() {
        // 创建一个 SpannableString对象
        msp = new SpannableString("获取验证码");

        // 设置字体背景色
        msp.setSpan(new ForegroundColorSpan(activity
                        .getResources().getColor(R.color.white_ffffff)), 0, 0,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // 设置前景色为

        activity.getCode_btn().setText(msp);
        activity.getCode_btn().setEnabled(true);
        setTimeNum(60);
    }
}
