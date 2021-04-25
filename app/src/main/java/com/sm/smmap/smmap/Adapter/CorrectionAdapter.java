package com.sm.smmap.smmap.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.sm.smmap.smmap.CorrectionActivity;
import com.sm.smmap.smmap.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Retrofit.Bean.JzGetData;
import com.sm.smmap.smmap.SernnXqActivity;
import com.sm.smmap.smmap.Utils.It.IT.ScreenCallBack;

import java.util.List;

/**
 * Created by WangChang on 2016/4/1.
 */
public class CorrectionAdapter extends RecyclerView.Adapter<CorrectionAdapter.MyViewHolder> {

    private List<GuijiViewBeanjizhan> guijiViewBeanjizhans;
    private CorrectionActivity.CorrectionCallBack callBack;//MainActivity 基站巡视列表callback
    private Dialog dialog;
    private List<Integer> listnum;
    private Context context;

    public CorrectionAdapter(Context context, List<GuijiViewBeanjizhan> guijiViewBeanjizhans, List<Integer> listnum, Dialog dialog2, CorrectionActivity.CorrectionCallBack callBack) {
        this.guijiViewBeanjizhans = guijiViewBeanjizhans;
        this.context = context;
        this.listnum = listnum;
        this.dialog = dialog2;
        this.callBack = callBack;
    }

    @Override
    public CorrectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.serrn_adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.title.setText("(" + listnum.get(i) + ")" + guijiViewBeanjizhans.get(i).getAddress() + "");
//        myViewHolder.title.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onClick(View view) {
//                LatLng latLng = new LatLng(list.getData().get(i).getLatitude(), list.getData().get(i).getLongitude());
//                CoordinateConverter converter = new CoordinateConverter()
//                        .from(CoordinateConverter.CoordType.GPS)
//                        .coord(latLng);
//                //转换坐标
//                LatLng desLatLngBaidu = converter.convert();
//                Intent intent = new Intent(context, SernnXqActivity.class);
//                Bundle bundle = new Bundle();
//                //初六保留6位小数
//
//                DecimalFormat df = new DecimalFormat("#.000000");
//                String lat = df.format(desLatLngBaidu.latitude);
//                String lon = df.format(desLatLngBaidu.longitude);
//                bundle.putString("lat", lat);
//                bundle.putString("lon", lon);
//                bundle.putString("address", String.valueOf(list.getData().get(i).getAreaName()));
//                bundle.putString("tac", String.valueOf(list.getData().get(i).getTac()));
//                bundle.putString("mnc", String.valueOf(list.getData().get(i).getMnc()));
//                int eNodeBid = list.getData().get(i).getENodeBid();
//                int js = eNodeBid * 256;
//
//                int eci = js + list.getData().get(i).getAreaMark();
//                Log.d("SerrnAdapter", "onClick:eNodeBid" + eNodeBid + "----" + js + "--" + "AreMark" + list.getData().get(i).getAreaMark() + "---eci" + eci);
//                bundle.putString("eci", eci + "");
//                bundle.putString("resources", "内部数据");
//
//
//
//                intent.putExtras(bundle);
//                dialog.dismiss();
////                context.startActivity(intent);
//                JzGetData.DataBean dataBean = new JzGetData.DataBean();
//                dataBean.setLatitude(list.getData().get(i).getLatitude());
//                dataBean.setLongitude(list.getData().get(i).getLongitude());
//                dataBean.setAreaName(list.getData().get(i).getAreaName());
//                dataBean.setTac(list.getData().get(i).getTac());
//                dataBean.setMnc(list.getData().get(i).getMnc());
//                dataBean.setAreaMark(list.getData().get(i).getAreaMark());
//                dataBean.setENodeBid(list.getData().get(i).getENodeBid());
//
//                dataBean.setBand(list.getData().get(i).getBand());
//                dataBean.setPci(list.getData().get(i).getPci());
//                dataBean.setDownFrequencyPoint(list.getData().get(i).getDownFrequencyPoint());
//                dataBean.setType(list.getData().get(i).getType());
        myViewHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callBack.call(guijiViewBeanjizhans.get(i));
            }
        });

//            }
//        });
    }


    @Override
    public int getItemCount() {
        return guijiViewBeanjizhans.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);

        }
    }


}
