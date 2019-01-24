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

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                String pwd = etNewPwd.getText().toString();
                String newPwd = etNewPwdSure.getText().toString();
                if(!TextUtils.isEmpty(pwd)&&!TextUtils.isEmpty(newPwd)){
                    if(pwd.equals(newPwd)){
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
