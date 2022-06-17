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
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.sm.smmap.smmap.R;
import com.sm.smmap.smmap.Utils.GCJ02ToWGS84Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;
import static com.sm.smmap.smmap.Utils.DtUtils.getBand;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseNrFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseNrFragment extends Fragment {

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
    private LinearLayout cons;
    private String formatLon;
    private String formatLat;
    public Runnable run;

    //定义集合 获取是卡槽1还是卡槽2
    private ArrayList<String> kaList = new ArrayList<>();
    //当前小区
    private ArrayList<To5GBean> list5 = new ArrayList<>();
    private ArrayList<To4GBean> list4 = new ArrayList<>();
    private ArrayList<To4GBean> list4Lin = new ArrayList<>();
    private ArrayList<To4GBean> list4Lin2 = new ArrayList<>();
    //邻小区
    private ArrayList<To5GBean> list5Lin = new ArrayList<>();
    private Runnable runnable;
    @SuppressLint("MissingPermission")
    private List<CellInfo> allCellInfo;
    private My5GAdapter my5GAdapter;
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
            if (msg.what == 2) {
                ArrayList<String> list2 = (ArrayList<String>) msg.obj;
                //判断获取到4 5G数据
                if (list2.size() > 0) {
                    scroll.setVisibility(View.VISIBLE);
                    if (list2.get(0).equals("5G") && page == 0) {//5G 第一张卡 页面1
                        linear_5G.setVisibility(View.VISIBLE);
                        linear_4G.setVisibility(View.GONE);
                        my5GRecycler.setVisibility(View.VISIBLE);
                        my4GRecycler.setVisibility(View.GONE);
                        if (list5.size() > 1) {
                            if (list5.get(1).getMnc() != null) {
                                if (Integer.parseInt(list5.get(1).getMnc()) == 01) {
                                    iv_log.setImageResource(R.drawable.cucc);
                                    tv_operator.setText("CUCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国联通");
                                }
                                if (Integer.parseInt(list5.get(1).getMnc()) == 11) {
                                    iv_log.setImageResource(R.drawable.ctcc);
                                    tv_operator.setText("CTCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国电信");
                                }
                                if (Integer.parseInt(list5.get(1).getMnc()) == 00) {
                                    iv_log.setImageResource(R.drawable.cmcc);
                                    tv_operator.setText("CMCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国移动");
                                }
                            }
                            tv_rssl.setText(list5.get(1).getSINR() + "");
                            tv_rsrp.setText(list5.get(1).getRSRP() + "");
                            tv_rsrq.setText(list5.get(1).getRSRQ() + "");
                            tv_ssRSRP.setText(list5.get(1).getRSRP() + "");
                            tv_ssRSRQ.setText(list5.get(1).getRSRQ() + "");
                            tv_ssSinr.setText(list5.get(1).getSINR() + "");

                            tv_mobile.setText(list5.get(1).getNr());
                            tv_mobile2.setText(list5.get(1).getNr());

                            tv_mnc.setText(list5.get(1).getMnc());
                            tv_cell_type.setText(list5.get(1).getNr());
                            tv_cell_type2.setText(list5.get(1).getNrType());
                            tv_pci.setText(list5.get(1).getPCI());
                            tv_ci.setText(list5.get(1).getCID());
                            tv_arfcn.setText(list5.get(1).getNR_ARFCN());
                            tv_bands.setText(list5.get(1).getBAND() + "");
                            tv_lac.setText(list5.get(1).getTac());

                            if (list5.get(1).getPsc() > 0) {
                                tvLac9.setText(list5.get(1).getLac() + "");
                                tv_pscTo.setText(list5.get(1).getPsc() + "");
                                tv_psc.setVisibility(View.VISIBLE);
                                tv_pscTo.setVisibility(View.VISIBLE);
                                tv_view_psc.setVisibility(View.VISIBLE);
                                tvLac.setVisibility(View.VISIBLE);
                                tv_view_lac.setVisibility(View.VISIBLE);
                                tvLac9.setVisibility(View.VISIBLE);
                                tv_view3.setVisibility(View.VISIBLE);
                            } else {
                                tv_psc.setVisibility(View.GONE);
                                tv_pscTo.setVisibility(View.GONE);
                                tv_view_psc.setVisibility(View.GONE);
                                tvLac.setVisibility(View.GONE);
                                tv_view_lac.setVisibility(View.GONE);
                                tvLac9.setVisibility(View.GONE);
                                tv_view3.setVisibility(View.GONE);
                            }


                            my5GAdapter = new My5GAdapter(list5, mainActivity);
                            recyclerView5G.setAdapter(my5GAdapter);
                            my5GAdapter.notifyDataSetChanged();
                        }
                    } else if (list2.get(list2.size() - 1).equals("5G") && page == 1) {//5G 第二张卡 页面2
                        linear_5G.setVisibility(View.VISIBLE);
                        linear_4G.setVisibility(View.GONE);
                        my5GRecycler.setVisibility(View.VISIBLE);
                        my4GRecycler.setVisibility(View.GONE);
                        if (list5.size() > 1) {
                            if (list5.get(1).getMnc() != null) {
                                if (Integer.parseInt(list5.get(1).getMnc()) == 01) {
                                    iv_log.setImageResource(R.drawable.cucc);
                                    tv_operator.setText("CUCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国联通");
                                }
                                if (Integer.parseInt(list5.get(1).getMnc()) == 11) {
                                    iv_log.setImageResource(R.drawable.ctcc);
                                    tv_operator.setText("CTCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国电信");
                                }
                                if (Integer.parseInt(list5.get(1).getMnc()) == 00) {
                                    iv_log.setImageResource(R.drawable.cmcc);
                                    tv_operator.setText("CMCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国移动");
                                }
                            }
                            tv_rssl.setText(list5.get(1).getSINR() + "");
                            tv_rsrp.setText(list5.get(1).getRSRP() + "");
                            tv_rsrq.setText(list5.get(1).getRSRQ() + "");
                            tv_ssRSRP.setText(list5.get(1).getRSRP() + "");
                            tv_ssRSRQ.setText(list5.get(1).getRSRQ() + "");
                            tv_ssSinr.setText(list5.get(1).getSINR() + "");

                            tv_mobile.setText(list5.get(1).getNr());
                            tv_mobile2.setText(list5.get(1).getNr());

                            tv_mnc.setText(list5.get(1).getMnc());
                            tv_cell_type.setText(list5.get(1).getNr());
                            tv_cell_type2.setText(list5.get(1).getNrType());
                            tv_pci.setText(list5.get(1).getPCI());
                            tv_ci.setText(list5.get(1).getCID());
                            tv_arfcn.setText(list5.get(1).getNR_ARFCN());
                            tv_bands.setText(list5.get(1).getBAND() + "");
                            tv_lac.setText(list5.get(1).getTac());


                            if (list5.get(1).getPsc() > 0) {
                                tvLac9.setText(list5.get(1).getLac() + "");
                                tv_pscTo.setText(list5.get(1).getPsc() + "");
                                tv_psc.setVisibility(View.VISIBLE);
                                tv_pscTo.setVisibility(View.VISIBLE);
                                tv_view_psc.setVisibility(View.VISIBLE);
                                tvLac.setVisibility(View.VISIBLE);
                                tv_view_lac.setVisibility(View.VISIBLE);
                                tvLac9.setVisibility(View.VISIBLE);
                                tvLac9.setVisibility(View.VISIBLE);
                                tv_view3.setVisibility(View.VISIBLE);
                            } else {
                                tv_psc.setVisibility(View.GONE);
                                tv_pscTo.setVisibility(View.GONE);
                                tv_view_psc.setVisibility(View.GONE);
                                tvLac.setVisibility(View.GONE);
                                tv_view_lac.setVisibility(View.GONE);
                                tvLac9.setVisibility(View.GONE);
                                tv_view3.setVisibility(View.GONE);
                            }


                            my5GAdapter = new My5GAdapter(list5, mainActivity);
                            recyclerView5G.setAdapter(my5GAdapter);
                            my5GAdapter.notifyDataSetChanged();
                        }
                    } else if (list2.get(0).equals("4G") && page == 0 || list2.get(list2.size() - 1).equals("4G") && page == 1) {//4G 第一个卡 页面1
                        linear_5G.setVisibility(View.GONE);
                        linear_4G.setVisibility(View.VISIBLE);
                        my5GRecycler.setVisibility(View.GONE);
                        my4GRecycler.setVisibility(View.VISIBLE);

                        if (list4.size() == 2 && page == 1) {
                            if (list4.get(1).getLteMncString() != null) {
                                if (Integer.parseInt(list4.get(1).getLteMncString()) == 01) {
                                    iv_log.setImageResource(R.drawable.cucc);
                                    tv_operator.setText("CUCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国联通");
                                }
                                if (Integer.parseInt(list4.get(1).getLteMncString()) == 11) {
                                    iv_log.setImageResource(R.drawable.ctcc);
                                    tv_operator.setText("CTCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国电信");
                                }
                                if (Integer.parseInt(list4.get(1).getLteMncString()) == 00) {
                                    iv_log.setImageResource(R.drawable.cmcc);
                                    tv_operator.setText("CMCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国移动");
                                }
                            }
                            tv_mobile.setText(list4.get(1).getLte());
                            tvLTE_lte.setText(list4.get(1).getLte());
                            tvLTE_lte1.setText(list4.get(1).getLte());
                            tvLTE_tac.setText(list4.get(1).getLteTac());
                            tvLTE_pci.setText(list4.get(1).getLtePci());
                            tvLTE_eci.setText(list4.get(1).getLteCi());
                            tvLTE_earfcn.setText(list4.get(1).getLteEarfac());
                            tvLTE_band.setText(list4.get(1).getLteBand() + "");
                            tv_cell_type.setText("LTE");
                            tv_cell_type2.setText(list4.get(1).getLteType());
                            tv_mnc.setText(list4.get(1).getLteMncString());
                            tv_rssl.setText(list4.get(1).getLteRssi());
                            tv_rsrp.setText(list4.get(1).getLteRsrp());
                            tv_rsrq.setText(list4.get(1).getLteRsrq());
                            tv_ssSinr.setText("---");
                            tv_ssRSRQ.setText("---");
                            tv_ssRSRP.setText("---");
                            my4GAdapter = new My4GAdapter(list4Lin2, mainActivity);
                            recyclerView4G.setAdapter(my4GAdapter);
                            my4GAdapter.notifyDataSetChanged();
                        } else if (list4.size() == 2 && page == 0 || list4.size() == 1) {
                            if (list4.get(0).getLteMncString() != null) {
                                if (Integer.parseInt(list4.get(0).getLteMncString()) == 01) {
                                    iv_log.setImageResource(R.drawable.cucc);
                                    tv_operator.setText("CUCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国联通");
                                }
                                if (Integer.parseInt(list4.get(0).getLteMncString()) == 11) {
                                    iv_log.setImageResource(R.drawable.ctcc);
                                    tv_operator.setText("CTCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国电信");
                                }
                                if (Integer.parseInt(list4.get(0).getLteMncString()) == 00) {
                                    iv_log.setImageResource(R.drawable.cmcc);
                                    tv_operator.setText("CMCC");
                                    tv_mcc.setText("460");
                                    tv_name.setText("中国移动");
                                }
                            }
                            tv_mobile.setText(list4.get(0).getLte());
                            tvLTE_lte.setText(list4.get(0).getLte());
                            tvLTE_lte1.setText(list4.get(0).getLte());
                            tvLTE_tac.setText(list4.get(0).getLteTac());
                            tvLTE_pci.setText(list4.get(0).getLtePci());
                            tvLTE_eci.setText(list4.get(0).getLteCi());
                            tvLTE_earfcn.setText(list4.get(0).getLteEarfac());
                            tvLTE_band.setText(list4.get(0).getLteBand() + "");
                            tv_cell_type.setText("LTE");
                            tv_cell_type2.setText(list4.get(0).getLteType());
                            tv_mnc.setText(list4.get(0).getLteMncString());
                            tv_rssl.setText(list4.get(0).getLteRssi());
                            tv_rsrp.setText(list4.get(0).getLteRsrp());
                            tv_rsrq.setText(list4.get(0).getLteRsrq());
                            tv_ssSinr.setText("---");
                            tv_ssRSRQ.setText("---");
                            tv_ssRSRP.setText("---");
                            my4GAdapter = new My4GAdapter(list4Lin, mainActivity);
                            recyclerView4G.setAdapter(my4GAdapter);
                            my4GAdapter.notifyDataSetChanged();
                        }
                    }
                } else {//如果没有4 5G数据
                    scroll.setVisibility(View.GONE);
                    View view = LayoutInflater.from(mainActivity).inflate(R.layout.lte_smi_backgroud, null);
                    cons.addView(view);
                }
            }
        }
    };


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int page;
    private ScrollView scroll;
    private int lac;
    private int psc;
    private TextView tvLac;
    private TextView tvLac9;
    private View tv_view_lac;
    private TextView tv_psc;
    private TextView tv_pscTo;
    private View tv_view_psc;
    private View tv_view3;
    private Runnable runnable1;

    public BaseNrFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page Parameter 1.
     * @return A new instance of fragment BaseNrFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseNrFragment newInstance(int page) {
        BaseNrFragment fragment = new BaseNrFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (handlerUp != null || runnable != null) {
            handlerUp.removeCallbacks(runnable);
        }
        getClear();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_nr, container, false);
        mainActivity = getActivity();
        getData(view);
        return view;
    }

    private void getData(View view) {


        //找到控件
        findView(view);

        setLocation();//获取经纬度

        recyclerView4G.setLayoutManager(new LinearLayoutManager(mainActivity));
        recyclerView5G.setLayoutManager(new LinearLayoutManager(mainActivity));


        runnable = new Runnable() {
            @Override
            public void run() {
                //每次都先清除集合
                getClear();

                //获取4 5G数据
                getDemo();
                Message message = new Message();
                message.what = 2;
                message.obj = kaList;

                handlerUp.sendMessage(message);
                handlerUp.postDelayed(this, 1000);
            }
        };
        handlerUp.post(runnable);
    }

    private void getClear() {
        kaList.clear();
        list5.clear();

        list4.clear();
        list4Lin.clear();
        list4Lin2.clear();
    }

    @SuppressLint("NewApi")
    private void getDemo() {
        //每次都添加一条假数据
        add4GMi();
        add5GMi();

        TelephonyManager manager = (TelephonyManager) getActivity().getSystemService(TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
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
                    list4.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
                            lteCi+"",lteEarfac+"",lteBand+"",lteRssi+"",
                            lteRsrp+"", lteRsrq+"", lteRssnr+"","op","LTE","小区类型 LTE"));
                }
                else{
                    if(list4.size()==1){
                        if(list4.get(0).getLteMncString()!=null&&lteMccString!=null){
                            if(Integer.parseInt(list4.get(0).getLteMncString())==Integer.parseInt(lteMncString)){
                                list4Lin.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
                                        lteCi+"",lteEarfac+"",lteBand+"",lteRssi+"",
                                        lteRsrp+"", lteRsrq+"", lteRssnr+"","op","LTE","小区类型 LTE"));
                            }
                        }

                    }
                    if(list4.size()==2){
                        if(Integer.parseInt(list4.get(1).getLteMncString())==Integer.parseInt(lteMncString)){//代表一个运营商的邻小区
                            list4Lin2.add(new To4GBean(lteMccString,lteMncString,lteTac+"",ltePci+"",
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

                    // 中国移动和中国联通获取LAC、CID的方式

                    @SuppressLint("MissingPermission")
                    CellLocation cellLocation=manager.getCellLocation();
                    if(cellLocation instanceof GsmCellLocation){
                        GsmCellLocation gsmCellLocation= (GsmCellLocation) cellLocation;
                        lac = gsmCellLocation.getLac();
                        psc = gsmCellLocation.getPsc();
                    }

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
                list5.add(new To5GBean(tac + "", band + "", nrarfcn + "", pci + "", ssRsrp + "", ssRsrq + "", ssSinr + "", cid, lac, psc, "NR", "小区类型 NR", mncString));
            }//包含5G的情况下

        }
    }


    //      用Math进行比较0~10 5是否在此区间

    public static boolean rangeInDefined(int current, int min, int max)
    {
        return Math.max(min, current) == Math.min(current, max);
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

    private void findView(View view) {
        cons = view.findViewById(R.id.cons);
        scroll = view.findViewById(R.id.scroll);

        tv_location =view. findViewById(R.id.tv_location);


        tvLac = view. findViewById(R.id.tvLac);
        tvLac9 = view. findViewById(R.id.tvLac9);
        tv_view_lac = view. findViewById(R.id.tv_view_lac);

        tv_psc = view. findViewById(R.id.tv_psc);
        tv_pscTo = view. findViewById(R.id.tv_pscTo);
        tv_view_psc = view. findViewById(R.id.tv_view_psc);
        tv_view3 = view. findViewById(R.id.tv_view3);
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

        tv_lac =  view.findViewById(R.id.tv_lac);
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
    }

    private void add4GMi() {
        list4Lin.add(new To4GBean("lteMccString","lteMncString",lteTac+"","PCI",
                "","EARFCN","BAND","RSSI",
                "RSRP", "RSRQ", "","","LTE","小区类型 LTE"));
        list4Lin2.add(new To4GBean("lteMccString","lteMncString",lteTac+"","PCI",
                "","EARFCN","BAND","RSSI",
                "RSRP", "RSRQ", "","","LTE","小区类型 LTE"));
    }


    private void add5GMi() {
        list5.add(new To5GBean("tac","BAND", "NR ARFCN", "PCI", "RSRP", "RSRQ", "SINR",null,0,0,"NR","小区类型 NR",
                "00"));
    }

}