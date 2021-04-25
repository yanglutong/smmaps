package com.sm.smmap.smmap.Base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.pedaily.yc.ycdialoglib.utils.DialogUtils;
import com.sm.smmap.smmap.Retrofit.ActivityManager;
import com.sm.smmap.smmap.Utils.MyToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by shkstart on 2016/12/6 0006.
 */
public abstract class BaseActivity extends FragmentActivity {
    private static boolean mBackKeyPressed = false;//记录是否有首次按键
    //权限
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_CAMERA = 2;
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    public List<String> mPermissionList = new ArrayList<>();
    public String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,

            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        DialogUtils.requestMsgPermission(this);
        ButterKnife.bind(this);

        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);

        initTitle();

        initData();

    }

    protected abstract void initData();

    protected void initTitle() {

    }

    ;

    protected abstract int getLayoutId();

//    public AsyncHttpClient client = new AsyncHttpClient();

    //启动新的activity
    public void goToActivity(Class Activity, Bundle bundle) {
        Intent intent = new Intent(this, Activity);
        //携带数据
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }

        startActivity(intent);
    }

    //销毁当前的Activity
    public void removeCurrentActivity() {
        ActivityManager.getInstance().removeCurrent();
    }

    //销毁所有的activity
    public void removeAll() {
        ActivityManager.getInstance().removeAll();
    }

    //保存用户信息
//    public void saveUser(User user) {
//        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("userid", user.getUserinfo().ge());//保存的Userid
//        editor.putString("dwid", user.getUserinfo().getDwid());
//        editor.putString("truename", user.getUserinfo().getTruename());
//        editor.putString("neckname", user.getUserinfo().getNeckname());
//        editor.putString("userphone", user.getUserinfo().getUserphone());
//        editor.putString("picurl", user.getUserinfo().getPicurl());
//        editor.putInt("workstatus", user.getUserinfo().getWorkstatus());
//        editor.putString("purcode", user.getUserinfo().getPurcode());
////        editor.putString("pwd",user.getUserinfo().getPwd().toString());//默认暂时为空的信息
//        editor.commit();//必须提交，否则保存不成功
//    }

    //读取用户信息
//    public User readUser(){
//        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
//        User user = new User();
//        user.setName(sp.getString("name",""));
//        user.setImageurl(sp.getString("imageurl", ""));
//        user.setPhone(sp.getString("phone", ""));
//        user.setCredit(sp.getBoolean("iscredit",false));

    //        return user;
//    }
    public void getPermissions() {//获取手机权限
        mPermissionList.clear();
//        for (int i = 0; i < permissions.length; i++) {
//            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
//                mPermissionList.add(permissions[i]);
//            }
//        }
//        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
////            Toast.makeText(LoginActivity.this,"已经授权",Toast.LENGTH_LONG).show();
//        } else {//请求权限方法
//            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
//            ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_CALL_CAMERA);
//        }
    }

//    /**
//     * 系统自带的弹出框
//     * @param keyCode
//     * @param event
//     * @return
//     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
//            AlertDialog.Builder dialog = new AlertDialog.Builder(this);//新建一个对话框
//
//            dialog.setMessage("确定要退出吗?");//设置提示信息
//            //设置确定按钮并监听
//            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    removeAll();
//                }
//            });
//            //设置取消按钮并监听
//            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    //这里什么也不用做
//                }
//            });
//            dialog.show();//最后不要忘记把对话框显示出来
//        }
//        return false;
//    }

    /**
     * //     * 第三方弹出窗
     * //     * @param keyCode
     * //     * @param event
     * //     * @return
     * //
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {//当返回按键被按下
////            AlertDialog.Builder dialog = new AlertDialog.Builder(this);//新建一个对话框
////
////            dialog.setMessage("确定要退出吗?");//设置提示信息
////            //设置确定按钮并监听
////            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    removeAll();
////                }
////            });
////            //设置取消按钮并监听
////            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
////                @Override
////                public void onClick(DialogInterface dialog, int which) {
////                    //这里什么也不用做
////                }
////            });
////            dialog.show();//最后不要忘记把对话框显示出来
////
////
////
////
////
//            final CustomDialogFragment dialog = new CustomDialogFragment();
//            //CustomDialogFragment customDialogFragment = CustomDialogFragment.create(getSupportFragmentManager());
//            dialog.setFragmentManager(getSupportFragmentManager());
//            dialog.setTitle("这个是是标题");//标题
//            dialog.setContent("这个是弹窗的内容");//内容
//            dialog.setCancelContent("取消");//取消
//            dialog.setOkContent("确定");//确定
//            dialog.setDimAmount(0.0f);
//            dialog.setTag("BottomDialog");
//            dialog.setCancelOutside(true);
//            dialog.setCancelListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CustomDialogFragment.dismissDialogFragment();
//                    ToastUtils.showRoundRectToast("取消了");
//                }
//            });
//            dialog.setOkListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    CustomDialogFragment.dismissDialogFragment();
//                    ToastUtils.showRoundRectToast("确定了");
//                    removeAll();
//                }
//            });
//            //这个高度可以自己设置，十分灵活
//            //dialog.setHeight(getScreenHeight() / 2);
//            dialog.show();
//            dialog.setLoadFinishListener(new BaseDialogFragment.onLoadFinishListener() {
//                @Override
//                public void listener() {
//                    Log.e("结束了","监听事件");
//                }
//            });
//
//
//        }
//        return false;
//    }
//    private long exitTime = 0;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
////        if (keyCode == KeyEvent.KEYCODE_BACK
////                && event.getAction() == KeyEvent.ACTION_DOWN) {
////            if ((System.currentTimeMillis() - exitTime) > 2000) {
////                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
////                exitTime = System.currentTimeMillis();
////                finish();
////                System.exit(0);
////            }
//            return true;
////        }
////        return super.onKeyDown(keyCode, event);
//
//
//    }
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

    private long mPressedTime = 0;

    public void setOnBackPressed(boolean b) {
        if (b == true) {
            onBackPressed();
        } else {

        }
    }

    @Override
    public void onBackPressed() {//重写返回键方法
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
//            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            MyToast.showToast("再按一次退出程序");
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }
}
