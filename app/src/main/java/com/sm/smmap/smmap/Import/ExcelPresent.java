package com.sm.smmap.smmap.Import;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.blankj.utilcode.util.ToastUtils;
import com.sm.smmap.smmap.Adapter.CorrectionAdapter;
import com.sm.smmap.smmap.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerJZ;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Retrofit.DataBean;
import com.sm.smmap.smmap.Retrofit.RetrofitFactory;
import com.sm.smmap.smmap.Utils.BillObject;
import com.sm.smmap.smmap.Utils.MyUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class ExcelPresent implements ExcelView.ExcelPresenter {
 public    ExcelView.View view;
    int type = 0;

    public ExcelPresent(@NonNull ExcelView.View view) {
//        this.models = models;
        this.view = view;
        view.setPresenter(this);
    }
    public static List<Integer>listNum=new ArrayList<>();
    @Override
    public void finish() {
        view.finishs();
    }

    @Override
    public void Querys(final List<BillObject> billObjectList, final Context context) {

        final Dialog dialog2 = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_dibushow9, null);
        Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        Button bt_cancel = inflate.findViewById(R.id.bt_clear);
        //初始化控件
        RadioGroup rg_main = inflate.findViewById(R.id.rg_main);
        final RadioButton b1 = inflate.findViewById(R.id.rb_yidong);
        final RadioButton b2 = inflate.findViewById(R.id.rb_liantong);
        final RadioButton b3 = inflate.findViewById(R.id.rb_dianxin);
        b1.setChecked(true);
        type = 0;

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Log.d(TAG, "onCheckedChanged: " + i);
                if (i == b1.getId()) {
                    Log.d(TAG, "onCheckedChangedb1: " + i);
                    type = 0;
                }
                if (i == b2.getId()) {
                    Log.d(TAG, "onCheckedChangedb2: " + i);
                    type = 1;
                }
                if (i == b3.getId()) {
                    Log.d(TAG, "onCheckedChangedb2: " + i);
                    type = 11;
                }
            }
        });
        bt_adddilao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewA) {

                String key = "4283a37b42d1e381f4ffa6bf9e8ecc96";
                ExcelView.View    viewS= (ExcelView.View) view;
//                sucesNum=0;

                for (int i = 0; i < billObjectList.size(); i++) {
                    String tac = billObjectList.get(i).getTac();
                    String ci = billObjectList.get(i).getCi();
                    if (!TextUtils.isEmpty(tac) && !TextUtils.isEmpty(ci)) {
                        try {
                            RetrofitFactory.getInstence().API().GETBaseStation(type, Integer.parseInt(tac), Integer.parseInt(ci), key).enqueue(new Callback<DataBean>() {
                                @Override
                                public void onResponse(Call<DataBean> call, Response<DataBean> response) {
                                    Log.d(TAG, "bt_adddilaoonResponse: " + response.toString());
                                    DataBean dataBean = response.body();
                                    if (dataBean.getReason().equals("查询成功") && dataBean.getError_code() == 0) {
                                        Log.d("qqqqq", "onResponse: " + dataBean.getResult().getLac() + "--" + dataBean.getResult().getCi());

                                    }
                                    DBManagerJZ dbManagerJZ = null;
                                    try {
                                        dbManagerJZ = new DBManagerJZ(context);
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                    dataBean = response.body();
                                    if (dataBean.getReason().equals("查询成功") && dataBean.getError_code() == 0) {
                                        try {
                                            String latresult = dataBean.getResult().getLat();
                                            String lonresult = dataBean.getResult().getLon();
                                            LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
                                            Log.d(TAG, "dataBeanisShowjizhan: " + dataBean.getResult());
//            LatLng latLngresult = new LatLng(Double.parseDouble("38.031242"), Double.parseDouble("114.450186"));

                                            CoordinateConverter converter = new CoordinateConverter()
                                                    .from(CoordinateConverter.CoordType.GPS)
                                                    .coord(latLngresult);
                                            //转换坐标
                                            LatLng desLatLngBaidu = converter.convert();
                                            GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                                            if (jizhanFlag == 4) {
//                                                d.setSid(et_sid.getText().toString());
//                                            } else {
//                                                d.setSid("");
//                                            }
                                            d.setTypes("");
                                            d.setBand("");
                                            d.setPci("");
                                            d.setDown("");
                                            d.setId(1);
                                            d.setAddress(dataBean.getResult().getAddress());
                                            d.setCi(dataBean.getResult().getCi());
                                            d.setLac(dataBean.getResult().getLac());
                                            d.setMcc(dataBean.getResult().getMcc());
                                            d.setMnc(dataBean.getResult().getMnc());
                                            d.setRadius(dataBean.getResult().getRadius());
//                        d.setTa(et_ta.getText().toString());
//                                            Log.d(TAG, "onResponse:aalist" + list);
                                            d.setTa("1");
                                            d.setType(0);
                                            d.setLat(String.valueOf(desLatLngBaidu.latitude));
                                            d.setLon(String.valueOf(desLatLngBaidu.longitude));
                                            d.setResources("公开数据");
                                            List<GuijiViewBeanjizhan> listS = dbManagerJZ.QueryVules(dataBean.getResult().getLac(), dataBean.getResult().getCi());
                                            if (listS != null && listS.size() > 0) {
                                                Log.d("AlistSs", "onResponse: 已经存在了");
//                                                listNum.add(1);
//                                                return;
                                            } else {
                                                int i = dbManagerJZ.insertStudent(d);
                                                Log.d("AlistSs", "onResponse: 没有数据,现在插入");
//                                                listNum.add(1);
                                            }

//                                            MapStatus.Builder builder = new MapStatus.Builder();
//                                            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                            initdatas();
//                                            list.clear();
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                            Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
                                        }

                                    }
                                }

                                @Override
                                public void onFailure(Call<DataBean> call, Throwable t) {
                                    Log.d(TAG, "bt_adddilaoonFailure: " + t.getMessage());
                                }
                            });
                        } catch (Exception e) {
                            ToastUtils.showShort("异常了：" + e.getMessage().toString());
                            Log.d("wgg", "异常了：" + e.getMessage().toString());
                        }

                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ToastUtils.showShort("操作完成");
//                Log.d("AlistSs", "onClick: 成功添加"+listNum.size()+"条");
                dialog2.cancel();
                viewS.QueryShow();
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
//
//        view.QueryShow();
    }
}
