package com.sm.smmap.smmap.map;

import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.sm.smmap.smmap.MainActivity;

public class MyLocationListener extends BDAbstractLocationListener {
    private Context context;
    private  BaiduMap mBaiduMap;
    private  MapView mMapView;

    public MyLocationListener (MapView mMapView, BaiduMap mBaiduMap){

        this.mMapView=mMapView;
        this.mBaiduMap=mBaiduMap;
    }

    public MyLocationListener(MainActivity mainActivity) {
        this.context=mainActivity;
    }

    @Override
    public void onReceiveLocation(BDLocation location) {
        //mapView 销毁后不在处理新接收的位置
        if (location == null || mMapView == null){
            return;
        }
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);
    }
}
