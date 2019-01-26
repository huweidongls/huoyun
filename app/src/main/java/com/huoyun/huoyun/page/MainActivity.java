package com.huoyun.huoyun.page;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huoyun.huoyun.R;
import com.huoyun.huoyun.adapter.MainAdapter;
import com.huoyun.huoyun.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.iv_isjiedan)
    ImageView ivJiedan;

    private MainAdapter adapter;
    private List<String> mList;

    private boolean isJiedan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(MainActivity.this);

        initData();

    }

    private void initData() {

        LinearLayoutManager manager = new LinearLayoutManager(context);
        rv.setLayoutManager(manager);
        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        adapter = new MainAdapter(mList);
        rv.setAdapter(adapter);

    }

    @OnClick({R.id.rl_menu, R.id.iv_isjiedan, R.id.btn_person_info, R.id.btn_modify_pwd, R.id.btn_exit})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_menu:
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.iv_isjiedan:
                if(isJiedan){
                    Glide.with(context).load(R.mipmap.off).into(ivJiedan);
                    isJiedan = false;
                }else {
                    Glide.with(context).load(R.mipmap.on).into(ivJiedan);
                    isJiedan = true;
                }
                break;
            case R.id.btn_person_info:
                intent.setClass(context, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_modify_pwd:

                break;
            case R.id.btn_exit:
                SpUtils.clear(context);
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
