package com.sm.smmap.smmap.lte_nr;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import android.telephony.SubscriptionManager;
import android.util.Log;
import android.view.View;

import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.sm.smmap.smmap.R;

import com.sm.smmap.smmap.Utils.DepthPageTransformer;
import com.sm.smmap.smmap.Utils.MyViewPager;
import com.sm.smmap.smmap.Utils.ViewPagerScroller;

import java.util.ArrayList;

public class BaseNrLteActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvSim1;
    private TextView tvSim2;
    private MyViewPager mVp;
    private TabLayout mTab;

    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_nr_lte);
        Log.e("TAG", "activity_base_nr_lte: " + SubscriptionManager.from(this).getActiveSubscriptionInfoCount());
//        setStatBar();
        //获取单卡槽双卡槽
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        mVp = findViewById(R.id.vp);
        mTab = findViewById(R.id.tab);
        ImageView finsh = findViewById(R.id.finsh);
        tvSim1 = findViewById(R.id.tvSim1);
        tvSim2 = findViewById(R.id.tvSim2);
        tvSim1.setOnClickListener(this);
        tvSim2.setOnClickListener(this);

        finsh.setOnClickListener(v -> {
            finish();
        });
        setTab();
    }

    /*双卡 第一条为4G*/
    private void setTab() {
//        mVp.setPageTransformer(true,new DepthPageTransformer());显示viewpager的切换动画
        //这里的TabLayout数据较多，用集合存储
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(BaseNrLteFragment.newInstance(0));
        fragments.add(BaseNrLteFragment.newInstance(1));
        //定义适配器与绑定
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), fragments);
        mVp.setAdapter(adapter);
        mTab.setupWithViewPager(mVp);
        mTab.setSelectedTabIndicatorHeight(0);//取消显示tab下划线
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    tvSim1.setBackground(getResources().getDrawable(R.drawable.base_msg_tab));
                    tvSim1.setTextColor(Color.parseColor("#01E6FC"));

                    tvSim2.setBackground(null);
                    tvSim2.setTextColor(Color.parseColor("#000000"));


                }else if(position == 1){
                    tvSim2.setBackground(getResources().getDrawable(R.drawable.base_msg_tab));
                    tvSim2.setTextColor(Color.parseColor("#01E6FC"));

                    tvSim1.setBackground(null);
                    tvSim1.setTextColor(Color.parseColor("#000000"));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("TAG", "onActivityResult: " + "回来了");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvSim1:
               tvSim1.setBackground(getResources().getDrawable(R.drawable.base_msg_tab));
               tvSim1.setTextColor(Color.parseColor("#01E6FC"));

               tvSim2.setBackground(null);
               tvSim2.setTextColor(Color.parseColor("#000000"));

                mVp.setCurrentItem(0,true);
                break;
            case R.id.tvSim2:
                tvSim2.setBackground(getResources().getDrawable(R.drawable.base_msg_tab));
                tvSim2.setTextColor(Color.parseColor("#01E6FC"));

                tvSim1.setBackground(null);
                tvSim1.setTextColor(Color.parseColor("#000000"));

                mVp.setCurrentItem(1,true);


                break;
        }
    }
}