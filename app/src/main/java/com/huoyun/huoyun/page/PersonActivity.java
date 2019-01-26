package com.huoyun.huoyun.page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.huoyun.huoyun.R;
import com.huoyun.huoyun.util.ToastUtil;
import com.huoyun.huoyun.util.WeiboDialogUtils;
import com.qiniu.android.common.AutoZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonActivity extends AppCompatActivity {

    private Context context = PersonActivity.this;

    @BindView(R.id.iv_car)
    ImageView ivCar;
    @BindView(R.id.iv_xsz)
    ImageView ivXsz;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_zzl)
    EditText etZzl;
    @BindView(R.id.et_chang)
    EditText etChang;
    @BindView(R.id.et_kuan)
    EditText etKuan;
    @BindView(R.id.et_gao)
    EditText etGao;

    private int REQUEST_CODE = 101;
    private int REQUEST_CODE1 = 102;

    private Dialog loadingDialog;
    private UploadManager uploadManager;

    /**
     * 车辆照片
     */
    private String imgCarUrl = "";
    /**
     * 行驶证照片
     */
    private String imgXszUrl = "";
    /**
     * 七牛token
     */
    private String token = "";
    private List<String> mList;
    private List<String> scList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        ButterKnife.bind(PersonActivity.this);

        initData();

    }

    private void initData() {

        Configuration config = new Configuration.Builder()
                .zone(AutoZone.autoZone)
                .build();
        uploadManager = new UploadManager(config);

    }

    @OnClick({R.id.rl_back, R.id.rl_upload_car, R.id.rl_upload_xsz, R.id.btn_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_upload_car:
                //单选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE); // 打开相册
                break;
            case R.id.rl_upload_xsz:
                //单选
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(true)  //设置是否单选
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(this, REQUEST_CODE1); // 打开相册
                break;
            case R.id.btn_sure:
                String name = etName.getText().toString();
                String phoneNum = etPhoneNum.getText().toString();
                String zzl = etZzl.getText().toString();
                String chang = etChang.getText().toString();
                String kuan = etKuan.getText().toString();
                String gao = etGao.getText().toString();
                if(TextUtils.isEmpty(name)||TextUtils.isEmpty(phoneNum)||TextUtils.isEmpty(zzl)||TextUtils.isEmpty(chang)
                        ||TextUtils.isEmpty(kuan)||TextUtils.isEmpty(gao)||TextUtils.isEmpty(imgCarUrl)
                        ||TextUtils.isEmpty(imgXszUrl)){
                    ToastUtil.showLong(context, "请完善信息之后上传");
                }else {
                    post();
                }
                break;
        }
    }

    /**
     * 图片上传七牛
     */
    private void post() {

        loadingDialog = WeiboDialogUtils.createLoadingDialog(context, "发布中...");

        mList = new ArrayList<>();
        scList = new ArrayList<>();
        mList.add(imgCarUrl);
        mList.add(imgXszUrl);

        //图片上传为rxjava异步上传mList为本地选取的图片地址，scList为七牛返回的链接数组
        Observable.fromIterable(mList)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        String str = value.substring(value.lastIndexOf("."));
                        final String name = System.currentTimeMillis() + (int) (Math.random() * 100000) + str;
                        uploadManager.put(value, name, token,
                                new UpCompletionHandler() {
                                    @Override
                                    public void complete(String key, ResponseInfo info, JSONObject res) {
                                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                                        if (info.isOK()) {
                                            Log.i("qiniu", "Upload Success");
                                            scList.add(name);
                                            if (mList.size() == scList.size()) {
                                                Log.e("qiniu", "上传成功");
                                                Gson gson = new Gson();
                                                String imageList = gson.toJson(scList);
                                                Log.e("qiniu", imageList);
                                                postImg();
                                            }
                                        } else {
                                            Log.i("qiniu", "Upload Fail");
                                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                                        }
//                                        Log.i("qiniu", key + ",\r\n " + info + ",\r\n " + res);
                                    }
                                }, null);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
//                        Log.e("qiniu", "上传成功");
//                        Gson gson = new Gson();
//                        String imageList = gson.toJson(scList);
//                        Log.e("qiniu", imageList);
                    }
                });

    }

    /**
     * 上传信息
     */
    private void postImg() {

        String images = "";

        for (int i = 0; i < scList.size(); i++) {
            if (i == scList.size() - 1) {
                images = images + scList.get(i);
            } else {
                images = images + scList.get(i) + ",";
            }
        }

        //根据接口传参，这个是以前代码粘过来的
        ViseHttp.POST("url")
                .addParam("title", " ")
                .addParam("images", images)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        Gson gson = new Gson();
//                        SavePostBean savePostBean = gson.fromJson(data, SavePostBean.class);
//                        Log.e("qiniu", savePostBean.getData() + "::" + savePostBean.getMsg() + "::" + savePostBean.getCode() + "");
//                        if (savePostBean.getCode() == 200) {
//                            WeiboDialogUtils.closeDialog(loadingDialog);
//                            ToastUtil.showShort(context, "上传成功");
//                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {
                        Log.e("222", "网络请求失败！");
                    }
                });

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
            Glide.with(context).load(images.get(0)).into(ivCar);
            imgCarUrl = images.get(0);
        }
        if (requestCode == REQUEST_CODE1 && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);

            /**
             * 是否是来自于相机拍照的图片，
             * 只有本次调用相机拍出来的照片，返回时才为true。
             * 当为true时，图片返回的结果有且只有一张图片。
             */
            boolean isCameraImage = data.getBooleanExtra(ImageSelector.IS_CAMERA_IMAGE, false);
            Glide.with(context).load(images.get(0)).into(ivXsz);
            imgXszUrl = images.get(0);
        }
    }

}
