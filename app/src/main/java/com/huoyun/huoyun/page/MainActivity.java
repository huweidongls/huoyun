package com.huoyun.huoyun.page;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huoyun.huoyun.R;
import com.huoyun.huoyun.adapter.MainAdapter;
import com.huoyun.huoyun.service.UploadLocationService;
import com.huoyun.huoyun.util.SpUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;

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
    @BindView(R.id.refresh)
    RefreshLayout refreshLayout;

    private MainAdapter adapter;
    private List<String> mList;

    private boolean isJiedan = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(MainActivity.this);

        PermissionManager.instance().request(this, new OnPermissionCallback() {
                    @Override
                    public void onRequestAllow(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_allow) + "\n" + permissionName);
                        Log.e("123123", "1"+permissionName);
                    }

                    @Override
                    public void onRequestRefuse(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_refuse) + "\n" + permissionName);
                        Log.e("123123", "2"+permissionName);
                    }

                    @Override
                    public void onRequestNoAsk(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_noAsk) + "\n" + permissionName);
                        Log.e("123123", "3");
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE);

        initData();

    }

    private void initData() {

        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        refreshLayout.setRefreshFooter(new ClassicsFooter(context));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(1000);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }
        });

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
                    //关闭上传定位服务
                    Intent intent2 = new Intent(context, UploadLocationService.class);
                    stopService(intent2);
                }else {
                    Glide.with(context).load(R.mipmap.on).into(ivJiedan);
                    isJiedan = true;
                    //开启上传定位服务
                    Intent intent1 = new Intent(context, UploadLocationService.class);
                    startService(intent1);
                }
                break;
            case R.id.btn_person_info:
                intent.setClass(context, PersonActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_modify_pwd:
                intent.setClass(context, ModifyPwdActivity.class);
                startActivity(intent);
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
