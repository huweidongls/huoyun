package com.huoyun.huoyun.page;

import android.content.Context;
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

public class RegisterActivity extends AppCompatActivity {

    private Context context = RegisterActivity.this;

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_car_num)
    EditText etCarNum;
    @BindView(R.id.et_zzl)
    EditText etZzl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(RegisterActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.tv_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_register:
                String name = etName.getText().toString();
                String phoneNum = etPhoneNum.getText().toString();
                String pwd = etPwd.getText().toString();
                String carNum = etCarNum.getText().toString();
                String zzl = etZzl.getText().toString();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(phoneNum)||TextUtils.isEmpty(pwd)
                        ||TextUtils.isEmpty(carNum)||TextUtils.isEmpty(zzl)){
                    ToastUtil.showLong(context, "请完善信息");
                }else {
                    //走注册接口
                }
                break;
        }
    }

}
