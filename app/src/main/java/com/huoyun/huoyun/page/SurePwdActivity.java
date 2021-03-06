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

public class SurePwdActivity extends AppCompatActivity {

    private Context context = SurePwdActivity.this;

    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_sure)
    EditText etNewPwdSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sure_pwd);

        ButterKnife.bind(SurePwdActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.btn_sure})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                String pwd = etNewPwd.getText().toString();
                String newPwd = etNewPwdSure.getText().toString();
                if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(newPwd)){
                    if(pwd.equals(newPwd)){
                        intent.setClass(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        ToastUtil.showLong(context, "密码不一致，请重新设置");
                    }
                }else {
                    ToastUtil.showLong(context, "密码不能为空");
                }
                break;
        }
    }

}
