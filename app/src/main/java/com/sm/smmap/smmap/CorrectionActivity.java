package com.sm.smmap.smmap;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.sm.smmap.smmap.Adapter.CorrectionAdapter;
import com.sm.smmap.smmap.Adapter.SerrnAdapter;
import com.sm.smmap.smmap.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerGuijiView;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerJZ;
import com.sm.smmap.smmap.Utils.ACacheUtil;
import com.sm.smmap.smmap.Utils.MyToast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CorrectionActivity extends FragmentActivity implements View.OnClickListener {
    EditText et_target, et_oneself, et_jz, et_correction_ta;
    ImageView finsh;
    ImageButton ib_button;
    Button button;
    CorrectionAdapter serrnAdapter;
    DBManagerJZ dbManagerJZ;
    LatLng latLngtarget = null;
    LatLng latLngoneself = null;
    CorrectionCallBack callBack = new CorrectionCallBack() {
        @Override
        public void call(GuijiViewBeanjizhan data) {
            Log.d("nzq", "GuijiViewBeanjizhancall: " + data);
            if (data != null) {
                et_jz.setText(data.getAddress() + "");
                latLngtarget = new LatLng(Double.parseDouble(data.getLat()), Double.parseDouble(data.getLon()));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_correction);
        Intent intent = getIntent();
        String lat = intent.getStringExtra("lat");
        String lon = intent.getStringExtra("lon");
        latLngoneself = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
//        setStatBar();
        findVies();
    }

    private void findVies() {
        try {
            dbManagerJZ = new DBManagerJZ(CorrectionActivity.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        et_target = findViewById(R.id.et_target);//目标手机TA值
        et_oneself = findViewById(R.id.et_oneself);//本手机TA值
        et_jz = findViewById(R.id.et_jz);
        et_correction_ta = findViewById(R.id.et_correction_ta);

        ib_button = findViewById(R.id.ib_button);
        ib_button.setOnClickListener(this);
        button = findViewById(R.id.button);
        button.setOnClickListener(this);
        finsh = findViewById(R.id.finsh);
        finsh.setOnClickListener(this);
        if (!TextUtils.isEmpty(ACacheUtil.gettarget())) {
            et_target.setText(ACacheUtil.gettarget() + "");
        }
        if (!TextUtils.isEmpty(ACacheUtil.getoneself())) {
            et_oneself.setText(ACacheUtil.getoneself() + "");
        }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                savaData();
                finish();
                break;
            case R.id.button:
                isEmptys();//非空判断

                break;

            case R.id.ib_button:
                final Dialog dialog2 = new Dialog(this, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                View inflate = LayoutInflater.from(this).inflate(R.layout.item_correctionshow, null);
                //初始化控件
                ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
                iv_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });
                Button bt_adddilao = new Button(this);
                bt_adddilao.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });

                List<GuijiViewBeanjizhan> guijiViewBeanjizhans = null;
                try {
                    guijiViewBeanjizhans = dbManagerJZ.guijiViewBeans();
                    Log.d("nzq", "guijiViewBeanjizhansonClick: " + guijiViewBeanjizhans);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                RecyclerView recyclerView = inflate.findViewById(R.id.ry);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                List<Integer> listnum = new ArrayList<>();
                for (int i = 1; i < guijiViewBeanjizhans.size() + 1; i++) {
                    listnum.add(i);
                }
                serrnAdapter = new CorrectionAdapter(this, guijiViewBeanjizhans, listnum, dialog2, callBack);
                recyclerView.setAdapter(serrnAdapter);
                Button button = inflate.findViewById(R.id.bt_adddilao);
                button.setText("关闭");
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });

                //将布局设置给Dialog
                dialog2.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialog2.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.CENTER);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialog2.show();//显示对话框
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void isEmptys() {
        if (TextUtils.isEmpty(et_target.getText().toString())) {
            MyToast.showToast("目标手机值不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_oneself.getText().toString())) {
            MyToast.showToast("本手机TA值不能为空");
            return;
        }
        if (TextUtils.isEmpty(et_jz.getText().toString())) {
            MyToast.showToast("基站不能为空");
            return;
        }
        if (latLngoneself == null) {
            MyToast.showToast("当前位置为空");
            return;
        }

        if (latLngtarget == null) {
            MyToast.showToast("基站位置为空");
            return;
        }
        try {
            double distancea = DistanceUtil.getDistance(latLngtarget, latLngoneself);
            DecimalFormat df = new DecimalFormat("0.00");
            //本机与基站距离  (D)
            String d = df.format(distancea);
            //D转为TA值
            double v = Double.parseDouble(d);
            double t = v / 78;
            //T1
            double v1 = Double.parseDouble(et_oneself.getText().toString());//本机TA值
            double t1 = t - v1;
            //目标手机TA值修正
            double v2 = Double.parseDouble(et_target.getText().toString());
            double correction = (float) v2 + (float) t1;
            Log.d("nzq", "correctionisEmptys: " + correction);
            et_correction_ta.setText(df.format(correction));

            savaData();
        } catch (Exception e) {

        }

    }

    private void savaData() {
        ACacheUtil.puttarget(et_target.getText().toString());
        ACacheUtil.putoneself(et_oneself.getText().toString());
    }

    public interface CorrectionCallBack {
        void call(GuijiViewBeanjizhan data);
    }

    @Override
    public void onBackPressed() {
        savaData();
        finish();


    }
}
