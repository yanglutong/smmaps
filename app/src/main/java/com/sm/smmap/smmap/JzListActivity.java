package com.sm.smmap.smmap;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sm.smmap.smmap.Adapter.JzListAdapter;
import com.sm.smmap.smmap.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerGuijiView;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerJZ;
import com.sm.smmap.smmap.Utils.MyToast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.sm.smmap.smmap.MainActivity.mylag;

public class JzListActivity extends FragmentActivity implements View.OnClickListener {
    View finsh;
    RecyclerView ry_ta;
    DBManagerJZ dbManagerJZ;
    JzListAdapter jzListAdapter;
    LinearLayout lltv, ll;
    JzListAdapter.JzListCallBack callBack = new JzListAdapter.JzListCallBack() {
        @Override
        public void call() {
            jzListAdapter.notifyDataSetChanged();
            setAdapter();
        }

        @Override
        public void callDele(int id) {
            try {
                int i = dbManagerJZ.deleteGuanyu(String.valueOf(id));
                Log.d("nzq", "callDele: " + i);
                if (i == 1) {
//                    Toast.makeText(JzListActivity.this, "删除成功", Toast.LENGTH_LONG).show();
                    MyToast.showToast("删除成功");
//                    jzListAdapter.notifyDataSetChanged();

                    setAdapter();
                } else {
//                    Toast.makeText(JzListActivity.this, "删除失败", Toast.LENGTH_LONG).show();
                    MyToast.showToast("删除失败");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void showCALL(int id, String lat, String lon) {
            Intent intent = new Intent(JzListActivity.this, MainActivity.class);
            intent.putExtra("id", id + "");
            intent.putExtra("lon", lon);
            intent.putExtra("lat", lat);
            setResult(11, intent);
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_jz_list);
        setStatBar();
        findViews();

        try {
            dbManagerJZ = new DBManagerJZ(JzListActivity.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setAdapter();


    }

    private void setAdapter() {
        try {
            List<GuijiViewBeanjizhan> guijiViewBeanjizhans = dbManagerJZ.guijiViewBeans();//查询数据库已保存的基站数据
            Log.d("nzq", "onCreate: " + "guijiViewBeanjizhans" + guijiViewBeanjizhans);
            if (guijiViewBeanjizhans.size() > 0) {
                List<Integer> listnum = new ArrayList<>();
                for (int i = 1; i < guijiViewBeanjizhans.size() + 1; i++) {
                    listnum.add(i);
                }
                jzListAdapter = new JzListAdapter(JzListActivity.this, guijiViewBeanjizhans, mylag, dbManagerJZ, callBack,listnum);
                ry_ta.setAdapter(jzListAdapter);
                ll.setVisibility(View.VISIBLE);
                lltv.setVisibility(View.GONE);
            } else {
                List<Integer> listnum = new ArrayList<>();
                for (int i = 1; i < guijiViewBeanjizhans.size() + 1; i++) {
                    listnum.add(i);
                }
                //没有基站数据
                jzListAdapter = new JzListAdapter(JzListActivity.this, guijiViewBeanjizhans, mylag, dbManagerJZ, callBack,listnum);
                ry_ta.setAdapter(jzListAdapter);
                ll.setVisibility(View.GONE);
                lltv.setVisibility(View.VISIBLE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findViews() {
        finsh = findViewById(R.id.finsh);
        finsh.setOnClickListener(this);
        ry_ta = findViewById(R.id.ry_ta);
        //设置RecycleView的布局方式，这里是线性布局，默认垂直
        ry_ta.setLayoutManager(new LinearLayoutManager(this));
        lltv = findViewById(R.id.lltv);//暂无数据
        ll = findViewById(R.id.lltv);
        ll.setVisibility(View.VISIBLE);
        lltv.setVisibility(View.GONE);
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
    public void onBackPressed() {//重写返回键方法
        Intent intent = new Intent(JzListActivity.this, MainActivity.class);
        intent.putExtra("JzListActivity", "0");
        setResult(12, intent);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finsh:
                Intent intent = new Intent(JzListActivity.this, MainActivity.class);
                intent.putExtra("JzListActivity", "0");
                setResult(12, intent);
                finish();
                break;
        }
    }
}
