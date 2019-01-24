package com.huoyun.huoyun.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.huoyun.huoyun.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private Context context = LoginActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(LoginActivity.this);

    }

    @OnClick({R.id.tv_forget_pwd})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.tv_forget_pwd:
                intent.setClass(context, ForgetPwdActivity.class);
                startActivity(intent);
                break;
        }
    }

}
