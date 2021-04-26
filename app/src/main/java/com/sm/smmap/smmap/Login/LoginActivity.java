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
import com.google.gson.Gson;
import com.sm.smmap.smmap.Bean.DataBean;
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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    private TimeBean timeBean;
    public static DataBean dataBean=new DataBean();

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

        getData();
        setStatBar();

        findViews();
      /*  et_user.setText("admin");
        et_user.setText("admin");*/

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

    private void getData() {
        String url = "http://quan.suning.com/getSysTime.do";//这是苏宁的北京时间接口
        OkHttpClient client = new OkHttpClient();//创建一个通讯对象
        //创建一个Request
        Request request = new Request.Builder()
                .url(url)
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                //请求失败时回调的方法
                Log.d("test","onFailure");
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                //请求成功时回调的方法
                if(response.isSuccessful()){
                    Log.d("test","获取数据成功");
                    Log.d("test","responce.code()=="+response.code());
                    String  urlRiQi= response.body().string();
                    timeBean = new Gson().fromJson(urlRiQi, TimeBean.class);
                    Log.i("杨路通", "onResponse: "+urlRiQi);
                }
            }
        });

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

                        /*用于判断当前用户是否在有效期内*/
                        if(timeBean!=null){//获取服务器日期与当前日期进行对比如果超出日期用户不可使用
                            String editTime = loginBena.getData().getDeadline();//服务器日期
                            String sysTime1 = timeBean.getSysTime2();

                            dataBean.setData(editTime);//服务器时间存放

                            String[] current = sysTime1.split(" ");
                            Log.i("杨路通", "当前日期: "+current[0]);
                            Log.i("杨路通", "服务器有效日期: "+editTime);
                            //用户当前使用的日期
                            if(MyUtils.getTimeCompareSize(current[0],editTime)){//没过服务器时间
                                MyToast.showToast(" 剩余查询次数:"+loginBena.getData().getRemainder()+"\n"+"当前有效期:"+editTime);
                                /*MyToast.showToast("有效期");*/
                            }else{
                                MyToast.showToast("当前已过有效期");
                                ViewLoading.dismiss(LoginActivity.this);
                                return;
                            }
                        }else{
                            Calendar calendar = Calendar.getInstance();
                            //获取系统的日期
                            //年
                            int year = calendar.get(Calendar.YEAR);
//月
                            int month = calendar.get(Calendar.MONTH)+1;
//日
                            int day = calendar.get(Calendar.DAY_OF_MONTH);
                            String s=null;
                            if(month>9&&month<=12){
                                s=month+"";
                            }else{
                                s="0"+month;
                            }
                            String data=year+"-"+s+"-"+day;
                            Log.i("sss",data);
                            //用户当前使用的日期
                            String editTime = loginBena.getData().getDeadline();

                                    dataBean.setData(editTime);//服务器时间存放到配置列表
                            if(MyUtils.getTimeCompareSize(data,editTime)){//没过服务器时间
                                MyToast.showToast(" 剩余查询次数:"+loginBena.getData().getRemainder()+"\n"+"当前有效期:"+editTime);
                                /*MyToast.showToast("有效期");*/
                            }else{
                                MyToast.showToast("当前已过有效期");
                                ViewLoading.dismiss(LoginActivity.this);
                                return;
                            }
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

