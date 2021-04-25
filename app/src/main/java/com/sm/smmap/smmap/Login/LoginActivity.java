package com.sm.smmap.smmap.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.sm.smmap.smmap.MainActivity;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerGuijiView;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerJZ;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Retrofit.Bean.JzGetData;
import com.sm.smmap.smmap.Retrofit.RetrofitFactory;
import com.sm.smmap.smmap.Utils.ACacheUtil;
import com.sm.smmap.smmap.Utils.MyToast;
import com.sm.smmap.smmap.Utils.MyUtils;
import com.sm.smmap.smmap.Utils.ViewLoading;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends FragmentActivity implements View.OnClickListener {
    private EditText et_user, et_pwd;
    private ImageView iv_show;
    private Button bt_login;
    private LoginBena loginBena;
    private boolean showaBoolean = false;
    private TextView tv_version;


    private void setUser_pwd() {
        SharedPreferences userSettings = getSharedPreferences("setting", 0);
        String namesp = userSettings.getString("name", "");
        String pswsp = userSettings.getString("pwd", "");
        et_user.setText(namesp);
        et_pwd.setText(pswsp);
        String appVersionName = AppUtils.getAppVersionName();
        tv_version.setText("测试版版本:" + appVersionName + "");
    }

    @SuppressLint("NewApi")
    public void setStatBar() {//根据版本设置沉浸式状态栏
        View decorView = getWindow().getDecorView();
        int option =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        decorView.setSystemUiVisibility(option);
        getWindow().setStatusBarColor(Color.TRANSPARENT
        );
    }

    private void findViews() {
        et_user = findViewById(R.id.et_user);
        et_pwd = findViewById(R.id.et_pwd);
        iv_show = findViewById(R.id.iv_show);
        iv_show.setOnClickListener(this);
        bt_login = findViewById(R.id.bt_login);
        bt_login.setOnClickListener(this);
        tv_version = findViewById(R.id.tv_version);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_login2);
        setStatBar();

        findViews();
        et_user.setText("admin");
        et_user.setText("admin");

        setUser_pwd();//设置保存的账号密码
//        //计算
//        LatLng latLng1 = new LatLng(38.031498, 114.45076);
//        LatLng latLng2 = new LatLng(38.031508, 114.451546);
//        double distance = DistanceUtil.getDistance(latLng1, latLng2);
//        Log.e("nzq", "onCreate: distance" + distance + "米");
//
//        LatLng latLng3 = new LatLng(38.02462665273593, 114.43804000428864);
//        LatLng latLng4 = new LatLng(38.024638926566844, 114.43882790283176);
//        double distancea = DistanceUtil.getDistance(latLng3, latLng4);
//        Log.e("nzq", "onCreate: distancea" + distancea + "米");


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_show:
//                Toast.makeText(LoginActivity.this, "你点击了展示密码功能", Toast.LENGTH_LONG).show();
                if (showaBoolean == false) {
                    showaBoolean = true;
                    et_pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Log.e("showaBoolean", "onClick: " + showaBoolean);
                } else if (showaBoolean == true) {
                    showaBoolean = false;
                    et_pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Log.e("showaBoolean", "onClick: " + showaBoolean);
                }


                break;
            case R.id.bt_login:
//                Toast.makeText(LoginActivity.this, "你点击登陆按钮", Toast.LENGTH_LONG).show();
                boolean availableByPing = NetworkUtils.isAvailableByPing();
                if (availableByPing == false) {
                    Log.d("nzq", "availableByPingonClick: " + availableByPing);
//                    Toast.makeText(LoginActivity.this, "网络不可用", Toast.LENGTH_SHORT).show();
                   MyToast.showToast("网络不可用");
                    break;
                } else {
                    Log.d("nzq", "availableByPingonClick: " + availableByPing);
                }
                Logins();//登陆功能
//                MyToast.showToast(getApplicationContext(),"你好");
                break;
        }
    }

    private void Logins() {
        final String number = et_user.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (!TextUtils.isEmpty(number) && !TextUtils.isEmpty(pwd)) {
            ViewLoading.show(LoginActivity.this, "正在登陆");
            boolean connected = NetworkUtils.isConnected();
            if (connected == false) {
//                Toast.makeText(LoginActivity.this, "网络不可用", Toast.LENGTH_LONG).show();
                MyToast.showToast("网络不可用");
                return;
            }

            try {
                RetrofitFactory.getInstence().API().login(number, pwd).enqueue(new Callback<LoginBena>() {
                    @Override
                    public void onResponse(Call<LoginBena> call, Response<LoginBena> response) {
//                            Log.d("wnzq", "onResponse: " + response.body().toString() + "aaa" + response.body().getData().getPassWord().toString());
//                    ACacheUtil.putID("1");//临时
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));//临时
                        loginBena = response.body();
                        if (loginBena == null) {
                            ViewLoading.dismiss(LoginActivity.this);
//                            Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_LONG).show();
                           MyToast.showToast("服务器异常");
                            return;
                        }
                        if (loginBena.getCode() == 1) {
//                        Toast.makeText(AppLication.getInstance().getApplicationContext(), "登陆成功", Toast.LENGTH_LONG).show();
                            SharedPreferences userSettings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = userSettings.edit();
                            editor.putString("name", number);
                            editor.putString("pwd", pwd);
                            editor.commit();


                            ACacheUtil.putID(loginBena.getData().getUserId() + "");
                            ACacheUtil.putNumberMax(loginBena.getData().getTotal() + "");
                            ACacheUtil.putNumberremainder(loginBena.getData().getRemainder() + "");
//

//                        try {
//                            Thread.sleep(3000);
//
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }

//                        new Handler().postDelayed(new Runnable() {
////                            @Override
//                            public void run() {
//                                ViewLoading.dismiss(LoginActivity.this);
//                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                                finish();
//                            }
//                        }, 5000);
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    /**
                                     *要执行的操作
                                     */
                                    ViewLoading.dismiss(LoginActivity.this);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            };
                            Timer timer = new Timer();
                            timer.schedule(task, 3000);//3秒后执行TimeTask的run方法


                        } else if (loginBena.getCode() == 0) {
//                            Toast.makeText(LoginActivity.this, "账号或者密码错误", Toast.LENGTH_LONG).show();
                          MyToast.showToast("账号或者密码错误");
                            SharedPreferences userSettings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = userSettings.edit();
                            editor.putString("name", number);
                            editor.putString("pwd", pwd);
                            editor.commit();
                            ViewLoading.dismiss(LoginActivity.this);
                        } else if (loginBena.getCode() == 2) {
//                            Toast.makeText(LoginActivity.this, "账号不存在", Toast.LENGTH_LONG).show();
                            MyToast.showToast("账号不存在");
                            SharedPreferences userSettings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = userSettings.edit();
                            editor.putString("name", number);
                            editor.putString("pwd", pwd);
                            ViewLoading.dismiss(LoginActivity.this);
                            editor.commit();
                        } else if (loginBena.getCode() == -3) {
                            MyToast.showToast("参数错误");
//                            Toast.makeText(LoginActivity.this, "参数错误", Toast.LENGTH_LONG).show();
                            SharedPreferences userSettings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = userSettings.edit();
                            editor.putString("name", number);
                            editor.putString("pwd", pwd);
                            editor.commit();
                            ViewLoading.dismiss(LoginActivity.this);
                        }

//                            if (loginBena.getCode())

                    }

                    @Override
                    public void onFailure(Call<LoginBena> call, Throwable t) {
                        Log.d("wnzq", "onFailure: " + t.getMessage().toString());
                        MyToast.showToast("服务器错误");
//                        Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_LONG).show();
                        ViewLoading.dismiss(LoginActivity.this);

                    }
                });
            } catch (Exception e) {
                ViewLoading.dismiss(LoginActivity.this);
                MyToast.showToast("服务器异常");
//                Toast.makeText(LoginActivity.this, "服务器异常", Toast.LENGTH_LONG).show();
            }

        } else {
            MyToast.showToast("用户名或密码不能为空");
//            Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
        }
    }

}

