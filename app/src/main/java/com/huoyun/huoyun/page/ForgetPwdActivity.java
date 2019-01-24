package com.huoyun.huoyun.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.huoyun.huoyun.R;
import com.huoyun.huoyun.app.MyApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPwdActivity extends AppCompatActivity {

    private Context context = ForgetPwdActivity.this;

    @BindView(R.id.btn_yzm)
    Button btnYzm;

    public Button getCode_btn() {
        return btnYzm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        ButterKnife.bind(ForgetPwdActivity.this);
        MyApp.ftptimecount.setActivity(ForgetPwdActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.btn_next, R.id.btn_yzm})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_next:

                break;
            case R.id.btn_yzm:
                MyApp.ftptimecount.start();
                break;
        }
    }

}
