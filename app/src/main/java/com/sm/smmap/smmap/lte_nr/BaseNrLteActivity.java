package com.sm.smmap.smmap.lte_nr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.google.gson.TypeAdapter;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Utils.MyToast;
import com.sm.smmap.smmap.Utils.MyViewPager;

import java.util.ArrayList;

public class BaseNrLteActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nr_lte);
//        setStatBar();
        if(getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }


        TabLayout mTab = findViewById(R.id.tab);
        MyViewPager mVp = findViewById(R.id.vp);
        ImageView finsh = findViewById(R.id.finsh);
        finsh.setOnClickListener(v -> {
            finish();
        });

            setTab(mTab, mVp);
    }



    /*双卡 第一条为4G*/
    private void setTab(TabLayout mTab, MyViewPager mVp) {
        //设置Tablayout与ViewPager同步
        mTab.setupWithViewPager(mVp);


        //这里的TabLayout数据较多，用集合存储
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BaseNrFragment.newInstance(0));
        fragments.add(BaseNrFragment.newInstance(1));
        //定义适配器与绑定
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mVp.setAdapter(adapter);
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
}