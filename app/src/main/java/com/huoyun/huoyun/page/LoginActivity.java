package com.huoyun.huoyun.page;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.huoyun.huoyun.R;
import com.huoyun.huoyun.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private Context context = LoginActivity.this;

    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_pwd)
    EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(LoginActivity.this);

    }

    @OnClick({R.id.tv_forget_pwd, R.id.tv_register, R.id.tv_login})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.tv_forget_pwd:
                intent.setClass(context, ForgetPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_register:
                intent.setClass(context, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_login:
                String phoneNum = etPhoneNum.getText().toString();
                String pwd = etPwd.getText().toString();
                if(TextUtils.isEmpty(phoneNum)||TextUtils.isEmpty(pwd)){
                    ToastUtil.showShort(context, "电话号或密码不能为空");
                }else {
                    intent.setClass(context, MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
