package com.sm.smmap.smmap.lte_nr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Utils.GCJ02ToWGS84Util;
import com.sm.smmap.smmap.Utils.MyToast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.sm.smmap.smmap.Utils.DtUtils.getBand;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment3 extends Fragment {

    private TextView tvLTE_band;
    private TextView tvLTE_earfcn;
    private TextView tvLTE_lte;
    private TextView tvLTE_eci;
    private TextView tvLTE_pci;
    private TextView tvLTE_tac;
    private TextView tvLTE_lte1;
    private My4GAdapter my4GAdapter;
    private RecyclerView recyclerView4G;
    private RecyclerView recyclerView5G;
    private LinearLayout my4GRecycler;
    private LinearLayout my5GRecycler;
    private ConstraintLayout cons;
    private String formatLon;
    private String formatLat;
    public Runnable run;

    //定义集合 获取是卡槽1还是卡槽2
    private ArrayList<String> kaList=new ArrayList<>();
    private int lac;
    private int psc;
    //当前小区
    private ArrayList<To5GBean> list = new ArrayList<>();
    //邻小区
    private ArrayList<To5GBean> listLin = new ArrayList<>();
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
    private TextView tv_pscTo;
    private TextView tv_lacTo;
    private View tv_view;
    private View tv_view2;
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
    private TextView tv_rssl;
    private TextView tv_rsrp;
    private TextView tv_rsrq;
    private TextView tv_ssRSRP;
    private TextView tv_ssRSRQ;
    private TextView tv_ssSinr;
    private int band;
    private TelephonyManager tm;
    public static Context mainActivity;
    private String lon;
    private String lat;
    private TextView tv_location;

    public Handler handlerUp = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
        public void handleMessage(Message msg) {
            if(msg.what==2){
               ArrayList<String> list2= (ArrayList<String>) msg.obj;
               if(list2.get(0).equals("5G")){//判断当前1卡槽是5G
                   linear_5G.setVisibility(View.VISIBLE);
                   linear_4G.setVisibility(View.GONE);
                   my5GRecycler.setVisibility(View.VISIBLE);
                   my4GRecycler.setVisibility(View.GONE);
                   if (list.size() > 0) {
                       if (list.get(0).getMnc() != null) {
                           if (Integer.parseInt(list.get(0).getMnc()) == 01) {
                               iv_log.setImageResource(R.drawable.cucc);
                               tv_operator.setText("CUCC");
                               tv_mcc.setText("460");
                               tv_name.setText("中国联通");
                           }
                           if (Integer.parseInt(list.get(0).getMnc()) == 11) {
                               iv_log.setImageResource(R.drawable.ctcc);
                               tv_operator.setText("CTCC");
                               tv_mcc.setText("460");
                               tv_name.setText("中国电信");
                           }
                           if (Integer.parseInt(list.get(0).getMnc()) == 00) {
                               iv_log.setImageResource(R.drawable.cmcc);
                               tv_operator.setText("CMCC");
                               tv_mcc.setText("460");
                               tv_name.setText("中国移动");
                           }
                       }
                       tv_rssl.setText(list.get(0).getSINR() + "");
                       tv_rsrp.setText(list.get(0).getRSRP() + "");
                       tv_rsrq.setText(list.get(0).getRSRQ() + "");
                       tv_ssRSRP.setText(list.get(0).getRSRP() + "");
                       tv_ssRSRQ.setText(list.get(0).getRSRQ() + "");
                       tv_ssSinr.setText(list.get(0).getSINR() + "");

                       tv_mobile.setText(list.get(0).getNr());
                       tv_mobile2.setText(list.get(0).getNr());

                       tv_mnc.setText(list.get(0).getMnc());
                       tv_cell_type.setText(list.get(0).getNr());
                       tv_cell_type2.setText(list.get(0).getNrType());
                       tv_pci.setText(list.get(0).getPCI());
                       tv_ci.setText(list.get(0).getCID());
                       tv_arfcn.setText(list.get(0).getNR_ARFCN());
                       tv_bands.setText(list.get(0).getBAND() + "");

                       if (list.get(0).getLac() > 0) {
                           tv_lacTo.setVisibility(View.VISIBLE);
                           tv_pscTo.setVisibility(View.VISIBLE);
                           tv_lac.setVisibility(View.VISIBLE);
                           tv_psc.setVisibility(View.VISIBLE);
                           tv_view.setVisibility(View.VISIBLE);
                           tv_view2.setVisibility(View.VISIBLE);
                           tv_psc.setText(list.get(0).getPsc() + "");
                           tv_lac.setText(list.get(0).getLac() + "");
                       } else {
                           tv_lacTo.setVisibility(View.GONE);
                           tv_pscTo.setVisibility(View.GONE);
                           tv_lac.setVisibility(View.GONE);
                           tv_psc.setVisibility(View.GONE);
                           tv_view2.setVisibility(View.GONE);
                           tv_view.setVisibility(View.GONE);
                       }
                       recyclerView5G.setAdapter(new My5GAdapter(listLin, mainActivity));
                       my5GAdapter.notifyDataSetChanged();
                   }
               }else if(list2.get(list2.size()-1).equals("5G")){//判断当前2卡槽是5G
                   linear_5G.setVisibility(View.VISIBLE);
                   linear_4G.setVisibility(View.GONE);
                   my5GRecycler.setVisibility(View.VISIBLE);
                   my4GRecycler.setVisibility(View.GONE);
                   if(list.get(0).getMnc()!=null){
                       if(Integer.parseInt(list.get(0).getMnc())==01){
                           iv_log.setImageResource(R.drawable.cucc);
                           tv_operator.setText("CUCC");
                           tv_mcc.setText("460");
                           tv_name.setText("中国联通");
                       }
                       if(Integer.parseInt(list.get(0).getMnc())==11){
                           iv_log.setImageResource(R.drawable.ctcc);
                           tv_operator.setText("CTCC");
                           tv_mcc.setText("460");
                           tv_name.setText("中国电信");
                       }
                       if(Integer.parseInt(list.get(0).getMnc())==00){
                           iv_log.setImageResource(R.drawable.cmcc);
                           tv_operator.setText("CMCC");
                           tv_mcc.setText("460");
                           tv_name.setText("中国移动");
                       }
                   }
                   Log.i("ylt", "handleMessage: " + list.get(0).getPCI());
                   tv_rssl.setText(list.get(0).getSINR() + "");
                   tv_rsrp.setText(list.get(0).getRSRP() + "");
                   tv_rsrq.setText(list.get(0).getRSRQ() + "");
                   tv_ssRSRP.setText(list.get(0).getRSRP() + "");
                   tv_ssRSRQ.setText(list.get(0).getRSRQ() + "");
                   tv_ssSinr.setText(list.get(0).getSINR() + "");

                   tv_mobile.setText(list.get(0).getNr());
                   tv_mobile2.setText(list.get(0).getNr());

                   tv_mnc.setText(list.get(0).getMnc());
                   tv_cell_type.setText(list.get(0).getNr());
                   tv_cell_type2.setText(list.get(0).getNrType());
                   tv_pci.setText(list.get(0).getPCI());
                   tv_ci.setText(list.get(0).getCID());
                   tv_arfcn.setText(list.get(0).getNR_ARFCN());
                   tv_bands.setText(list.get(0).getBAND()+"");

                   if (list.get(0).getLac() > 0) {
                       tv_lacTo.setVisibility(View.VISIBLE);
                       tv_pscTo.setVisibility(View.VISIBLE);
                       tv_lac.setVisibility(View.VISIBLE);
                       tv_psc.setVisibility(View.VISIBLE);
                       tv_view.setVisibility(View.VISIBLE);
                       tv_view2.setVisibility(View.VISIBLE);
                       tv_psc.setText(list.get(0).getPsc()+"");
                       tv_lac.setText(list.get(0).getLac()+"");
                   } else {
                       tv_lacTo.setVisibility(View.GONE);
                       tv_pscTo.setVisibility(View.GONE);
                       tv_lac.setVisibility(View.GONE);
                       tv_psc.setVisibility(View.GONE);
                       tv_view2.setVisibility(View.GONE);
                       tv_view.setVisibility(View.GONE);
                   }
                   recyclerView5G.setAdapter(new My5GAdapter(listLin, mainActivity));
                   my5GAdapter.notifyDataSetChanged();
               }else if(list2.get(0).equals("4G")&&list2.get(list2.size()-1).equals("4G")){//双卡都为4G
                    linear_5G.setVisibility(View.GONE);
                    linear_4G.setVisibility(View.VISIBLE);
                    my5GRecycler.setVisibility(View.GONE);
                    my4GRecycler.setVisibility(View.VISIBLE);

                    if(array4GList.size()>0){//设置获取的第一条数据
                        if(array4GList.get(0).getLteMncString()!=null){
                            if(Integer.parseInt(array4GList.get(0).getLteMncString())==01){
                                iv_log.setImageResource(R.drawable.cucc);
                                tv_operator.setText("CUCC");
                                tv_mcc.setText("460");
                                tv_name.setText("中国联通");
                            }
                            if(Integer.parseInt(array4GList.get(0).getLteMncString())==11){
                                iv_log.setImageResource(R.drawable.ctcc);
                                tv_operator.setText("CTCC");
                                tv_mcc.setText("460");
                                tv_name.setText("中国电信");
                            }
                            if(Integer.parseInt(array4GList.get(0).getLteMncString())==00){
                                iv_log.setImageResource(R.drawable.cmcc);
                                tv_operator.setText("CMCC");
                                tv_mcc.setText("460");
                                tv_name.setText("中国移动");
                            }
                        }
                        tv_mobile.setText(array4GList.get(0).getLte());
                        tvLTE_lte.setText(array4GList.get(0).getLte());
                        tvLTE_lte1.setText(array4GList.get(0).getLte());
                        tvLTE_tac.setText(array4GList.get(0).getLteTac());
                        tvLTE_pci.setText(array4GList.get(0).getLtePci());
                        tvLTE_eci.setText(array4GList.get(0).getLteCi());
                        tvLTE_earfcn.setText(array4GList.get(0).getLteEarfac());
                        tvLTE_band.setText(array4GList.get(0).getLteBand()+"");
                        tv_cell_type.setText("LTE");
                        tv_cell_type2.setText(array4GList.get(0).getLteType());
                        tv_mnc.setText(array4GList.get(0).getLteMncString());
                        tv_rssl.setText(array4GList.get(0).getLteRssi());
                        tv_rsrp.setText(array4GList.get(0).getLteRsrp());
                        tv_rsrq.setText(array4GList.get(0).getLteRsrq());
                        tv_ssSinr.setText("---");
                        tv_ssRSRQ.setText("---");
                        tv_ssRSRP.setText("---");
                        recyclerView4G.setAdapter(new My4GAdapter(array4Lin, mainActivity));
                        my4GAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    private ArrayList<To4GBean> array4GList=new ArrayList<>();
    private ArrayList<To4GBean> array4Lin=new ArrayList<>();
    private ArrayList<To4GBean> array4Lin2=new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout lte_sim;

    public HomeFragment3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mainActivity=getActivity();
        getData(view);
        return view;
    }

    @SuppressLint("NewApi")
    private void getData(View view) {
            //找到控件
            findView(view);

            setLocation();//获取经纬度
            //获取当前5G还是4G



        recyclerView4G.setLayoutManager(new LinearLayoutManager(mainActivity));
        my4GAdapter = new My4GAdapter(array4Lin,mainActivity);

        recyclerView5G.setLayoutManager(new LinearLayoutManager(mainActivity));
        my5GAdapter = new My5GAdapter(listLin,mainActivity);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //每次都先清除集合
                kaList.clear();
                list.clear();
                listLin.clear();
                array4Lin2.clear();
                array4Lin.clear();
                array4GList.clear();
                //每次都添加一条假数据
                add4GMi();
                addMi();

                getDemo();
                Message message = new Message();
                message.what = 2;
                message.obj = kaList;

                handlerUp.sendMessage(message);
                handlerUp.postDelayed(this, 5000);
            }
        };
        handlerUp.post(runnable);
    }

    @SuppressLint("NewApi")
    private void getDemo() {
        TelephonyManager manager = (TelephonyManager) mainActivity.getSystemService(TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission")
        List<CellInfo> cellInfoList = manager.getAllCellInfo();
        for (CellInfo info : cellInfoList) {
            Log.i("ylt", "get4GDemo: " + info.toString());
            if (info.toString().contains("CellInfoLte")) {
                kaList.add("4G");
                @SuppressLint({"NewApi", "LocalSuppress"})
                CellInfoLte cellInfoLte = (CellInfoLte) info;
                @SuppressLint({"NewApi", "LocalSuppress"})
                CellIdentityLte cellIdentityLte = cellInfoLte.getCellIdentity();

                lteTac = cellIdentityLte.getTac();
                lteCi= cellIdentityLte.getCi();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    lteMccString = cellIdentityLte.getMccString();
                    Log.i("ylt", "get4GDemo: mccString  "+ lteMccString);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    lteMncString = cellIdentityLte.getMncString();
                    Log.i("ylt", "get4GDemo: mncString  "+ lteMncString);
                }
                Log.i("ylt", "get4GDemo: tac  "+ lteTac);
                Log.i("ylt", "get4GDemo: ci  "+ lteCi);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    lteRsrp = cellInfoLte.getCellSignalStrength().getRsrp();
                    Log.i("ylt", "get4GDemo: rsrp  "+ lteRsrp);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    lteRsrq = cellInfoLte.getCellSignalStrength().getRsrq();
                    Log.i("ylt", "get4GDemo: rsrq "+ lteRsrq);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    lteRssi = cellInfoLte.getCellSignalStrength().getRssi();
                    Log.i("ylt", "get4GDemo: rssi    "+ lteRssi);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    lteRssnr = cellInfoLte.getCellSignalStrength().getRssnr();
                    Log.i("ylt", "get4GDemo: rssnr    "+ lteRssnr);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    lteEarfac = cellIdentityLte.getEarfcn();
                    Log.i("ylt", "get4GDemo: earfcn"+ lteEarfac);
                }
                ltePci = cellIdentityLte.getPci();

                Log.i("ylt", "get4GDemo: pci"+ ltePci);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    String mobileNetworkOperator = cellIdentityLte.getMobileNetworkOperator();
                    Log.i("ylt", "get4GDemo: mobileNetworkOperator"+mobileNetworkOperator);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    lteBand = getBand(cellIdentityLte.getEarfcn());
                    Log.i("ylt", "get4GDemo: band"+ lteBand);
                }
                if (lteTac != 2147483647 && lteCi != 2147483647) {//当前连接的基站  第一次进来添加到集合 第二次进来不加
                    array4GList.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
                            lteCi+"",lteEarfac+"",lteBand+"",lteRssi+"",
                            lteRsrp+"", lteRsrq+"", lteRssnr+"","op","LTE","小区类型 LTE"));
                }else{
                    if(array4GList.size()==1){
                        if(Integer.parseInt(array4GList.get(0).getLteMncString())==Integer.parseInt(lteMncString)){
                            array4Lin.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
                                    lteCi+"",lteEarfac+"",lteBand+"",lteRssi+"",
                                    lteRsrp+"", lteRsrq+"", lteRssnr+"","op","LTE","小区类型 LTE"));
                        }
                    }
                    if(array4GList.size()==2){
                        if(Integer.parseInt(array4GList.get(1).getLteMncString())==Integer.parseInt(lteMncString)){
                            array4Lin2.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
                                    lteCi+"",lteEarfac+"",lteBand+"",lteRssi+"",
                                    lteRsrp+"", lteRsrq+"", lteRssnr+"","op","LTE","小区类型 LTE"));
                        }
                    }
                }
            } else if (info instanceof CellInfoNr) {
                kaList.add("5G");
                //获取5G数据管理
                CellIdentityNr nr = (CellIdentityNr) ((CellInfoNr) info).getCellIdentity();
                CellSignalStrengthNr nrStrength = (CellSignalStrengthNr) ((CellInfoNr) info)
                        .getCellSignalStrength();
                String mncString = nr.getMncString();
                int tac = nr.getTac();
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
                if(tac!=2147483647){//不为假基站
                    list.add(new To5GBean(tac+"",band+"",nrarfcn+"",pci+"",ssRsrp+"", ssRsrq+"", ssSinr+"",cid,lac,psc,"NR","小区类型 NR",mncString));
                }else{
                    listLin.add(new To5GBean(tac+"",band+"",nrarfcn+"",pci+"",ssRsrp+"", ssRsrq+"", ssSinr+"",cid,lac,psc,"NR","小区类型 NR",mncString));
                }
            }//包含5G的情况下

        }
    }

    private void findView(View view) {
        cons = view.findViewById(R.id.cons);
        tv_location =view. findViewById(R.id.tv_location);
//        5G

        linear_5G =  view.findViewById(R.id.Linear_5G);
        recyclerView4G = view. findViewById(R.id.recycler4G);
        recyclerView5G = view. findViewById(R.id.recycler5G);
        my4GRecycler = view.findViewById(R.id.my4GRecycler);
        my5GRecycler = view.findViewById(R.id.my5GRecycler);
        tv_rssl =  view.findViewById(R.id.tv_rssl);
        tv_rsrp = view. findViewById(R.id.tv_rsrp);
        tv_rsrq =  view.findViewById(R.id.tv_rsrq);
        tv_ssRSRP = view. findViewById(R.id.tv_ssRSRP);
        tv_ssRSRQ =  view.findViewById(R.id.tv_ssRSRQ);
        tv_ssSinr =  view.findViewById(R.id.tv_ssSinr);


        tv_mobile = view. findViewById(R.id.tv_MOBILE);
        tv_operator = view. findViewById(R.id.tv_operator);
        tv_mcc = view. findViewById(R.id.tv_mcc);
        tv_mnc = view. findViewById(R.id.tv_mnc);
        tv_mobile2 =  view.findViewById(R.id.tv_MOBILE2);
        tv_cell_type = view. findViewById(R.id.tv_Cell_type);
        tv_cell_type2 = view. findViewById(R.id.tv_Cell_type2);
        tv_pci =  view.findViewById(R.id.tv_pci);
        tv_ci = view. findViewById(R.id.tv_ci);
        tv_arfcn =  view.findViewById(R.id.tv_arfcn);
        tv_bands =  view.findViewById(R.id.tv_bands);

        tv_psc = view. findViewById(R.id.tv_psc);
        tv_lac =  view.findViewById(R.id.tv_lac);
        tv_pscTo = view. findViewById(R.id.tv_pscTo);
        tv_lacTo = view. findViewById(R.id.tv_lacTo);
        tv_view =  view.findViewById(R.id.tv_view);
        tv_view2 = view. findViewById(R.id.tv_view2);


        iv_log = view. findViewById(R.id.iv_log);
        tv_name = view.findViewById(R.id.tv_Name);
//        5G



//4G

        linear_4G =  view.findViewById(R.id.Linear_4G);
        tvLTE_band = view.findViewById(R.id.tvLTE_band);
        tvLTE_earfcn =view.findViewById(R.id.tvLTE_EARFCN);
        tvLTE_lte = view. findViewById(R.id.tvLTE_lte);
        tvLTE_eci = view. findViewById(R.id.tvLTE_eci);
        tvLTE_pci = view. findViewById(R.id.tvLTE_pci);
        tvLTE_tac = view. findViewById(R.id.tvLTE_tac);
        tvLTE_lte1 = view. findViewById(R.id.tvLTE_LTE);
//4G
    }

    private void setLocation() {
        //获取到经纬度
        Intent intent = getActivity().getIntent();
        String longitude = intent.getStringExtra("longitude");
        String latitude = intent.getStringExtra("latitude");
        if(latitude!=null&&longitude!=null){
            Map<String, Double> map = GCJ02ToWGS84Util.bd09to84(Double.parseDouble(longitude), Double.parseDouble(latitude));
            for(String s:map.keySet()){
                //发送位置信息给广播
//            intentMsg = new Intent("name");
//            intentMsg.putExtra("name","key : "+s+" value : "+map.get(s));
                if(s.equals("lon")){
                    lon=map.get(s)+"";
                }else{
                    lat=map.get(s)+"";
                }
            }
            System.out.println(lon+"       "+lat);
            //将经纬度保存到六位小数
            DecimalFormat df = new DecimalFormat("#.000000");
            //fo
            formatLon = df.format(Double.parseDouble(lon));
            //fo
            formatLat = df.format(Double.parseDouble(lat));
            tv_location.setText("我的位置： "+ formatLon +"/"+ formatLat);
        }
    }




    private void add4GMi() {
        array4Lin.add(new To4GBean("lteMccString","lteMncString",lteTac+"","PCI",
                "","EARFCN","BAND","RSSI",
                "RSRP", "RSRQ", "","","LTE","小区类型 LTE"));
        array4Lin2.add(new To4GBean("lteMccString","lteMncString",lteTac+"","PCI",
                "","EARFCN","BAND","RSSI",
                "RSRP", "RSRQ", "","","LTE","小区类型 LTE"));
    }


    private void addMi() {
        listLin.add(new To5GBean("tac","BAND", "NR ARFCN", "PCI", "RSRP", "RSRQ", "SINR",null,0,0,"NR","小区类型 NR",
                "00"));
    }



    //      用Math进行比较0~10 5是否在此区间

    public static boolean rangeInDefined(int current, int min, int max)
    {
        return Math.max(min, current) == Math.min(current, max);
    }
    //判断smi卡是否存在
    /**
     * 判断是否包含SIM卡
     *
     * @return 状态
     */
    public static boolean hasSimCard(Context context) {
        TelephonyManager telMgr = (TelephonyManager)
                context.getSystemService(TELEPHONY_SERVICE);
        int simState = telMgr.getSimState();
        boolean result = true;
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
            case TelephonyManager.SIM_STATE_UNKNOWN:
                result = false; // 没有SIM卡
                break;
        }
        Log.d("ylt", result ? "有SIM卡" : "无SIM卡");
//        MyToast.showToast( result ? "有SIM卡" : "无SIM卡");
        return result;
    }
}