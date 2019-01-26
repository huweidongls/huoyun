package com.huoyun.huoyun.page;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.huoyun.huoyun.R;
import com.huoyun.huoyun.service.UploadLocationService;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    private Context context = OrderActivity.this;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    private double latitude = 0.0;//纬度
    private double longitude = 0.0;//经度

    private static final int REQUEST_CODE = 103;

    /**
     * 照片本地路径
     */
    private String imgUrl = "";
    /**
     * 照片七牛路径
     */
    private String imgQiniu = "";
    /**
     * 七牛token
     */
    private String token = "";
    private UploadManager uploadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ButterKnife.bind(OrderActivity.this);

        initData();

    }

    private void initData() {

        startLocate();
        Configuration config = new Configuration.Builder()
                .zone(AutoZone.autoZone)
                .build();
        uploadManager = new UploadManager(config);

    }

    @OnClick({R.id.rl_back, R.id.btn_ok})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_ok:
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.mipmap.ic_launcher);
                normalDialog.setTitle("提示");
                normalDialog.setMessage("请拍摄货物现场画面");
                normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //单选
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(true)  //设置是否单选
                                .setViewImage(true) //是否点击放大图片查看,，默认为true
                                .start(OrderActivity.this, REQUEST_CODE); // 打开相册
                    }
                });
                normalDialog.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                // 显示
                normalDialog.show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);

            /**
             * 是否是来自于相机拍照的图片，
             * 只有本次调用相机拍出来的照片，返回时才为true。
             * 当为true时，图片返回的结果有且只有一张图片。
             */
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            imgUrl = images.get(0);
            String str = imgUrl.substring(imgUrl.lastIndexOf("."));
            final String name = System.currentTimeMillis() + (int) (Math.random() * 100000) + str;
            uploadManager.put(imgUrl, name, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                            if (info.isOK()) {
                                //走自己逻辑，name为七牛路径，经纬度在最底下方法中每秒更新一次

                            } else {
                                Log.i("qiniu", "Upload Fail");
                                //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                            }
//                                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                        }
                    }, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
    }

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.e("123123", "lat" + location.getLatitude() + "--long" + location.getLongitude());

        }
    }

}
