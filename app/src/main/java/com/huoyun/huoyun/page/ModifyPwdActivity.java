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

public class ModifyPwdActivity extends AppCompatActivity {

    private Context context = ModifyPwdActivity.this;

    @BindView(R.id.et_old_pwd)
    EditText etOldPwd;
    @BindView(R.id.et_new_pwd)
    EditText etNewPwd;
    @BindView(R.id.et_new_pwd_sure)
    EditText etNewPwdSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);

        ButterKnife.bind(ModifyPwdActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.btn_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                String oldPwd = etOldPwd.getText().toString();
                String newPwd = etNewPwd.getText().toString();
                String newPwdSure = etNewPwdSure.getText().toString();
                if(TextUtils.isEmpty(oldPwd)||TextUtils.isEmpty(newPwd)||TextUtils.isEmpty(newPwdSure)){
                    ToastUtil.showLong(context, "密码不能为空");
                }else {
                    if(newPwd.equals(newPwdSure)){
                        ToastUtil.showLong(context, "密码修改成功");
                    }else {
                        ToastUtil.showLong(context, "新密码不一致");
                    }
                }
                break;
        }
    }

}
