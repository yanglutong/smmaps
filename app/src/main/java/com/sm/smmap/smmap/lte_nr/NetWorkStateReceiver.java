/*
package com.sm.smmap.smmap.lte_nr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityNr;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoNr;
import android.telephony.CellLocation;
import android.telephony.CellSignalStrengthNr;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sm.smmap.smmap.R;

import java.util.ArrayList;
import java.util.List;

import static com.sm.smmap.smmap.Utils.DtUtils.getBand;

*/
/**
 * Created by Carson_Ho on 16/10/31.
 *//*

public class NetWorkStateReceiver extends BroadcastReceiver {
    private ArrayList<To4GBean> array4GList=new ArrayList<>();
    private String operator;
    private RecyclerView recyclerView;
    private TextView tv_rssl;
    private TextView tv_rsrp;
    private TextView tv_rsrq;
    private TextView tv_ssRSRP;
    private TextView tv_ssRSRQ;
    private TextView tv_ssSinr;
    private int band;
    private TelephonyManager tm;
    Handler handler1 = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ArrayList<To5GBean> string = (ArrayList<To5GBean>) msg.obj;
                    if (string.size() > 1) {
                        if(operator!=null){
                            if("CUCC".equals(operator)){
                                iv_log.setImageResource(R.drawable.cucc);
                            }
                            if("CTCC".equals(operator)){
                                iv_log.setImageResource(R.drawable.ctcc);
                            }
                            if("CMCC".equals(operator)){
                                iv_log.setImageResource(R.drawable.cmcc);
                            }
                        }
                        Log.i("杨路通", "handleMessage: " + string.get(0).getPCI());
                        tv_rssl.setText(string.get(1).getSINR() + "");
                        tv_rsrp.setText(string.get(1).getRSRP() + "");
                        tv_rsrq.setText(string.get(1).getRSRQ() + "");
                        tv_ssRSRP.setText(string.get(1).getRSRP() + "");
                        tv_ssRSRQ.setText(string.get(1).getRSRQ() + "");
                        tv_ssSinr.setText(string.get(1).getSINR() + "");

                        tv_mobile.setText("NR");
                        tv_mobile2.setText("NR");
                        tv_operator.setText(operator);
                        tv_mcc.setText(mccString);
                        tv_mnc.setText(mncString);
                        tv_cell_type.setText("NR");
                        tv_cell_type2.setText("小区类型 NR");
                        tv_pci.setText(string.get(1).getPCI());
                        tv_ci.setText(string.get(1).getCID());
                        tv_arfcn.setText(string.get(1).getNR_ARFCN());
                        tv_bands.setText(string.get(1).getBAND()+" TDD");

                        tv_name.setText(name);
                        if (string.get(1).getLac() > 0) {
                            tv_lacTo.setVisibility(View.VISIBLE);
                            tv_pscTo.setVisibility(View.VISIBLE);
                            tv_lac.setVisibility(View.VISIBLE);
                            tv_psc.setVisibility(View.VISIBLE);
                            tv_view.setVisibility(View.VISIBLE);
                            tv_psc.setText(string.get(1).getPsc()+"");
                            tv_lac.setText(string.get(1).getLac()+"");
                        } else {
                            tv_lacTo.setVisibility(View.GONE);
                            tv_pscTo.setVisibility(View.GONE);
                            tv_lac.setVisibility(View.GONE);
                            tv_psc.setVisibility(View.GONE);
                            tv_view.setVisibility(View.GONE);
                        }
                    }
                    recyclerView.setAdapter(new My5GAdapter(list, BaseMsgActivity.mainActivity));
                    break;
            }
            super.handleMessage(msg);
        }
    };
    private int lac;
    private int psc;
    //recycler的集合
    private ArrayList<To5GBean> list = new ArrayList<>();
    private String type;
    private String subtypeName;
    private Runnable runnable;
    @SuppressLint("MissingPermission")
    private List<CellInfo> allCellInfo;
    private My5GAdapter my5GAdapter;
    private TextView tv_psc;
    private TextView tv_lac;
    private TextView tv_mobile;
    private TextView tv_operator;
    private TextView tv_mcc;
    private TextView tv_mnc;
    private TextView tv_mobile2;
    private TextView tv_cell_type;
    private TextView tv_cell_type2;
    private TextView tv_pci;
    private TextView tv_ci;
    private TextView tv_arfcn;
    private TextView tv_bands;
    private String mccString;
    private String mncString;
    private String cid;
    private TextView tv_pscTo;
    private TextView tv_lacTo;
    private View tv_view;
    private ImageView iv_log;
    private TextView tv_name;
    private String name;
    private LinearLayout linear_4G;
    private LinearLayout linear_5G;
    private int lteTac;
    private int lteCi;
    private String lteMccString;
    private String lteMncString;
    private int lteRsrp;
    private int lteRsrq;
    private int lteRssi;
    private int lteRssnr;
    private int lteEarfac;
    private int ltePci;
    private String lteBand;
    private Runnable runnable4G;
    private Handler handler4G=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(msg.what==2){
                ArrayList<To4GBean> list= (ArrayList<To4GBean>) msg.obj;
                Log.i("杨路通", "handleMessage: "+list.size());
                if(list.size()>0){
                    if(list.get(0).getOperator()!=null){
                        if("CUCC".equals(list.get(0).getOperator())){
                            iv_log.setImageResource(R.drawable.cucc);
                        }
                        if("CTCC".equals(list.get(0).getOperator())){
                            iv_log.setImageResource(R.drawable.ctcc);
                        }
                        if("CMCC".equals(list.get(0).getOperator())){
                            iv_log.setImageResource(R.drawable.cmcc);
                        }
                    }
                    tv_mobile.setText("LTE");
                    tvLTE_lte.setText("LTE");
                    tvLTE_lte1.setText("LTE");
                    tvLTE_tac.setText(list.get(0).getLteTac());
                    tvLTE_pci.setText(list.get(0).getLtePci());
                    tvLTE_eci.setText(list.get(0).getLteCi());
                    tvLTE_earfcn.setText(list.get(0).getLteEarfac());
                    tvLTE_band.setText(list.get(0).getLteBand()+" FDD");
                    tv_cell_type.setText("LTE");
                    tv_cell_type2.setText("小区类型 LTE");
                    tv_name.setText(name);
                    tv_operator.setText(list.get(0).getOperator());
                    tv_mcc.setText(list.get(0).getLteMccString());
                    tv_mnc.setText(list.get(0).getLteMncString());


                    tv_rssl.setText(list.get(0).getLteRssi());
                    tv_rsrp.setText(list.get(0).getLteRsrp());
                    tv_rsrq.setText(list.get(0).getLteRsrq());
                    tv_ssSinr.setText("---");
                    tv_ssRSRQ.setText("---");
                    tv_ssRSRP.setText("---");
                }

            }
            super.handleMessage(msg);
        }
    };
    private TextView tvLTE_band;
    private TextView tvLTE_earfcn;
    private TextView tvLTE_lte;
    private TextView tvLTE_eci;
    private TextView tvLTE_pci;
    private TextView tvLTE_tac;
    private TextView tvLTE_lte1;


    @Override
    public void onReceive(Context context, Intent intent) {
        findView();
        System.out.println("网络状态发生变化");
        //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Toast.makeText(context, "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                Toast.makeText(context, "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Toast.makeText(context, "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
            }
        } else {
            //这里的就不写了，前面有写，大同小异
            System.out.println("API level 大于21");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();

            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
            */
/*//*
/    networkInfo.isConnected() 此方法判断当前是否有网络连接
            sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());*//*

                type = networkInfo.getTypeName();
                subtypeName = networkInfo.getSubtypeName();
                Log.i("杨路通", "type:    " + type + "      subtypeName :  " + subtypeName);

            }
            if ("MOBILE".equals(type)) {
                if ("NR".equals(subtypeName)) {
                    linear_4G.setVisibility(View.GONE);
                    linear_5G.setVisibility(View.VISIBLE);
                    */
/*
                    4G的资源释放
                    * *//*

                    if(array4GList.size()>0||handler4G!=null){
                        array4GList.clear();
                        handler4G.removeCallbacks(runnable4G);
                    }
                    */
/*
                    4G的资源释放
                    * *//*


                    get5GDemo();
                    recyclerView.setLayoutManager(new LinearLayoutManager(BaseMsgActivity.mainActivity));
                    my5GAdapter = new My5GAdapter(list, BaseMsgActivity.mainActivity);
                    recyclerView.setAdapter(my5GAdapter);
                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            //获取5G
                            get5GDemo();
                            Message message = new Message();
                            message.what = 1;
                            message.obj = list;
                            handler1.sendMessage(message);
                            handler1.postDelayed(this, 2000);
                        }
                    };
                    handler1.post(runnable);
                    Toast.makeText(context, "当前网络为5G", Toast.LENGTH_SHORT).show();
                } else {
                    if ("LTE".equals(subtypeName)) {
                        linear_5G.setVisibility(View.GONE);
                        linear_4G.setVisibility(View.VISIBLE);
                        */
/*5G资源释放*//*

                        list.clear();
                        if (my5GAdapter != null) {
                            my5GAdapter.notifyDataSetChanged();
                        }
                        if(handler1!=null){
                            handler1.removeCallbacks(runnable);
                        }
                        */
/*5G资源释放*//*



                        //获取4G信息
                        get4GDemo();
                        runnable4G = new Runnable() {
                            @Override
                            public void run() {
                                //获取4G
                                get4GDemo();
                                Message message = new Message();
                                message.what = 2;
                                message.obj = array4GList;
                                handler4G.sendMessage(message);
                                handler4G.postDelayed(this, 2000);
                            }
                        };
                        handler4G.post(runnable4G);
                        Toast.makeText(context, "当前网络为4G", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            if ("WIFI".equals(type)) {

                Toast.makeText(context, "当前网络为WIFI", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //9264

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void get5GDemo() {
        list.clear();
        band = 0;
        tm = (TelephonyManager) BaseMsgActivity.mainActivity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(BaseMsgActivity.mainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        List<CellInfo> allCellInfo = tm.getAllCellInfo();
        getData(allCellInfo);
    }


    private void getData(List<CellInfo> allCellInfo) {
        addMi();//先添加一条假数据
        if (allCellInfo != null && allCellInfo.size() > 0) {
            for (int i = 0; i < allCellInfo.size(); i++) {
                CellInfo info = allCellInfo.get(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    //5G
                    if (info instanceof CellInfoNr) {
                        Log.i("杨路通", "5G: ");
                        //获取5G数据管理
                        CellIdentityNr nr = (CellIdentityNr) ((CellInfoNr) info).getCellIdentity();
                        CellSignalStrengthNr nrStrength = (CellSignalStrengthNr) ((CellInfoNr) info)
                                .getCellSignalStrength();

                        mccString = nr.getMccString();
                        mncString = nr.getMncString();
                        int pci = nr.getPci();
                        long ci = nr.getNci();
                        String cid = String.valueOf(ci);
                        int nrarfcn = nr.getNrarfcn();
                        int ssRsrp = nrStrength.getSsRsrp();
                        int ssRsrq = nrStrength.getSsRsrq();
                        int ssSinr = nrStrength.getSsSinr();

                        //判断是什么频点
                        if (rangeInDefined(nrarfcn, 151600, 160600)) {
                            band = 28;
                        }
                        if (rangeInDefined(nrarfcn, 499200, 537999)) {
                            band = 41;
                        }
                        if (rangeInDefined(nrarfcn, 620000, 653333)) {
                            band = 78;
                        }
                        if (rangeInDefined(nrarfcn, 693333, 733333)) {
                            band = 79;
                        }




                        //5G情况下判断是否为联通移动电信
                        TelephonyManager tel = (TelephonyManager) BaseMsgActivity.mainActivity.getSystemService(Context.TELEPHONY_SERVICE);
                        @SuppressLint("MissingPermission") CellLocation cel = tel.getCellLocation();
                        GsmCellLocation gsmCellLocation = (GsmCellLocation) cel;
                        if(gsmCellLocation!=null){
                             lac = gsmCellLocation.getLac();
                             psc = gsmCellLocation.getPsc();
                        }

                        String operator = tel.getSimOperator();
                        if(operator!=null){
                            if(operator.equals("46000") || operator.equals("46002") || operator.equals("46004") || operator.equals("46007")){
                                Log.i("杨路通", "中国移动: ");
                                this.operator="CMCC";
                                name="中国移动";
                            }else if(operator.equals("46001") || operator.equals("46006") || operator.equals("46009")){
                                Log.i("杨路通", "中国联通: ");
                                this.operator="CUCC";
                                name="中国联通";
                            }else if(operator.equals("46003") || operator.equals("46005") || operator.equals("46011")){
                                Log.i("杨路通", "中国电信: ");
                                this.operator="CTCC";
                                name="中国电信";
                            }
                        }


                        Log.i("杨路通", "mccString: "+ mccString);
                        Log.i("杨路通", "mncString: "+ mncString);
                        Log.i("杨路通", "NR-PCI: "+pci);
                        Log.i("杨路通", "NR-CI: "+ cid);
                        Log.i("杨路通", "NR-ARFCN: "+nrarfcn);
                        Log.i("杨路通", "NR-BAND: "+band);

                        Log.i("杨路通", "ssRsrp: "+ssRsrp);
                        Log.i("杨路通", "ssRsrq: "+ssRsrq);
                        Log.i("杨路通", "ssSinr: "+ssSinr);
                        Log.i("杨路通", "lac: "+lac);
                        Log.i("杨路通", "psc: "+psc);
                        list.add(new To5GBean(band+"", nrarfcn+"", pci+"",
                                ssRsrp+"", ssRsrq+"", ssSinr+"",cid,lac,psc));
                    }
                }
            }
        }
    }


    private void findView() {
        */
/*
        5G
        * *//*

        linear_5G = BaseMsgActivity.mainActivity.findViewById(R.id.Linear_5G);
        recyclerView = BaseMsgActivity.mainActivity.findViewById(R.id.recycler);
        tv_rssl = BaseMsgActivity.mainActivity.findViewById(R.id.tv_rssl);
        tv_rsrp = BaseMsgActivity.mainActivity.findViewById(R.id.tv_rsrp);
        tv_rsrq = BaseMsgActivity.mainActivity.findViewById(R.id.tv_rsrq);
        tv_ssRSRP = BaseMsgActivity.mainActivity.findViewById(R.id.tv_ssRSRP);
        tv_ssRSRQ = BaseMsgActivity.mainActivity.findViewById(R.id.tv_ssRSRQ);
        tv_ssSinr = BaseMsgActivity.mainActivity.findViewById(R.id.tv_ssSinr);


        tv_mobile = BaseMsgActivity.mainActivity.findViewById(R.id.tv_MOBILE);
        tv_operator = BaseMsgActivity.mainActivity.findViewById(R.id.tv_operator);
        tv_mcc = BaseMsgActivity.mainActivity.findViewById(R.id.tv_mcc);
        tv_mnc = BaseMsgActivity.mainActivity.findViewById(R.id.tv_mnc);
        tv_mobile2 = BaseMsgActivity.mainActivity.findViewById(R.id.tv_MOBILE2);
        tv_cell_type = BaseMsgActivity.mainActivity.findViewById(R.id.tv_Cell_type);
        tv_cell_type2 = BaseMsgActivity.mainActivity.findViewById(R.id.tv_Cell_type2);
        tv_pci = BaseMsgActivity.mainActivity.findViewById(R.id.tv_pci);
        tv_ci = BaseMsgActivity.mainActivity.findViewById(R.id.tv_ci);
        tv_arfcn = BaseMsgActivity.mainActivity.findViewById(R.id.tv_arfcn);
        tv_bands = BaseMsgActivity.mainActivity.findViewById(R.id.tv_bands);




        tv_psc = BaseMsgActivity.mainActivity.findViewById(R.id.tv_psc);
        tv_lac = BaseMsgActivity.mainActivity.findViewById(R.id.tv_lac);
        tv_pscTo = BaseMsgActivity.mainActivity.findViewById(R.id.tv_pscTo);
        tv_lacTo = BaseMsgActivity.mainActivity.findViewById(R.id.tv_lacTo);
        tv_view = BaseMsgActivity.mainActivity.findViewById(R.id.tv_view);


        iv_log = BaseMsgActivity.mainActivity.findViewById(R.id.iv_log);
        tv_name = BaseMsgActivity.mainActivity.findViewById(R.id.tv_Name);
        */
/*fdsf
        5G
        * *//*



        */
/*4G*//*

        linear_4G = BaseMsgActivity.mainActivity.findViewById(R.id.Linear_4G);
        tvLTE_band = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_band);
        tvLTE_earfcn = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_EARFCN);
        tvLTE_lte = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_lte);
        tvLTE_eci = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_eci);
        tvLTE_pci = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_pci);
        tvLTE_tac = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_tac);
        tvLTE_lte1 = BaseMsgActivity.mainActivity.findViewById(R.id.tvLTE_LTE);

        */
/*4G*//*


    }
    private void addMi() {
        list.add(new To5GBean("BAND", "NR ARFCN", "PCI", "RSRP", "RSRQ", "SINR",null,0,0));
    }
    */
/*
       用Math进行比较0~10 5是否在此区间
   * *//*

    public static boolean rangeInDefined(int current, int min, int max)
    {
        return Math.max(min, current) == Math.min(current, max);
    }
*/
/*
//    调用接口让activity获取到广播接收器的数据
    Msg msg;
    interface Msg{
        void getMsg(String name);
        void getMsg(Handler handler1, Handler handler4G, ArrayList<To5GBean> list, ArrayList<To4GBean> array4GList, Runnable runnable, Runnable runnable4G);
    }

    public void setMsg(Msg msg) {
        this.msg = msg;
    }*//*

   @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
   private void get4GDemo() {
       array4GList.clear();
       TelephonyManager manager = (TelephonyManager) BaseMsgActivity.mainActivity.getSystemService(Context.TELEPHONY_SERVICE);
       @SuppressLint("MissingPermission")
       List<CellInfo> cellInfoList = manager.getAllCellInfo();
       for (CellInfo info : cellInfoList) {
           Log.i("杨路通", "get4GDemo: "+info.toString());

           if (info.toString().contains("CellInfoLte")) {
               @SuppressLint({"NewApi", "LocalSuppress"}) CellInfoLte cellInfoLte = (CellInfoLte) info;
               @SuppressLint({"NewApi", "LocalSuppress"}) CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();

               lteTac = cellIdentityLte.getTac();
               lteCi = cellIdentityLte.getCi();
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                   lteMccString = cellIdentityLte.getMccString();
                   Log.i("杨路通", "get4GDemo: mccString  "+ lteMccString);
               }
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                   lteMncString = cellIdentityLte.getMncString();
                   Log.i("杨路通", "get4GDemo: mncString  "+ lteMncString);
               }
               Log.i("杨路通", "get4GDemo: tac  "+ lteTac);
               Log.i("杨路通", "get4GDemo: ci  "+ lteCi);
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   lteRsrp = cellInfoLte.getCellSignalStrength().getRsrp();
                   Log.i("杨路通", "get4GDemo: rsrp  "+ lteRsrp);
               }
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   lteRsrq = cellInfoLte.getCellSignalStrength().getRsrq();
                   Log.i("杨路通", "get4GDemo: rsrq "+ lteRsrq);
               }
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                   lteRssi = cellInfoLte.getCellSignalStrength().getRssi();
                   Log.i("杨路通", "get4GDemo: rssi    "+ lteRssi);
               }
               if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                   lteRssnr = cellInfoLte.getCellSignalStrength().getRssnr();
                   Log.i("杨路通", "get4GDemo: rssnr    "+ lteRssnr);
               }
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                   lteEarfac = cellIdentityLte.getEarfcn();
                   Log.i("杨路通", "get4GDemo: earfcn"+ lteEarfac);
               }
               ltePci = cellIdentityLte.getPci();

               Log.i("杨路通", "get4GDemo: pci"+ ltePci);
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                   String mobileNetworkOperator = cellIdentityLte.getMobileNetworkOperator();
                   Log.i("杨路通", "get4GDemo: mobileNetworkOperator"+mobileNetworkOperator);
               }
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                   lteBand = getBand(cellIdentityLte.getEarfcn());
                   Log.i("杨路通", "get4GDemo: band"+ lteBand);
               }

               //判断是否为联通移动电信
               String operator = manager.getSimOperator();
               String ope=null;
               if(operator!=null){
                   if(operator.equals("46000") || operator.equals("46002") || operator.equals("46004") || operator.equals("46007")){
//中国移动
                       Log.i("杨路通", "中国移动: ");
                       ope="CMCC";
                       name="中国移动";
                   }else if(operator.equals("46001") || operator.equals("46006") || operator.equals("46009")){
//中国联通
                       Log.i("杨路通", "中国联通: ");
                       ope="CUCC";
                       name="中国联通";
                   }else if(operator.equals("46003") || operator.equals("46005") || operator.equals("46011")){
//中国电信
                       Log.i("杨路通", "中国电信: ");
                       ope="CTCC";
                       name="中国电信";
                   }
               }
                //将获取到的数据每一次都存到集合里
                array4GList.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
                        lteCi+"",lteEarfac+"",lteBand+"",lteRssi+"",
                        lteRsrp+"", lteRsrq+"", lteRssnr+"",ope));
           }
       }
   }
}
*/
