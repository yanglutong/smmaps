package com.sm.smmap.smmap.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.blankj.utilcode.util.AppUtils;
import com.j256.ormlite.stmt.query.In;
import com.sm.smmap.smmap.Bean.NumberBean;
import com.sm.smmap.smmap.MainActivity;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Retrofit.DataBean;
import com.sm.smmap.smmap.Retrofit.MyURL;
import com.sm.smmap.smmap.Retrofit.RetrofitFactory;
import com.sm.smmap.smmap.Utils.Bean.DownBean;
import com.sm.smmap.smmap.Utils.http.OkGoUpdateHttpUtil;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.vector.update_app.utils.AppUpdateUtils;
import com.vector.update_app.utils.ColorUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static android.content.Context.SENSOR_SERVICE;

public class MyUtils {
    private static Context context;
    private static boolean typeAppup = false;//是否强制更新

    public static void getPermissions(MainActivity mainActivity) {
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mainActivity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(mainActivity, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    public MyUtils(Context context) {
        this.context = context;

    }

    //权限
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    public static List<String> mPermissionList = new ArrayList<>();
    public static String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,

            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.GET_TASKS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.ACCESS_NOTIFICATION_POLICY,
            Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.RECEIVE_BOOT_COMPLETED,
            Manifest.permission.REQUEST_INSTALL_PACKAGES
    };//申请的权限

    /**
     * 申请权限
     */
    public static void getPermissions() {//获取手机权限
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
//            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
        }
    }

    @SuppressLint("NewApi")
    public static void setStatBar(MainActivity mainActivity) {//根据版本设置沉浸式状态栏
        View decorView = mainActivity.getWindow().getDecorView();
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        mainActivity.getWindow().setStatusBarColor(Color.TRANSPARENT
        );
    }

    public static void Viewjizhan(Marker markerMy, BaiduMap mBaiduMap, DataBean dataBean) {
        if (markerMy != null) {
            LatLng positionjingbaojizhan = markerMy.getPosition();//基站的位置
            Log.d(TAG, "onReceiveLocation: markerMy" + "有数据");
            //画大圆
            int TAS = 78;
            String ta = ACacheUtil.getTa();
            int i = Integer.parseInt(ta);
            int Myradius = i * TAS;
            OverlayOptions ooCircle = new CircleOptions()
//                            .fillColor(0x000000FF)
                    .fillColor(Color.argb(40, 176,
                            224,
                            230))
                    .center(positionjingbaojizhan)
                    .stroke(new Stroke(2, Color.rgb(135,
                            206,
                            235)))
                    .radius(Myradius);
            LatLng center = ((CircleOptions) ooCircle).getCenter();
            Log.d("nzq", "onCreate: " + center);
            mBaiduMap.addOverlay(ooCircle);
            //小圆
//                    LatLng llDot = new LatLng(39.90923, 116.447428);
            OverlayOptions ooDot = new DotOptions().center(positionjingbaojizhan).radius(6).color(0xFF0000FF);
            mBaiduMap.addOverlay(ooDot);
            //构建Marker图标
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.jizhan1);
            //构建MarkerOption，用于在地图上添加Marker
            Bundle bundles = new Bundle();
            bundles.putString("mcc", dataBean.getResult().getMcc());
            bundles.putString("mnc", dataBean.getResult().getMnc());
            bundles.putString("lac", dataBean.getResult().getLac());
            bundles.putString("ci", dataBean.getResult().getCi());
            bundles.putString("lat", String.valueOf(positionjingbaojizhan.latitude));
            bundles.putString("lon", String.valueOf(positionjingbaojizhan.longitude));
            bundles.putString("radius", dataBean.getResult().getRadius());
            bundles.putString("address", dataBean.getResult().getAddress());
            OverlayOptions optiona = new MarkerOptions()
                    .anchor(10, 30)
                    .extraInfo(bundles)
                    .position(positionjingbaojizhan) //必传参数
                    .perspective(true)
                    .icon(bitmap) //必传参数
                    .draggable(true)
                    .draggable(true)
                    //设置平贴地图，在地图中双指下拉查看效果
                    .flat(true)
                    .alpha(0.5f);
            //在地图上添加Marker，并显示
            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker
            //构建Marker图标
//            Log.d(TAG, "pointsonMapClick: " + points.size());
        }
    }

    //版本更新
    public static void upApp(final Context context, String appId) {
        RetrofitFactory.getInstence().API().download("1").enqueue(new Callback<DownBean>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<DownBean> call, final Response<DownBean> response) {
                Log.d(TAG, "upApponResponse: " + response.body());
                if (response.body().getData() != null) {
                    String versionCode = response.body().getData().getVersionCode();
                    int DoubuleversionCode = Integer.parseInt(versionCode);
                    int appVersionCode = AppUtils.getAppVersionCode();
                    //是否强制更新
                    if (response.body().getData().getAppType() == 1) {
                        typeAppup = true;
                    } else {
                        typeAppup = false;
                    }
//
                    if (DoubuleversionCode > appVersionCode) {// 服务器版本 大于当前APP 版本
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("appId", "1");
//        params.put("appVersion", AppUpdateUtils.getVersionName((Activity) context));

                        new UpdateAppManager
                                .Builder()
                                //必须设置，当前Activity
                                .setActivity((Activity) context)
                                //必须设置，实现httpManager接口的对象
                                .setHttpManager(new OkGoUpdateHttpUtil())
                                //必须设置，更新地址
//                .setUpdateUrl(MyURL.BASE_URL + "UpdateInfo.aspx")
                                .setUpdateUrl("http://39.107.141.215:81/app/download")
                                .handleException(new ExceptionHandler() {
                                    @Override
                                    public void onException(Exception e) {
                                        e.printStackTrace();
                                    }
                                })
                                //以下设置，都是可选
                                //设置请求方式，默认get
                                .setPost(false)
                                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
                                .setParams(params)

                                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度
                                .hideDialogOnDownloading()
                                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
                                .setTopPic(R.mipmap.top_3)
                                //为按钮，进度条设置颜色，默认从顶部图片自动识别。
                                .setThemeColor(ColorUtil.getRandomColor())
                                .setThemeColor(Color.rgb(255, 0, 0))
                                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                                .setTargetPath(path)
                                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
                                //.setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                                //不显示通知栏进度条
//                                .dismissNotificationProgress()
                                //是否忽略版本
                                //.showIgnoreVersion()
                                .setIgnoreDefParams(true)
                                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                                    @Override
                                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                                        String apkFileUrl = updateApp.getApkFileUrl();
                                        Log.d(TAG, "onUpdateNotifyDialogCancel: " + apkFileUrl);
                                    }
                                })
                                .build()
                                //检测是否有新版本
                                .checkNewApp(new UpdateCallback() {
                                    /**
                                     * 解析json,自定义协议
                                     *
                                     * @param json 服务器返回的json
                                     * @return UpdateAppBean
                                     */
                                    @Override
                                    protected UpdateAppBean parseJson(String json) {
                                        Log.d(TAG, "parseJson: " + json);
                                        UpdateAppBean updateAppBean = new UpdateAppBean();
                                        try {
                                            JSONObject jsonObject = new JSONObject(json);
                                            updateAppBean
                                                    //（必须）是否更新Yes,No
                                                    .setUpdate("Yes")
//                                    .setUpdate("e")
                                                    //（必须）新版本号，
                                                    .setNewVersion(jsonObject.optString("versionName"))
//
                                                    //（必须）下载地址
                                                    .setApkFileUrl(response.body().getData().getAppPath() + "")
                                                    .setUpdateDefDialogTitle("是否升级到新版本" + response.body().getData().getVersionName() + "")
                                                    //（必须）更新内容
                                                    .setUpdateLog(response.body().getData().getNewFunction())
                                                    //大小，不设置不显示大小，可以不设置
//                                    .setTargetSize(jsonObject.optString("newFunction"))
                                                    //是否强制更新，可以不设置

                                                    .setConstraint(typeAppup);
                                            //设置md5，可以不设置
//                                    .setNewMd5(jsonObject.optString("new_md51"));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        return updateAppBean;
                                    }

                                    /**
                                     * 网络请求之前
                                     */
                                    @Override
                                    public void onBefore() {
                                        CProgressDialogUtils.showProgressDialog((Activity) context);
                                    }

                                    /**
                                     * 网路请求之后
                                     */
                                    @Override
                                    public void onAfter() {
                                        CProgressDialogUtils.cancelProgressDialog((Activity) context);
                                    }

                                });


                    }

                }
            }

            @Override
            public void onFailure(Call<DownBean> call, Throwable t) {
                Log.d(TAG, "upApponFailure: " + t.getMessage());
            }
        });

    }

    //查询次数
    public static void getNumber(String appId) {
        RetrofitFactory.getInstence().API().NUMBER(appId).enqueue(new Callback<NumberBean>() {
            @Override
            public void onResponse(Call<NumberBean> call, Response<NumberBean> response) {
                if (response.body().getData() != null) {
                    ACacheUtil.putNumberMax(response.body().getData().getTotal() + "");//一共查询次数
                    ACacheUtil.putNumberremainder(response.body().getData().getRemainder() + "");
                    Log.d(TAG, "getNumberonResponse: " + "最多" + ACacheUtil.getNumberMax() + "剩余:" + ACacheUtil.getNumberremainder());
                }
            }

            @Override
            public void onFailure(Call<NumberBean> call, Throwable t) {

            }
        });
    }

    //集合转String
    public static String listToString(List<Double> stringList) {
        if (stringList == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (Double string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    public static List<Double> StringTolist(String str) {

        List list = Arrays.asList(str.split(","));
        List<Double> integerList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
//            Integer.parseInt((String) list.get(i));
//            integerList.add(Integer.parseInt((String) list.get(i)));
            integerList.add(Double.parseDouble((String) list.get(i)));
        }
        return integerList;
    }
    public static List<BillObject> removeDuplicate(List<BillObject> list) {

        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).getTac().equals(list.get(i).getTac()) && list.get(j).getCi().equals(list.get(i).getCi())) {
                    list.remove(j);
                }
            }
        }

        return list;
    }
}
