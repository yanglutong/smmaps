package com.sm.smmap.smmap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DecimalFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;

import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.DotOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.blankj.utilcode.util.ToastUtils;
import com.mylhyl.circledialog.CircleDialog;
import com.sm.smmap.smmap.Adapter.DemoAdapter;
import com.sm.smmap.smmap.Adapter.DemoAdapteradd;
import com.sm.smmap.smmap.Adapter.SerrnTaAdapter;
import com.sm.smmap.smmap.Bean.NumberBean;
import com.sm.smmap.smmap.Import.ExcelImportActivity;
import com.sm.smmap.smmap.It.Mycallback;
import com.sm.smmap.smmap.Login.LoginActivity;
import com.sm.smmap.smmap.OrmSqlLite.Bean.GuijiViewBean;
import com.sm.smmap.smmap.OrmSqlLite.Bean.GuijiViewBeanjizhan;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerGuijiView;
import com.sm.smmap.smmap.OrmSqlLite.DBManagerJZ;
import com.sm.smmap.smmap.Retrofit.Bean.JzDataQury;
import com.sm.smmap.smmap.Retrofit.Bean.JzGetData;
import com.sm.smmap.smmap.Retrofit.DataAliBean;
import com.sm.smmap.smmap.Retrofit.DataBean;
import com.sm.smmap.smmap.Retrofit.RetrofitFactory;
import com.sm.smmap.smmap.Utils.ACacheUtil;
import com.sm.smmap.smmap.Utils.Bean.SpBean;
import com.sm.smmap.smmap.Utils.DtUtils;
import com.sm.smmap.smmap.Utils.GCJ02ToWGS84Util;
import com.sm.smmap.smmap.Utils.It.IT.AddCallBack;
import com.sm.smmap.smmap.Utils.It.IT.CallBack;
import com.sm.smmap.smmap.Utils.It.IT.ScreenCallBack;
import com.sm.smmap.smmap.Utils.MyToast;
import com.sm.smmap.smmap.Utils.MyUtils;
import com.sm.smmap.smmap.Utils.Myshow;
import com.sm.smmap.smmap.Utils.NotificationUtils;
import com.sm.smmap.smmap.Utils.ViewLoading;
import com.sm.smmap.smmap.introduce.introduceActivity;
import com.sm.smmap.smmap.lte_nr.BaseNrLteActivity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import me.jessyan.autosize.internal.CancelAdapt;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static com.baidu.mapapi.utils.CoordinateConverter.CoordType.BD09LL;
import static com.sm.smmap.smmap.Constant.CmainAcitvity.MAXTA;
import static com.sm.smmap.smmap.Constant.CmainAcitvity.MINTA;
import static com.sm.smmap.smmap.Constant.CmainAcitvity.UNIFORMTA;
import static com.sm.smmap.smmap.Retrofit.MyURL.appKey;
import static com.sm.smmap.smmap.Retrofit.MyURL.getBaseUrl;
import static com.sm.smmap.smmap.Retrofit.MyURL.getBaseUrl3;
import static com.sm.smmap.smmap.Utils.MyUtils.getPermissions;

public class MainActivity extends FragmentActivity implements SensorEventListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener, CancelAdapt {
    JzGetData jzGetData;
    double add;
    int DATATYPE = 1;
    public static int BUSINESS = 0;//选择的运营商
    private List<Double> list = new ArrayList<>();
    private List<Double> list2 = new ArrayList<>();//巡视列表的Ta 添加
    private List<Double> listMarker = new ArrayList<>();
    private DemoAdapter demoAdapter;
    private TextToSpeech textToSpeech = null;//创建自带语音对象
    //获取屏幕经纬度
    Point zuoxiapoint = new Point();
    Point youshangpoint = new Point();
    Point zuoshangpoint = new Point();
    Point youxiapoing = new Point();
    float x1 = (float) 59;
    float y1 = (float) 1989;
    float x2 = (float) 1034;
    float y2 = (float) 360;
    LatLng zuoxiaLatLng = null;
    LatLng youxiaaLatLng = null;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0x19e64dff;

    private static final int accuracyCircleStrokeColor = 0x19e64dff;
    private SensorManager mSensorManager;
    private Double lastX = 0.0;
    private int mCurrentDirection = 0;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    Button requestLocButton;
    boolean isFirstLoc = true; // 是否首次定位
    private MyLocationData locData;
    private float direction;
    //View  查询基站   切换地图     清空地图    当前位置        热力   交通地图 测量开关  距离警报     缩放加   缩放减      测量清空
    Button bt_jizhan, bt_qiehuan, bt_clear, bt_location, bt_hot, bt_jt, bt_juli, bt_jingbao, bt_jia, bt_jian, bt_uiceliangclear;
    //     轨迹记录-  轨迹查看-
    Button bt_text, bt_ikan;
    //整改的UI View   切换 ，   清空地图 ， 交通  ， 热力图，  报警    轨迹开始和停止  轨迹动画开始和停止      轨迹清空     覆盖
    LinearLayout ll_qiehuan, ll_clear, ll_jt, ll_rl, ll_baojing, ll_guijis, ll_gjview, ll_gjclear, ll_gensui, ll_fugai, ll_screen;
    LinearLayout ll_sid, ll_location;
    //            当前的位置         测量         发起查询        基站数量查询
    private Button bt_uilocation, bt_uiceliang, bt_uisearch, bt_jizhan0;
    //
    private ImageView ib_qiehuan, ib_clear, ib_jt, ib_rl, ib_baojing, iv_gjstart, iv_viewstart, ib_gensui, ib_fugai, ib_screen;
    private TextView tv_screen;
    //    Button bt_hot;//
    //用于底部弹出窗
    private Dialog dialog, dialog2, dialogmenu;
    private View inflate;
    private EditText et_taclac, et_eci, et_ta, et_sid, ets_lon, ets_lat;
    private Button bt_adddilao;
    private CheckBox rb_yidong, rb_ldainxin4, rb_liantong, rb_cdma, rb_bdjz1, rb_bdjz2;
    private ImageView iv_finish, iv_set;
    private int jizhanFlag = 0;
    public static LatLng mylag = null;
    public static String longitude;
    private static String latitude;
    //menu底部弹出窗
    EditText et_guijitime;
    EditText et_baojingtime;
    EditText et_queryMax;
    EditText et_querynum;
    EditText et_data;
    EditText et_fw;
    EditText et_kg;
    Button bt_adddilaomenu;
    Button bt_m_locations;
    //查询的基站数据
    private DataBean dataBean;
    //阿里的基站查询
    private DataAliBean dataAliBean;
    List<LatLng> points = new ArrayList<LatLng>();//绘制线的list
    private LatLng ll;
    private double distance2 = 0;
    private double distance = 0;
    private int hottype = 1;
    private int hjttype = 1;
    private boolean juliFlage = false;
    //百度地图可移动拖拽的
    private Marker markerMy;
    private Bundle bundles;//接口查询获取的数据
    private GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用
    private GeoCoder SdmSearch = null; // 搜索模块，也可去掉地图模块独立使用

    private String address_reverseGeoCodeResult = "";//拖动后的marker地址
    private boolean jingbaoflag = false;//判断是否启动警报
    private int juliType = 0;
    private boolean guijiFlag = false;
    private boolean guijistart = false;
    private boolean gensuiFlag = false;
    private boolean fugaiFlag = false;
    private int ScanSpan = 1000;//报警时间的设置
    //数据库操作轨迹
    private DBManagerGuijiView dbManagerGuijiView;
    private LatLng jisuanLant;//用于计算轨迹点间隔
    private Handler mHandler;
    private Polyline mPolyline;
    private Marker mMoveMarker;
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 180;
    private static final double DISTANCE = 0.00001;
    private BitmapDescriptor mRedTexture = BitmapDescriptorFactory.fromAsset("Icon_road_red_arrow.png");
    private BitmapDescriptor mBlueTexture = BitmapDescriptorFactory.fromAsset("Icon_road_blue_arrow.png");
    private BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("Icon_road_green_arrow.png");
    private NotificationUtils mNotificationUtils;
    private Notification notification;
    private Timer timer, timer2;
    LatLng positionjingbaojizhan;
    List<LatLng> pointsJingbao;
    private Boolean MapinitFlag = false;
    private boolean xunFlag = false;
    DBManagerJZ dbManagerJZ;
    DemoAdapteradd demoAdapteradd;
    SerrnTaAdapter serrnAdapters;
    UiSettings uiSettings;
    private Handler handler = new Handler() {
        @TargetApi(Build.VERSION_CODES.N)
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Viewgjs();
            }
            if (msg.what == 2) {//报警功能
//                JingBaos(pointsJingbao, positionjingbaojizhan);
                JingBaos(pointsJingbao, positionjingbaojizhan, mylag, true);
                try {
                    List<GuijiViewBeanjizhan> guijiViewBeanjizhans = dbManagerJZ.queryType();
                    Log.d(TAG, "JingBaos:Type " + guijiViewBeanjizhans);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    //巡视列表的回调
    ScreenCallBack screencallback = new ScreenCallBack() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void call(final JzGetData.DataBean dataBean) {
            Log.d(TAG, "screencallbackcall: " + dataBean);
            mBaiduMap.clear();
            LatLng latLng = new LatLng(dataBean.getLatitude(), dataBean.getLongitude());
            CoordinateConverter converter = new CoordinateConverter()
                    .from(CoordinateConverter.CoordType.GPS)
                    .coord(latLng);
            //转换坐标 84转百度09
            final LatLng desLatLngBaidus = converter.convert();

            Bundle bundle = new Bundle();
            //计算eci
            final int ECIS = dataBean.getENodeBid() * 256 + dataBean.getAreaMark();
            DecimalFormat df = new DecimalFormat("#.000000");
            final String lat = df.format(desLatLngBaidus.latitude);
            final String lon = df.format(desLatLngBaidus.longitude);
            bundle.putString("address", dataBean.getAreaName() + "");
            bundle.putString("ci", ECIS + "");
            bundle.putString("mcc", "");
            bundle.putString("mnc", dataBean.getMnc() + "");
            bundle.putString("type", "2");//2 代表没添加过的数据是当前屏幕获得的
            bundle.putString("Radius", "0");
            bundle.putString("lac", dataBean.getTac() + "");
            bundle.putString("lat", lat);
            bundle.putString("lon", lon);
            bundle.putString("resources", "内部数据");

            bundle.putString("band", dataBean.getBand() + "");
            bundle.putString("types", dataBean.getType() + "");
            bundle.putString("pci", dataBean.getPci() + "");
            bundle.putString("down", dataBean.getDownFrequencyPoint() + "");
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.jizhan1);
            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions optiona = new MarkerOptions()
                    .extraInfo(bundle)
                    .anchor(10, 30)
                    .position(desLatLngBaidus) //必传参数
                    .perspective(true)
                    .icon(bitmap) //必传参数
                    .draggable(true)
                    .draggable(true)
                    //设置平贴地图，在地图中双指下拉查看效果
                    .flat(true)
                    .alpha(0.5f);
            //在地图上添加Marker，并显示
            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker
            //展示到当前的位置
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(desLatLngBaidus).zoom(19.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            //点击事件
            TextView tv_title, tv_fugai, tv_mnc, tv_lac, tv_cid, tv_address, tv_lat_lon, tv_resources;
            ImageButton bt_openMap;
            Button bt_quanjing;
            Button bt_m_dele;
            ImageButton finsh;
            View view = View.inflate(MainActivity.this, R.layout.activity_map_info, null);
            tv_title = view.findViewById(R.id.tv_title);
            tv_fugai = view.findViewById(R.id.tv_fugai);
            tv_mnc = view.findViewById(R.id.tv_mnc);
            tv_lac = view.findViewById(R.id.tv_lac);
            tv_cid = view.findViewById(R.id.tv_cid);
            tv_address = view.findViewById(R.id.tv_address);
            tv_lat_lon = view.findViewById(R.id.tv_lat_lon);
            tv_resources = view.findViewById(R.id.tv_resources);

            String str = "";
            if (String.valueOf(dataBean.getMnc()).equals("0")) {
                str = "移动";
            }
            if (String.valueOf(dataBean.getMnc()).equals("1")) {
                str = "联通";
            }
            if (String.valueOf(dataBean.getMnc()).equals("11")) {
                str = "电信";
            }
            tv_title.setText(str + "");
            tv_fugai.setText("");
            tv_mnc.setText(dataBean.getMnc() + "");
            tv_lac.setText(dataBean.getTac() + "");
            tv_cid.setText(ECIS + "");
            tv_address.setText(dataBean.getAreaName() + "");
            tv_lat_lon.setText("纬度:" + lat + "," + "经度:" + lon);
            tv_resources.setText("内部数据");


            LinearLayout ll_types = view.findViewById(R.id.ll_types);
            LinearLayout ll_pci = view.findViewById(R.id.ll_pci);
            LinearLayout ll_band = view.findViewById(R.id.ll_band);
            LinearLayout ll_down = view.findViewById(R.id.ll_down);
            ll_types.setVisibility(View.VISIBLE);
            ll_pci.setVisibility(View.VISIBLE);
            ll_band.setVisibility(View.VISIBLE);
            ll_down.setVisibility(View.VISIBLE);

            TextView tv_band = view.findViewById(R.id.tv_band);
            TextView tv_types = view.findViewById(R.id.tv_types);
            TextView tv_pci = view.findViewById(R.id.tv_pci);
            TextView tv_down = view.findViewById(R.id.tv_down);
            tv_band.setText(dataBean.getBand() + "");
            tv_types.setText(dataBean.getType() + "");
            tv_pci.setText(dataBean.getPci() + "");
            tv_down.setText(dataBean.getDownFrequencyPoint() + "");


            finsh = view.findViewById(R.id.iv_finishs);
            finsh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBaiduMap.hideInfoWindow();//隐藏弹出窗
                }
            });
            bt_openMap = view.findViewById(R.id.bt_openMap);
            bt_openMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //底部弹出窗

                    Dialog dialog2 = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                    //填充对话框的布局
                    View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dibushowdaohao, null);
                    //初始化控件
                    TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
//        choosePhoto.setVisibility(View.GONE);
                    TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
//                            baidu.setOnClickListener(new MyonclickXian(mylag));
                    baidu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                LatLng startLatLng = new LatLng(39.940387, 116.29446);
                                LatLng endLatLng = new LatLng(39.87397, 116.529025);
                                String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
                                                "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
                                        lat, lon);
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
//                    ToastUtil.showShort(this, "请安装百度地图");
//                                Toast.makeText(MainActivity.this, "请安装百度地图", Toast.LENGTH_SHORT).show();
                                MyToast.showToast("请安装百度地图");
                            }
                        }
                    });
                    gaode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
//                    double gdLatitude = 39.92848272
//                    double gdLongitude = 116.39560823
                                //初始化坐标转换工具类，设置源坐标类型和坐标数据
                                //初始化坐标转换工具类，设置源坐标类型和坐标数据
                                LatLng latLngs = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                                CoordinateConverter converter = new CoordinateConverter()
                                        .from(BD09LL)
                                        .coord(latLngs);

//转换坐标
                                LatLng desLatLng = converter.convert();

                                String uri = String.format("amapuri://route/plan/?dlat=%s&dlon=%s&dname=终点&dev=0&t=0",
                                        desLatLng.latitude, desLatLng.longitude);
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                intent.addCategory("android.intent.category.DEFAULT");
                                intent.setData(Uri.parse(uri));
                                intent.setPackage("com.autonavi.minimap");
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
//                                Toast.makeText(MainActivity.this, "请安装高德地图", Toast.LENGTH_SHORT).show();
                                MyToast.showToast("请安装高德地图");
                            }
                        }
                    });
                    //将布局设置给Dialog
                    dialog2.setContentView(inflate);
                    //获取当前Activity所在的窗体
                    Window dialogWindow = dialog2.getWindow();
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity(Gravity.BOTTOM);
                    //获得窗体的属性
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                    dialogWindow.setAttributes(lp);
                    dialog2.show();//显示对话框
                }
            });
            bt_quanjing = view.findViewById(R.id.bt_quanjing);
            bt_quanjing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Intent intent = new Intent(MainActivity.this, PanoramaDemoActivityMain.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", lat);
                    bundle.putString("lon", lon);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            bt_m_dele = view.findViewById(R.id.bt_m_dele);
            bt_m_dele.setVisibility(View.VISIBLE);
            bt_m_dele.setBackground(getResources().getDrawable(R.mipmap.markeradd));
            bt_m_dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog2 = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                    //填充对话框的布局
                    View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dibushow2, null);
                    //初始化控件
                    final EditText editText = inflate.findViewById(R.id.et_ta);
                    TextView tv_titls = inflate.findViewById(R.id.tv_titls);
                    tv_titls.setText("添加TA值");
                    ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
                    iv_finish.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog2.dismiss();
                        }
                    });
                    final RecyclerView recyclerView = inflate.findViewById(R.id.recylerview);

                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
                    serrnAdapters = new SerrnTaAdapter(list, callBack);
                    recyclerView.setAdapter(serrnAdapters);
                    Button btadd = inflate.findViewById(R.id.btadd);
                    btadd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (TextUtils.isEmpty(editText.getText().toString())) {
//                                Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                                MyToast.showToast("输入不能为空");
                                return;
                            }
                            list.add(Double.parseDouble(editText.getText().toString()));
                            editText.setText("");
                            serrnAdapters.notifyDataSetChanged();

//                aaa
                        }
                    });

                    Button bt_adddilao = inflate.findViewById(R.id.bt_adddilao);

                    bt_adddilao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (list.size() > 0) {
                                String s = MyUtils.listToString(list);
                                GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
                                d.setDown(dataBean.getDownFrequencyPoint() + "");
                                d.setPci(dataBean.getPci() + "");
                                d.setTypes(dataBean.getType() + "");
                                d.setBand(dataBean.getBand() + "");
                                d.setId(1);
                                d.setAddress(dataBean.getAreaName() + "");
                                d.setLac(dataBean.getTac() + "");
                                d.setMcc("");
                                d.setMnc(dataBean.getMnc() + "");
                                d.setRadius("0");
                                d.setTa(s);
                                d.setType(0);
                                d.setLat(desLatLngBaidus.latitude + "");
                                d.setLon(desLatLngBaidus.longitude + "");
                                d.setCi(ECIS + "");
                                d.setResources("内部数据" + "");
                                try {
                                    int i = dbManagerJZ.insertStudent(d);
                                    Log.d(TAG, "insertonClick: " + i);
                                    if (i == 1) {
                                        dialog2.dismiss();
//                                        Toast.makeText(AppLication.getInstance().getApplicationContext(), "增加成功", Toast.LENGTH_LONG).show();
//                                        MyToast.showToast();
                                        MyToast.showToast("增加成功");
                                        list.clear();
                                        initdatas2();
                                    }

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    Log.d(TAG, "insertonClickerro: " + e.getMessage());
                                }
                            } else {
//                                Toast.makeText(AppLication.getInstance().getApplicationContext(), "至少添加一个TA值", Toast.LENGTH_LONG).show();
                                MyToast.showToast("至少添加一个TA值");
                            }
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
                }
            });
//            finsh = findViewById(R.id.finsh);
            final InfoWindow mInfoWindow = new InfoWindow(view, desLatLngBaidus, -47);
            mBaiduMap.showInfoWindow(mInfoWindow);
//构造InfoWindow
//point 描述的位置点
//-100 InfoWindow相对于point在y轴的偏移量
            final InfoWindow mInfoWindows = new InfoWindow(view, desLatLngBaidus, -47);

//使InfoWindow生效
            mBaiduMap.showInfoWindow(mInfoWindows);
        }
    };
    Myshow.Callshow callshow = new Myshow.Callshow() {
        @Override
        public void call() {
            initdatas2();
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void callfg() {
            cover();
        }
    };
    Mycallback mycallback = new Mycallback() {
        @Override
        public void call() {
            initdatas();
        }

        @Override
        public void isCheck(boolean b, int t) {
            Log.d(TAG, "isCheckbs: " + b);
            xunFlag = b;
            double zoomset = mBaiduMap.getMapStatus().zoom;
            add = 0.0125f / Math.pow(2, zoomset - 15);
            zuoxiapoint.set((int) x1, (int) y1);
            youshangpoint.set((int) x2, (int) y2);
            LatLng zuoshangLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoshangpoint);//初始化
            LatLng youxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(youxiapoing);//初始化
            youxiaaLatLng = mBaiduMap.getProjection().fromScreenLocation(youshangpoint);//初始化
            //转换坐标类型
            Map<String, Double> stringDoubleMap = GCJ02ToWGS84Util.bd09to84(youxiaaLatLng.longitude, youxiaaLatLng.latitude);
            youxiaaLatLng = new LatLng(stringDoubleMap.get("lat"), stringDoubleMap.get("lon"));


            zuoxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoxiapoint);
            //转换坐标类型
            Map<String, Double> zuoxial = GCJ02ToWGS84Util.bd09to84(zuoxiaLatLng.longitude, zuoxiaLatLng.latitude);
            zuoxiaLatLng = new LatLng(zuoxial.get("lat"), zuoxial.get("lon"));
            BUSINESS = t;
            if (b == true) {
                mBaiduMap.clear();
                JzGetData(add);
            } else {
                initdatas3();
            }

        }

        @Override
        public void DataShow(boolean b) {
            if (b == true) {
                initdatas3();
            }
        }


    };
    private CallBack callBack = new CallBack() {
        @Override
        public void call(String data, int i) {
            Log.d("nzq", "call: " + data + "第--" + i);
            list.remove(i);//新增的button// 删除
//            list.set(i, 6);//替换
            if (demoAdapter != null) {
                demoAdapter.notifyDataSetChanged();
            }
            if (serrnAdapters != null) {
                serrnAdapters.notifyDataSetChanged();
            }

        }


    };

    private AddCallBack addcallBack = new AddCallBack() {
        @Override
        public void call(String data, int i) {
            listMarker.remove(i);
            demoAdapteradd.notifyDataSetChanged();
        }
    };

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("杨路通", "onCreate: ");
        // 地图初始化
        MyUtils.getPermissions(this);//获取权限
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        LocationClient.setAgreePrivacy(true);//同意用户隐私政策
        SDKInitializer.initialize(getApplicationContext());
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
        setContentView(R.layout.activity_location);
        //后台运行
        isIgnoringBatteryOptimizations();
        requestIgnoreBatteryOptimizations();
        //初始化未认证key


        jzGetData = new JzGetData();
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mMapView.showZoomControls(false);//取消缩放

        uiSettings = mBaiduMap.getUiSettings();
        ViewLoading.show(MainActivity.this, "加载中");
        //版本更新
        MyUtils.upApp(this, ACacheUtil.getID());//版本更新
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        SdmSearch = GeoCoder.newInstance();
        SdmSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
            @Override
            public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

            }

            @Override
            public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//                if (reverseGeoCodeResult == null
//                        || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
//                    // 没有检测到结果
////                    ToastUtils.showShort("2");
//                    try {
//                        dbManagerJZ = new DBManagerJZ(MainActivity.this);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    LatLng latLng = new LatLng(Double.parseDouble(ets_lon.getText().toString()), Double.parseDouble(ets_lat.getText().toString()));
////                    LatLng latLng = new LatLng(Double.parseDouble("40.050716"), Double.parseDouble("116.33068"));
//                    CoordinateConverter converter = new CoordinateConverter()
//                            .from(CoordinateConverter.CoordType.GPS)
//                            .coord(latLng);
//                    //转换坐标
//                    LatLng desLatLngBaidu = converter.convert();
//                    GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
////                if (jizhanFlag == 4) {
////                    d.setSid(et_sid.getText().toString());
////                } else {
////                    d.setSid("");
////                }
//                    d.setTypes("");
//                    d.setBand("");
//                    d.setPci("");
//                    d.setDown("");
//                    d.setId(1);
////                    d.setAddress(reverseGeoCodeResult.getAddress());
//                    d.setCi(et_eci.getText().toString()+"");
//                    d.setLac(et_taclac.getText().toString()+"");
//                    d.setMcc("");
//                    d.setMnc("");
//                    d.setRadius("");
////                        d.setTa(et_ta.getText().toString());
//                    Log.d(TAG, "onResponse:aalist" + list);
//                    d.setTa(MyUtils.listToString(list));
//                    d.setType(0);
//                    d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                    d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                    d.setResources("手动输入");
//                    try {
//                        dbManagerJZ.insertStudent(d);
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                    MapStatus.Builder builder = new MapStatus.Builder();
//                    builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                    initdatas();
//                    list.clear();
//                    return;
//                }
//                ReverseGeoCodeResult.AddressComponent addressDetail = reverseGeoCodeResult.getAddressDetail();
//                //需要的地址信息就在AddressComponent 里
////                Log.d("2ilssDetail", "onGetReverseGeoCodeResult: " + addressDetail.toString());
////                ToastUtils.showShort("1" + reverseGeoCodeResult.getAddress());
//                try {
//                    dbManagerJZ = new DBManagerJZ(MainActivity.this);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                LatLng latLng=new LatLng(Double.parseDouble(ets_lon.getText().toString()),Double.parseDouble(ets_lat.getText().toString()));
////                LatLng latLng = new LatLng(Double.parseDouble("40.050716"), Double.parseDouble("116.33068"));
//                CoordinateConverter converter = new CoordinateConverter()
//                        .from(CoordinateConverter.CoordType.GPS)
//                        .coord(latLng);
//                //转换坐标
//                LatLng desLatLngBaidu = converter.convert();
//                GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
////                if (jizhanFlag == 4) {
////                    d.setSid(et_sid.getText().toString());
////                } else {
////                    d.setSid("");
////                }
//                d.setTypes("");
//                d.setBand("");
//                d.setPci("");
//                d.setDown("");
//                d.setId(1);
//                d.setAddress(reverseGeoCodeResult.getAddress());
//                d.setCi(et_eci.getText().toString()+"");
//                d.setLac(et_taclac.getText().toString()+"");
//                d.setMcc("");
//                d.setMnc("");
//                d.setRadius("");
////                        d.setTa(et_ta.getText().toString());
//                Log.d(TAG, "onResponse:aalist" + list);
//                d.setTa(MyUtils.listToString(list));
//                d.setType(0);
//                d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                d.setResources("手动输入");
//                try {
//                    dbManagerJZ.insertStudent(d);
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                initdatas();
//                list.clear();
            }
        });

        try {
            dbManagerGuijiView = new DBManagerGuijiView(MainActivity.this);
            dbManagerJZ = new DBManagerJZ(MainActivity.this);
        } catch (SQLException e) {
            e.printStackTrace();
        }

////
        // 地图初始化
        MyUtils.getPermissions(this);//获取权限
        try {
            findView();//View初始化
        } catch (Exception e) {
            Log.e("TAG_onRestart", "findView: "+e.getMessage() );
            e.printStackTrace();
        }
        initMap();
//        initdatas();//查询数据库数据
        initdatas2();

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onMarkerClick(final Marker marker) {
                Log.d(TAG, "onMarkerClick: " + marker.getExtraInfo());
                if (jingbaoflag == true) {
//                    Toast.makeText(MainActivity.this, "请先关闭警报", Toast.LENGTH_LONG).show();
                    MyToast.showToast("请先关闭报警");
                } else {
                    setMapMakerView(marker);
                }
                return false;
            }
        });
        //线的点击事件

        //设置Polyline点击监听器
        mBaiduMap.setOnPolylineClickListener(new BaiduMap.OnPolylineClickListener() {
            @Override
            public boolean onPolylineClick(Polyline polyline) {
//                Toast.makeText(MainActivity.this, "Click on polyline", Toast.LENGTH_LONG).show();
//                Log.d(TAG, "onPolylineClick: "+polyline.getPoints().);
//                if (points.get(0)==polyline.getPoints().get(0)){
//                    Log.d(TAG, "onPolylineClick: points"+"相等");
//                }
                return false;
            }
        });
        //设置地图单击事件监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onMapClick(LatLng latLng) {
                if (juliFlage == true) {
                    DecimalFormat df = new DecimalFormat(".#");
                    //构建折线点坐标
                    points.add(latLng);
                    //构建Marker图标
                    if (points.size() > 1) {
                        //设置折线的属性
                        OverlayOptions mOverlayOptions = new PolylineOptions()
                                .width(2)
                                .color(Color.rgb(65, 105, 225))
                                .points(points);
                        //在地图上绘制折线
                        //mPloyline 折线对象
                        Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
                        int sizes = 0;

                        Log.d(TAG, "onMapClickpoints.size: " + points.size());
                        for (int i = 0; i < points.size(); i++) {
                            System.out.print("aaaaaaa" + i);
                            Log.d(TAG, "aaaaaaaonMapClick: " + i);
                            sizes = i;
//                         distance = DistanceUtil.getDistance(points.get(i), points.get(1));
                        }
                        LatLng latLng2 = points.get(sizes - 1);
                        distance2 = DistanceUtil.getDistance(latLng2, latLng);//第二个全长
                        Log.d(TAG, "distanceonMapClicks: " + distance + distance2 + "米" + "第二段长" + distance2 + "米");
                        //文字覆盖物位置坐标
                        LatLng latLngtext = new LatLng(latLng.latitude - 0.00005, latLng.longitude);
                        //构建TextOptions对象
                        OverlayOptions mTextOptions = new TextOptions()
                                .text("" + df.format(distance2) + "米") //文字内容
                                .bgColor(Color.rgb(224, 255, 255)) //背景色
                                .fontSize(22) //字号
                                .fontColor(Color.rgb(0, 0, 0)) //文字颜色
                                .rotate(0) //旋转角度
                                .position(latLngtext);
                        //在地图上显示文字覆盖物
                        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
                        //                   latLngOnclicek = latLng;

                        //防止遮挡画标记
                        for (int i = 0; i < points.size(); i++) {
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.celiangbiao);
                            //构建MarkerOption，用于在地图上添加Marker
                            Bundle bundle = new Bundle();
                            bundle.putString("address", "");
                            OverlayOptions option = new MarkerOptions()
                                    .position(points.get(i))
                                    .extraInfo(bundle)
                                    .perspective(true)
                                    .visible(true)
                                    .icon(bitmap);
                            //在地图上添加Marker，并显示
                            mBaiduMap.addOverlay(option);
                            Log.d(TAG, "pointsonMapClick: " + points.size());
                        }
                    }
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.celiangbiao);


                    //构建MarkerOption，用于在地图上添加Marker
                    Bundle bundle = new Bundle();
                    bundle.putString("address", "");
                    OverlayOptions option = new MarkerOptions()
                            .position(latLng)
                            .extraInfo(bundle)
                            .perspective(true)
                            .visible(true)
                            .icon(bitmap);
                    //在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                    Log.d(TAG, "pointsonMapClick: " + points.size());
                }


            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                if (juliFlage == true) {
                    DecimalFormat df = new DecimalFormat(".#");
                    LatLng latLng = new LatLng(mapPoi.getPosition().latitude, mapPoi.getPosition().longitude);
                    //构建折线点坐标
                    points.add(latLng);

                    if (points.size() > 1) {
                        //设置折线的属性
                        OverlayOptions mOverlayOptions = new PolylineOptions()
                                .width(2)
                                .color(Color.rgb(65, 105, 225))
                                .points(points);
                        //在地图上绘制折线
                        //mPloyline 折线对象
                        Overlay mPolyline = mBaiduMap.addOverlay(mOverlayOptions);
                        int sizes = 0;
                        for (int i = 0; i < points.size(); i++) {
                            System.out.print("aaaaaaa" + i);
                            Log.d(TAG, "aaaaaaaonMapClick: " + i);
                            sizes = i;
                        }
                        LatLng latLng2 = points.get(sizes - 1);
                        double distance2 = DistanceUtil.getDistance(latLng2, latLng);//第二个全长
                        Log.d(TAG, "distanceonMapClick: " + "第二段长" + distance2 + "米");
                        //文字覆盖物位置坐标

                        //构建TextOptions对象
                        LatLng latLngtext = new LatLng(latLng.latitude - 0.00005, latLng.longitude);
                        //构建TextOptions对象
                        OverlayOptions mTextOptions = new TextOptions()
                                .text("" + df.format(distance2) + "米") //文字内容
                                .bgColor(Color.rgb(224, 255, 255)) //背景色
                                .fontSize(22) //字号
                                .fontColor(Color.rgb(0, 0, 0)) //文字颜色
                                .rotate(0) //旋转角度
                                .position(latLngtext);
                        //在地图上显示文字覆盖物
                        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
                        //防止遮挡画标记
                        for (int i = 0; i < points.size(); i++) {
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.celiangbiao);
                            //构建MarkerOption，用于在地图上添加Marker
                            Bundle bundle = new Bundle();
                            bundle.putString("address", "");
                            OverlayOptions option = new MarkerOptions()
                                    .position(points.get(i))
                                    .extraInfo(bundle)
                                    .perspective(true)
                                    .visible(true)
                                    .icon(bitmap);
                            //在地图上添加Marker，并显示
                            mBaiduMap.addOverlay(option);
                            Log.d(TAG, "pointsonMapClick: " + points.size());
                        }
                    }
                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.celiangbiao);
                    //构建MarkerOption，用于在地图上添加Marker
                    Bundle bundle = new Bundle();
                    bundle.putString("address", "");
                    OverlayOptions option = new MarkerOptions()
                            .position(latLng)
                            .extraInfo(bundle)
                            .visible(true)
                            .icon(bitmap);
                    //在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                }

//                return false;
            }
        });

        // 地图缩放的监听
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {//滑动屏幕结束时的的状态
                double zoomset = mBaiduMap.getMapStatus().zoom;
                Log.d(TAG, "aaweq: " + zoomset);
                if (xunFlag == true) {
//

                }

//              i

                if (zoomset > 18) {
                    add = 0.0125f / Math.pow(2, zoomset - 15);
                    zuoxiapoint.set((int) x1, (int) y1);
                    youshangpoint.set((int) x2, (int) y2);
                    LatLng zuoshangLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoshangpoint);//初始化
                    LatLng youxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(youxiapoing);//初始化
                    youxiaaLatLng = mBaiduMap.getProjection().fromScreenLocation(youshangpoint);//初始化
                    //转换坐标类型
                    Map<String, Double> stringDoubleMap = GCJ02ToWGS84Util.bd09to84(youxiaaLatLng.longitude, youxiaaLatLng.latitude);
                    youxiaaLatLng = new LatLng(stringDoubleMap.get("lat"), stringDoubleMap.get("lon"));


                    zuoxiaLatLng = mBaiduMap.getProjection().fromScreenLocation(zuoxiapoint);
                    //转换坐标类型
                    Map<String, Double> zuoxial = GCJ02ToWGS84Util.bd09to84(zuoxiaLatLng.longitude, zuoxiaLatLng.latitude);
                    zuoxiaLatLng = new LatLng(zuoxial.get("lat"), zuoxial.get("lon"));
                    JzGetData(add);

                }

            }
        });
        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
//
            }
        });
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {

            //在Marker拖拽过程中回调此方法，这个Marker的位置可以通过getPosition()方法获取
            //marker 被拖动的Marker对象
            @Override
            public void onMarkerDrag(Marker marker) {
                //对marker处理拖拽逻辑
                if (jingbaoflag == true) {
//                    Toast.makeText(MainActivity.this, "请先关闭警报", Toast.LENGTH_LONG).show();
                    MyToast.showToast("请先关闭报警");
                    return;
                }
            }

            //在Marker拖动完成后回调此方法， 这个Marker的位可以通过getPosition()方法获取
            //marker 被拖拽的Marker对象
            @Override
            public void onMarkerDragEnd(final Marker marker) {
                final LatLng positionA = marker.getPosition();
//                GeoCoder mCoder = GeoCoder.newInstance();

                if (jingbaoflag == true) {
//                    Toast.makeText(MainActivity.this, "请先关闭警报", Toast.LENGTH_LONG).show();
                    MyToast.showToast("请先关闭报警");
                    return;
                }
                mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                    @Override
                    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                    }

                    @Override
                    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                            //没有找到检索结果
                            if (jingbaoflag == true) {
//                                Toast.makeText(MainActivity.this, "请先关闭警报", Toast.LENGTH_LONG).show();
                                MyToast.showToast("请先关闭报警");
                                return;

                            }
                            Log.d(TAG, "onGetReverseGeoCodeResult: " + "没有找到检索结果");
                            mBaiduMap.clear();
                            Log.d(TAG, "onMarkerDragEnd: " + positionA);
//                            aaaaaaaa
                            Bundle extraInfo = marker.getExtraInfo();

                            String ta1 = extraInfo.getString("ta");

                            List<Double> lists = MyUtils.StringTolist(ta1);
                            int sum = 0;
                            for (int j = 0; j < lists.size(); j++) {
                                sum += lists.get(j);
                            }

                            int size = lists.size();
                            Log.d(TAG, "DataAll平均数: " + sum / size);
                            //平均值的圈


                            double sum_db = sum;
                            double size_db = size;
                            double Myradius_db = sum_db / size_db * 78;
                            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
                            int Myradius = new Double(Myradius_db).intValue();
                            Log.d(TAG, "JingBaos:Myradius " + Myradius);
                            if (UNIFORMTA == true) {
                                OverlayOptions ooCirclepingjun = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(40, 255,
                                                0,
                                                0))

                                        .center(positionA)

                                        .stroke(new Stroke(5, Color.rgb(255,
                                                0,
                                                0)))
                                        .radius(Myradius);
                                mBaiduMap.addOverlay(ooCirclepingjun);
                                Log.e(TAG, "DataAll:最大 " + Collections.max(lists));
                            }

                            //最大ta圈
                            if (MAXTA == true) {
                                OverlayOptions ooCircleaMAx = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(0, 255,
                                                0,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(2, Color.rgb(255,
                                                0,
                                                0)))
                                        .radius((int) (Collections.max(lists) * 78));

                                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
//            Log.d("nzq", "onCreate: " + center);
                                mBaiduMap.addOverlay(ooCircleaMAx);
                            }
                            Log.d(TAG, "DataAllsum: " + sum);
                            //最小ta圈
                            if (MINTA == true) {
                                OverlayOptions ooCircleaMin = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(0, 255,
                                                0,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(2, Color.rgb(255,
                                                0,
                                                0)))
                                        .radius((int) (Collections.min(lists) * 78));

                                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
//            Log.d("nzq", "onCreate: " + center);
                                mBaiduMap.addOverlay(ooCircleaMin);
                            }
                            Log.d(TAG, "listDataAll: " + lists.get(0));

                            //小圆
//                    LatLng llDot = new LatLng(39.90923, 116.447428);
                            OverlayOptions ooDot = new DotOptions().center(positionA).radius(6).color(0xFF0000FF);
                            mBaiduMap.addOverlay(ooDot);
                            //构建Marker图标
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.jizhan1);
                            //构建MarkerOption，用于在地图上添加Marker

                            extraInfo.putString("lat", String.valueOf(positionA.latitude));
                            extraInfo.putString("lon", String.valueOf(positionA.longitude));

                            extraInfo.putString("address", "无");
                            OverlayOptions optiona = new MarkerOptions()
                                    .anchor(10, 30)
                                    .extraInfo(extraInfo)
                                    .position(positionA) //必传参数
                                    .perspective(true)
                                    .icon(bitmap) //必传参数
                                    .draggable(true)
                                    .draggable(true)
                                    //设置平贴地图，在地图中双指下拉查看效果
                                    .flat(true)
                                    .alpha(0.5f);
                            //在地图上添加Marker，并显示
                            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker
                            if (fugaiFlag == true) {
                                //覆盖范围
                                String tas = extraInfo.getString("Radius");
                                int is = Integer.parseInt(tas);
                                OverlayOptions ooCirclefugai = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(40, 255,
                                                165,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(2, Color.rgb(255,
                                                165,
                                                0)))
                                        .radius(is);
                                mBaiduMap.addOverlay(ooCirclefugai);
                                return;
                            }
//

                        } else {
                            Bundle extraInfo = marker.getExtraInfo();
                            //详细地址
                            address_reverseGeoCodeResult = reverseGeoCodeResult.getAddress();
                            Log.d(TAG, "onGetReverseGeoCodeResult: " + address_reverseGeoCodeResult);
                            mBaiduMap.clear();
                            Log.d(TAG, "onMarkerDragEnd: " + positionA);
                            String ta1 = extraInfo.getString("ta");
                            List<Double> lists = MyUtils.StringTolist(ta1);
                            int sum = 0;
                            for (int j = 0; j < lists.size(); j++) {
                                sum += lists.get(j);
                            }

                            int size = lists.size();
                            double sum_db = sum;
                            double size_db = size;
                            double Myradius_db = sum_db / size_db * 78;
                            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
                            int Myradius = new Double(Myradius_db).intValue();
                            Log.d(TAG, "JingBaos:Myradius " + Myradius);
                            //平均值的圈
                            if (UNIFORMTA == true) {
                                OverlayOptions ooCirclepingjun = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(40, 255,
                                                0,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(10, Color.rgb(255,
                                                0,
                                                0)))
                                        .radius(Myradius);
                                mBaiduMap.addOverlay(ooCirclepingjun);
                                Log.e(TAG, "DataAll:最大 " + Collections.max(lists));
                            }

                            //最大ta圈
                            if (MAXTA == true) {
                                OverlayOptions ooCircleaMAx = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(0, 255,
                                                0,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(2, Color.rgb(255,
                                                0,
                                                0)))
                                        .radius((int) (Collections.max(lists) * 78));

                                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
//            Log.d("nzq", "onCreate: " + center);
                                mBaiduMap.addOverlay(ooCircleaMAx);
                            }
                            Log.d(TAG, "DataAllsum: " + sum);
                            //最小ta圈
                            if (MINTA == true) {
                                OverlayOptions ooCircleaMin = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(0, 255,
                                                0,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(2, Color.rgb(255,
                                                0,
                                                0)))
                                        .radius((int) (Collections.min(lists) * 78));

                                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
//            Log.d("nzq", "onCreate: " + center);
                                mBaiduMap.addOverlay(ooCircleaMin);
                            }
                            //小圆
//                    LatLng llDot = new LatLng(39.90923, 116.447428);
                            OverlayOptions ooDot = new DotOptions().center(positionA).radius(6).color(0xFF0000FF);
                            mBaiduMap.addOverlay(ooDot);
                            //构建Marker图标
                            BitmapDescriptor bitmap = BitmapDescriptorFactory
                                    .fromResource(R.drawable.jizhan1);
                            //构建MarkerOption，用于在地图上添加Marker


                            extraInfo.putString("lat", String.valueOf(positionA.latitude));
                            extraInfo.putString("lon", String.valueOf(positionA.longitude));

                            extraInfo.putString("address", address_reverseGeoCodeResult);
                            OverlayOptions optiona = new MarkerOptions()
                                    .anchor(10, 30)
                                    .extraInfo(extraInfo)
                                    .position(positionA) //必传参数
                                    .perspective(true)
                                    .icon(bitmap) //必传参数
                                    .draggable(true)
                                    .draggable(true)
                                    //设置平贴地图，在地图中双指下拉查看效果
                                    .flat(true)
                                    .alpha(0.5f);
                            //在地图上添加Marker，并显示
                            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker
                            //覆盖范围
                            if (fugaiFlag == true) {
                                String tas = extraInfo.getString("Radius");
                                int is = Integer.parseInt(tas);
                                OverlayOptions ooCirclefugai = new CircleOptions()
//                            .fillColor(0x000000FF)
                                        .fillColor(Color.argb(40, 255,
                                                165,
                                                0))
                                        .center(positionA)
                                        .stroke(new Stroke(2, Color.rgb(255,
                                                165,
                                                0)))
                                        .radius(is);
                                mBaiduMap.addOverlay(ooCirclefugai);
                            }

                            //行政区号
                            int adCode = reverseGeoCodeResult.getCityCode();
                        }
                    }
                });
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(positionA).radius(1000).newVersion(1));


            }

            //在Marker开始被拖拽时回调此方法， 这个Marker的位可以通过getPosition()方法获取
            //marker 被拖拽的Marker对象
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });
        ViewLoading.dismiss(MainActivity.this);

        mBaiduMap.setIndoorEnable(true);//打开室内图，默认为关闭状态
        mBaiduMap.setOnBaseIndoorMapListener(new BaiduMap.OnBaseIndoorMapListener() {
            @Override
            public void onBaseIndoorMapMode(boolean on, MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {
                if (on) {
                    // 进入室内图
                    // 通过获取回调参数 mapBaseIndoorMapInfo 便可获取室内图信
                    //息，包含楼层信息，室内ID等
                } else {
                    // 移除室内图
                }
            }
        });
    }

    public void initdatas() {
        //查询已存储的基站

        List<GuijiViewBeanjizhan> resultBeans = null;
        try {
            mBaiduMap.clear();
            resultBeans = dbManagerJZ.guijiViewBeans();
            Log.d(TAG, "查询到的resultBeansonCreate: " + resultBeans);
            Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
            if (resultBeans.size() > 0 && resultBeans != null) {
                mBaiduMap.clear();
                DataAll(resultBeans);
            } else {
                Log.d(TAG, "initdatas: aa" + "1111");
//                Toast.makeText(MainActivity.this, "请先添加基站", Toast.LENGTH_LONG).show();
//                MyToast.showToast("请先添加基站");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initdatas2() {
        //查询已存储的基站

        List<GuijiViewBeanjizhan> resultBeans = null;
        try {
            mBaiduMap.clear();
            resultBeans = dbManagerJZ.guijiViewBeans();
            Log.d(TAG, "查询到的resultBeansonCreate: " + resultBeans);
            Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
            if (resultBeans.size() > 0 && resultBeans != null) {
                mBaiduMap.clear();
                DataAll(resultBeans);
            } else {
//                Toast.makeText(MainActivity.this, "请先添加基站", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initdatas3() {
        //查询已存储的基站

        List<GuijiViewBeanjizhan> resultBeans = null;
        try {
//            mBaiduMap.clear();
            resultBeans = dbManagerJZ.guijiViewBeans();
            Log.d(TAG, "查询到的resultBeansonCreate: " + resultBeans);
            Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
            if (resultBeans.size() > 0 && resultBeans != null) {
                mBaiduMap.clear();
                DataAll(resultBeans);
            } else {
//                Toast.makeText(MainActivity.this, "请先添加基站", Toast.LENGTH_LONG).show();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void JzGetData(double add) {
        double daoxianData_zoomset = mBaiduMap.getMapStatus().zoom;
        if (xunFlag == true) {
            Log.d(TAG, "JzGetDatabusiness: " + BUSINESS);
            if (daoxianData_zoomset > 18) {
                RetrofitFactory.getInstence().API().JzData(String.valueOf(BUSINESS),
                        String.valueOf(zuoxiaLatLng.longitude - add),
                        String.valueOf(zuoxiaLatLng.latitude),
                        String.valueOf(youxiaaLatLng.longitude + add),
                        String.valueOf(youxiaaLatLng.latitude + 1.3f * add)


                ).enqueue(new Callback<JzGetData>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<JzGetData> call, Response<JzGetData> response) {
                        Log.e(TAG, "daoxian: " + response.body().toString());
                        jzGetData = response.body();

                        if (jzGetData.getCode() == 1 && jzGetData.getData().size() > 0 && jzGetData.getData() != null) {
                            Log.d(TAG, "onResponse:jzgetData" + "有数据啦" + jzGetData.getData().size());
                            Log.d(TAG, "onResponse:当前数据长度" + jzGetData.getData().size());

                            tv_screen.setText(jzGetData.getData().size() + "条");

                            for (int i = 0; i < jzGetData.getData().size(); i++) {
                                LatLng latLng = new LatLng(jzGetData.getData().get(i).getLatitude(), jzGetData.getData().get(i).getLongitude());
                                CoordinateConverter converter = new CoordinateConverter()
                                        .from(CoordinateConverter.CoordType.GPS)
                                        .coord(latLng);
                                //转换坐标 84转百度09
                                LatLng desLatLngBaidus = converter.convert();

                                Bundle bundle = new Bundle();
                                //计算eci
                                int ECIS = jzGetData.getData().get(i).getENodeBid() * 256 + jzGetData.getData().get(i).getAreaMark();
                                DecimalFormat df = new DecimalFormat("#.000000");
                                String lat = df.format(desLatLngBaidus.latitude);
                                String lon = df.format(desLatLngBaidus.longitude);
                                bundle.putString("address", jzGetData.getData().get(i).getAreaName() + "");
                                bundle.putString("ci", ECIS + "");
                                bundle.putString("mcc", "");
                                bundle.putString("mnc", jzGetData.getData().get(i).getMnc() + "");
                                bundle.putString("type", "2");//2 代表没添加过的数据是当前屏幕获得的
                                bundle.putString("Radius", "0");
                                bundle.putString("lac", jzGetData.getData().get(i).getTac() + "");
                                bundle.putString("lat", lat);
                                bundle.putString("lon", lon);
                                //
                                bundle.putString("resources", "内部数据");
                                bundle.putString("band", jzGetData.getData().get(i).getBand() + "");
                                bundle.putString("types", jzGetData.getData().get(i).getType() + "");
                                bundle.putString("pci", jzGetData.getData().get(i).getPci() + "");
                                bundle.putString("down", jzGetData.getData().get(i).getDownFrequencyPoint() + "");
                                BitmapDescriptor bitmap = BitmapDescriptorFactory
                                        .fromResource(R.drawable.jizhan1);
                                //构建MarkerOption，用于在地图上添加Marker
                                OverlayOptions optiona = new MarkerOptions()
                                        .extraInfo(bundle)
                                        .anchor(10, 30)
                                        .position(desLatLngBaidus) //必传参数
                                        .perspective(true)
                                        .icon(bitmap) //必传参数
                                        .draggable(true)
                                        .draggable(true)
                                        //设置平贴地图，在地图中双指下拉查看效果
                                        .flat(true)
                                        .alpha(0.5f);
                                //在地图上添加Marker，并显示
                                markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker
                            }

                        } else {
                            tv_screen.setText(0 + "条");
                        }


                    }

                    @Override
                    public void onFailure(Call<JzGetData> call, Throwable t) {

                    }
                });
            }
        }

    }

    //数据库基站数据
    private void DataAll(List<GuijiViewBeanjizhan> resultBeans) {

        mBaiduMap.clear();
        for (int i = 0; i < resultBeans.size(); i++) {
            Bundle bundle = new Bundle();
            bundle.putString("band", resultBeans.get(i).getBand() + "");
            bundle.putString("types", resultBeans.get(i).getTypes() + "");
            bundle.putString("pci", resultBeans.get(i).getPci() + "");
            bundle.putString("down", resultBeans.get(i).getDown() + "");
            //上面新增
            bundle.putString("id", resultBeans.get(i).getId() + "");
            bundle.putString("address", resultBeans.get(i).getAddress());
            bundle.putString("ci", resultBeans.get(i).getCi());
            bundle.putString("mcc", resultBeans.get(i).getMcc());
            bundle.putString("mnc", resultBeans.get(i).getMnc());
            bundle.putString("type", resultBeans.get(i).getType() + "");
            bundle.putString("Radius", resultBeans.get(i).getRadius() + "");
            bundle.putString("lac", resultBeans.get(i).getLac());
            bundle.putString("lat", resultBeans.get(i).getLat());
            bundle.putString("lon", resultBeans.get(i).getLon());
            bundle.putString("ta", resultBeans.get(i).getTa());
            bundle.putString("sid", resultBeans.get(i).getSid());
            bundle.putString("resources", resultBeans.get(i).getResources() + "");
            String latresult = resultBeans.get(i).getLat();
            String lonresult = resultBeans.get(i).getLon();
            LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
            Log.d(TAG, "dataBeanisShowjizhan: " + resultBeans);
//            LatLng latLngresult = new LatLng(Double.parseDouble("38.031242"), Double.parseDouble("114.450186"));
            //判断是否已经转换  //  内部数据时已经转换为百度坐标系

//            if (resultBeans.get(i).getResources().equals("内部数据")) {
//
//            }
            CoordinateConverter converter = new CoordinateConverter()
                    .from(CoordinateConverter.CoordType.GPS)
                    .coord(latLngresult);
            //转换坐标
            LatLng desLatLngBaidu = new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon()));
            LatLng desLatLngBaiduD = new LatLng(Double.parseDouble(resultBeans.get(i).getLat()) - 0.00002, Double.parseDouble(resultBeans.get(i).getLon()));


//构建TextOptions对象
            OverlayOptions mTextOptions = new TextOptions()
                    .text(resultBeans.get(i).getLac() + "-" + resultBeans.get(i).getCi()) //文字内容
//                    .bgColor(getResources().getColor(R.color.color_f25057)) //背景色
                    .fontSize(32) //字号
                    .fontColor(getResources().getColor(R.color.color_f25057)) //文字颜色
                    .rotate(0) //旋转角度
                    .position(desLatLngBaiduD);

//在地图上显示文字覆盖物
            Overlay mText = mBaiduMap.addOverlay(mTextOptions);


            //画大圆
            int TAS = 78;
            String ta = "";
            if (TextUtils.isEmpty(resultBeans.get(i).getTa() + "")) {
                ta = "1";
            } else {
                ta = resultBeans.get(i).getTa() + "";
            }
            List<Double> lists = MyUtils.StringTolist(resultBeans.get(i).getTa());
            Double sum = Double.valueOf(0);
            for (int j = 0; j < lists.size(); j++) {
                sum += lists.get(j);
            }
            //平均值的圈
            int size = lists.size();
            double sum_db = sum;
            double size_db = size;
            double Myradius_db = sum_db / size_db * 78;

            Log.d(TAG, "DataAlla: " + sum_db + "--" + size_db);
            Log.d(TAG, "DataAll: aaa0" + sum_db / size_db * 78);
            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
            int Myradius = new Double(Myradius_db).intValue();
            Log.d(TAG, "JingBaos:Myradius " + Myradius);
            if (UNIFORMTA == true) {
                OverlayOptions ooCirclepingjun = new CircleOptions()
//                            .fillColor(0x000000FF)
                        .fillColor(Color.argb(40, 255,
                                0,
                                0))
                        .center(desLatLngBaidu)
                        .stroke(new Stroke(5, Color.rgb(255,
                                0,
                                0)))
                        .radius((int) Myradius_db);
                mBaiduMap.addOverlay(ooCirclepingjun);
                Log.e(TAG, "DataAll:最大 " + Collections.max(lists));
                Log.d(TAG, "DataAlla:pingjun" + (int) Myradius_db);
            }

            //最大ta圈
            if (MAXTA == true) {
                OverlayOptions ooCircleaMAx = new CircleOptions()
//                            .fillColor(0x000000FF)
                        .fillColor(Color.argb(0, 255,
                                0,
                                0))
                        .center(desLatLngBaidu)
                        .stroke(new Stroke(2, Color.rgb(255,
                                0,
                                0)))
                        .radius((int) (Collections.max(lists) * 78));
                Log.d(TAG, "DataAlla:MAXTA" + (int) (Collections.max(lists) * 78));
                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
//            Log.d("nzq", "onCreate: " + center);
                mBaiduMap.addOverlay(ooCircleaMAx);
            }
            Log.d(TAG, "DataAllsum: " + sum);
            //最小ta圈
            if (MINTA == true) {
                OverlayOptions ooCircleaMin = new CircleOptions()
//                            .fillColor(0x000000FF)
                        .fillColor(Color.argb(0, 255,
                                0,
                                0))
                        .center(desLatLngBaidu)
                        .stroke(new Stroke(2, Color.rgb(255,
                                0,
                                0)))
                        .radius((int) (Collections.min(lists) * 78));
                Log.d(TAG, "DataAlla:min" + (int) (Collections.min(lists) * 78));
                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
//            Log.d("nzq", "onCreate: " + center);
                mBaiduMap.addOverlay(ooCircleaMin);
            }
            Log.d(TAG, "listDataAll: " + lists.get(0));

            //小圆
//                    LatLng llDot = new LatLng(39.90923, 116.447428);
            OverlayOptions ooDot = new DotOptions().center(desLatLngBaidu).radius(6).color(0xFF0000FF);
            mBaiduMap.addOverlay(ooDot);
            //构建Marker图标
            BitmapDescriptor bitmap;
            if (resultBeans.get(i).getType() == 1) {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.jizhan2);
            } else {
                bitmap = BitmapDescriptorFactory
                        .fromResource(R.drawable.jizhan1);
            }


            //构建MarkerOption，用于在地图上添加Marker
            OverlayOptions optiona = new MarkerOptions()
                    .anchor(10, 30)
                    .extraInfo(bundle)
                    .position(desLatLngBaidu) //必传参数
                    .perspective(true)
                    .icon(bitmap) //必传参数
                    .draggable(true)
                    .draggable(true)
                    //设置平贴地图，在地图中双指下拉查看效果
                    .flat(true)
                    .alpha(0.5f);
            //在地图上添加Marker，并显示
            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker

            //覆盖范围
            String tas = resultBeans.get(i).getRadius();
            int is = 0;
            if (!TextUtils.isEmpty(tas)) {
                is = Integer.parseInt(tas);
            }

//            int iss = 0;
//            String tass = resultBeans.get(i).getRadius();
//            if (!TextUtils.isEmpty(tass)) {
//                is = Integer.parseInt(tas);
//            }


            if (fugaiFlag == true) {

                OverlayOptions ooCirclefugai = new CircleOptions()
//                            .fillColor(0x000000FF)
                        .fillColor(Color.argb(40, 255,
                                165,
                                0))
                        .center(desLatLngBaidu)
                        .stroke(new Stroke(2, Color.rgb(255,
                                165,
                                0)))
                        .radius(is);
                mBaiduMap.addOverlay(ooCirclefugai);

            }
            if (!TextUtils.isEmpty(ACacheUtil.getFugaiKG())) {
                int kg = Integer.parseInt(ACacheUtil.getFugaiKG());
                if (kg == 0) {//0 展示覆盖范围 其余不展示
                    OverlayOptions ooCirclefugai = new CircleOptions()
//                            .fillColor(0x000000FF)
                            .fillColor(Color.argb(40, 255,
                                    165,
                                    0))
                            .center(desLatLngBaidu)
                            .stroke(new Stroke(2, Color.rgb(255,
                                    165,
                                    0)))
                            .radius(is);
                    mBaiduMap.addOverlay(ooCirclefugai);
                }
            }

        }


    }

    private void initTTS() {
        //实例化自带语音对象
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT) {
            textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == textToSpeech.SUCCESS) {
                        // Toast.makeText(MainActivity.this,"成功输出语音",
                        // Toast.LENGTH_SHORT).show();
                        // Locale loc1=new Locale("us");
                        // Locale loc2=new Locale("china");

                        textToSpeech.setPitch(1.0f);//方法用来控制音调
                        textToSpeech.setSpeechRate(1.0f);//用来控制语速

                        //判断是否支持下面两种语言
                        int result1 = textToSpeech.setLanguage(Locale.US);
                        int result2 = textToSpeech.setLanguage(Locale.
                                SIMPLIFIED_CHINESE);
                        boolean a = (result1 == TextToSpeech.LANG_MISSING_DATA || result1 == TextToSpeech.LANG_NOT_SUPPORTED);
                        boolean b = (result2 == TextToSpeech.LANG_MISSING_DATA || result2 == TextToSpeech.LANG_NOT_SUPPORTED);

                        Log.i("zhh_tts", "US支持否？--》" + a +
                                "\nzh-CN支持否》--》" + b);

                    } else {
                        MyToast.showToast("数据丢失或不支持");
                        //                    Toast.makeText(MainActivity.this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void startAuto(String data) {
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        textToSpeech.setPitch(1.0f);
        // 设置语速
        textToSpeech.setSpeechRate(3.01f);
        textToSpeech.speak(data,//输入中文，若不支持的设备则不会读出来
                TextToSpeech.QUEUE_FLUSH, null);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setMapMakerView(final Marker marker) {
        Log.e("yltddddd", "setMapMakerView: ");
        final Bundle extraInfo = marker.getExtraInfo();
        if (!TextUtils.isEmpty(extraInfo.getString("address"))) {
            String address = extraInfo.getString("address");
            Log.d(TAG, "onMarkerClick: " + address);
            final String Mylatitude = marker.getPosition().latitude + "";
            final String Mylongitude = marker.getPosition().longitude + "";

            View view = View.inflate(MainActivity.this, R.layout.activity_map_info, null);
            TextView tv_title = view.findViewById(R.id.tv_title);
            String str = "";
            if (extraInfo.getString("mnc").equals("0")) {
                str = "移动";
            } else if (extraInfo.getString("mnc").equals("1")) {
                str = "联通";
            } else if (extraInfo.getString("mnc").equals("11")) {
                str = "电信";
            } else if (TextUtils.isEmpty(extraInfo.getString("mnc"))) {//如果是cdma显示 sid数据
                str = "";
                TextView tv_sid = view.findViewById(R.id.tv_sid);
                tv_sid.setText(extraInfo.getString("sid"));
                LinearLayout llsid = view.findViewById(R.id.llsid);
                llsid.setVisibility(View.VISIBLE);
            } else {
                str = "CDMA";
                TextView tv_sid = view.findViewById(R.id.tv_sid);
                tv_sid.setText(extraInfo.getString("sid"));
                LinearLayout llsid = view.findViewById(R.id.llsid);
                llsid.setVisibility(View.VISIBLE);
            }
            if (extraInfo.get("resources").equals("内部数据")) {//如果是内部数据显示 band pci 覆盖类型 down
                LinearLayout ll_types = view.findViewById(R.id.ll_types);
                LinearLayout ll_pci = view.findViewById(R.id.ll_pci);
                LinearLayout ll_band = view.findViewById(R.id.ll_band);
                LinearLayout ll_down = view.findViewById(R.id.ll_down);
                ll_types.setVisibility(View.VISIBLE);
                ll_pci.setVisibility(View.VISIBLE);
                ll_band.setVisibility(View.VISIBLE);
                ll_down.setVisibility(View.VISIBLE);

                TextView tv_band = view.findViewById(R.id.tv_band);
                TextView tv_types = view.findViewById(R.id.tv_types);
                TextView tv_pci = view.findViewById(R.id.tv_pci);
                TextView tv_down = view.findViewById(R.id.tv_down);
                tv_band.setText(extraInfo.get("band") + "");
                tv_types.setText(extraInfo.get("types") + "");
                tv_pci.setText(extraInfo.get("pci") + "");
                tv_down.setText(extraInfo.get("down") + "");


            }
//            aaa
            tv_title.setText(str + "");
            TextView tv_fugai = view.findViewById(R.id.tv_fugai);
            tv_fugai.setText(extraInfo.getString("Radius") + "");
            Log.d(TAG, "setMapMakerViaew: " + extraInfo.getString("Radius"));
            TextView tv_mnc = view.findViewById(R.id.tv_mnc);
            tv_mnc.setText(extraInfo.getString("mnc"));
            TextView tv_lac = view.findViewById(R.id.tv_lac);
            tv_lac.setText(extraInfo.getString("lac"));
            TextView tv_cid = view.findViewById(R.id.tv_cid);
            tv_cid.setText(extraInfo.getString("ci"));
            TextView tv_address = view.findViewById(R.id.tv_address);
            tv_address.setText(extraInfo.getString("address"));
            TextView tv_lat_lon = view.findViewById(R.id.tv_lat_lon);

            DecimalFormat df = new DecimalFormat(".######");
            final String lats = extraInfo.getString("lat");
            final String lons = extraInfo.getString("lon");
            double dlat = Double.parseDouble(lats);
            double dlons = Double.parseDouble(lons);
            tv_lat_lon.setText("纬度:" + df.format(dlat) + ",经度:" + df.format(dlons));

            TextView tv_resources = view.findViewById(R.id.tv_resources);

            tv_resources.setText(extraInfo.getString("resources"));
            ImageButton bt_openMap = view.findViewById(R.id.bt_openMap);
            Button bt_quanjing = view.findViewById(R.id.bt_quanjing);
            bt_m_locations = view.findViewById(R.id.bt_m_location);//设为警报的点
            if (extraInfo.get("type").equals("1")) {
                bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
            } else {
                bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojinglan1));
            }
            bt_m_locations.setVisibility(View.VISIBLE);
            bt_m_locations.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "extraInfoidonClick: " + extraInfo.getString("id"));

                    new CircleDialog.Builder()
                            .setTitle("")
                            .setText("确定要设为报警基站吗")
                            .setTitleColor(Color.parseColor("#00acff"))
                            .setNegative("确定", new Positiv(4, extraInfo))
                            .setPositive("取消", null)
                            .show(getSupportFragmentManager());
                }
            });
            final Button bt_taset = view.findViewById(R.id.bt_taset);//TA值改变数据
            bt_taset.setVisibility(View.VISIBLE);
            bt_taset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("id", String.valueOf(extraInfo.get("id")));
                    Intent intent = new Intent(MainActivity.this, TaActivity.class);
                    intent.putExtras(bundle);
//                    startActivity(intent);
                    startActivityForResult(intent, 13);
                }
            });
            Button bt_m_dele = view.findViewById(R.id.bt_m_dele);
            bt_m_dele.setVisibility(View.VISIBLE);
            if (extraInfo.get("type").equals("2")) {
                bt_m_dele.setBackground(getResources().getDrawable(R.mipmap.markeradd));
                bt_taset.setVisibility(View.GONE);
                bt_m_locations.setVisibility(View.GONE);
            }
            bt_m_dele.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (extraInfo.get("type").equals("2")) {//是要增加的基站
                        listMarker.clear();
                        demoAdapteradd = new DemoAdapteradd(listMarker, addcallBack);
                        Myshow.show(MainActivity.this, extraInfo, dbManagerJZ, mycallback, addcallBack, listMarker, demoAdapteradd);


                    } else {
                        try {
                            String dele = extraInfo.getString("id");
                            new CircleDialog.Builder()
                                    .setTitle("")
                                    .setText("确定要删除基站吗")
                                    .setTitleColor(Color.parseColor("#00acff"))
                                    .setNegative("确定", new Positiv(3, dele))
                                    .setPositive("取消", null)
                                    .show(getSupportFragmentManager());

                        } catch (Exception e) {
                            Log.d(TAG, "panderonClick: " + "3" + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                }
            });
            ImageButton iv_finishs = view.findViewById(R.id.iv_finishs);
            iv_finishs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBaiduMap.hideInfoWindow();

                }
            });
            bt_openMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    //底部弹出窗

                    dialog2 = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                    //填充对话框的布局
                    inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dibushowdaohao, null);
                    //初始化控件
                    TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
//        choosePhoto.setVisibility(View.GONE);
                    TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
//                            baidu.setOnClickListener(new MyonclickXian(mylag));
                    baidu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                LatLng startLatLng = new LatLng(39.940387, 116.29446);
                                LatLng endLatLng = new LatLng(39.87397, 116.529025);
                                String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
                                                "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
                                        marker.getPosition().latitude, marker.getPosition().longitude);
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
//                    ToastUtil.showShort(this, "请安装百度地图");
//                                Toast.makeText(MainActivity.this, "请安装百度地图", Toast.LENGTH_SHORT).show();
                                MyToast.showToast("请安装百度地图");
                            }
                        }
                    });
                    gaode.setOnClickListener(new MyonclickXian(mylag, String.valueOf(marker.getPosition().latitude), String.valueOf(marker.getPosition().longitude), MainActivity.this));
                    //将布局设置给Dialog
                    dialog2.setContentView(inflate);
                    //获取当前Activity所在的窗体
                    Window dialogWindow = dialog2.getWindow();
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity(Gravity.BOTTOM);
                    //获得窗体的属性
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                    dialogWindow.setAttributes(lp);
                    dialog2.show();//显示对话框
                }
            });
            bt_quanjing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, PanoramaDemoActivityMain.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", String.valueOf(marker.getPosition().latitude));
                    bundle.putString("lon", String.valueOf(marker.getPosition().longitude));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            final InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
            mBaiduMap.showInfoWindow(mInfoWindow);
        } else {
            //非查询基站的标记

            final LatLng sl = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
            String address = extraInfo.getString("address");
            Log.d(TAG, "onMarkerClick: " + address);
            final String Mylatitude = marker.getPosition().latitude + "";
            final String Mylongitude = marker.getPosition().longitude + "";

            final View view = View.inflate(MainActivity.this, R.layout.activity_map_info, null);
            TextView title = view.findViewById(R.id.title);
            title.setText("标记点信息");
            TextView tv_mnc = view.findViewById(R.id.tv_mnc);
            tv_mnc.setText("");
            TextView tv_lac = view.findViewById(R.id.tv_lac);
            tv_lac.setText("");
            TextView tv_cid = view.findViewById(R.id.tv_cid);
            tv_cid.setText("");
            final TextView tv_address = view.findViewById(R.id.tv_address);
//            tv_address.setText(extraInfo.getString("address"));
            TextView tv_lat_lon = view.findViewById(R.id.tv_lat_lon);
            ImageButton iv_finishs = view.findViewById(R.id.iv_finishs);
            Button bt_m_location = view.findViewById(R.id.bt_m_location);//设为警报的点
            bt_m_location.setVisibility(View.GONE);
            iv_finishs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mBaiduMap.hideInfoWindow();
                }
            });
            mSearch.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

                }

                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                        //没有找到检索结果
                        Log.d(TAG, "onGetReverseGeoCodeResult: " + "没有找到检索结果");
                        Log.d(TAG, "onMarkerDragEnd: " + sl);
                        tv_address.setText("无");
                        return;
                    } else {
                        //详细地址
                        address_reverseGeoCodeResult = reverseGeoCodeResult.getAddress();
                        tv_address.setText(address_reverseGeoCodeResult);
                    }
                }
            });
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(sl).radius(1000).newVersion(1));


            DecimalFormat df = new DecimalFormat(".######");
            final String lats = extraInfo.getString("lat");
            final String lons = extraInfo.getString("lon");
            double dlat = marker.getPosition().latitude;
            double dlons = marker.getPosition().longitude;
            tv_lat_lon.setText("纬度:" + df.format(dlat) + ",经度:" + df.format(dlons));
            ImageButton bt_openMap = view.findViewById(R.id.bt_openMap);
            Button bt_quanjing = view.findViewById(R.id.bt_quanjing);

            bt_openMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //底部弹出窗

                    dialog2 = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                    //填充对话框的布局
                    inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dibushowdaohao, null);
                    //初始化控件
                    TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
//        choosePhoto.setVisibility(View.GONE);
                    TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
//                            baidu.setOnClickListener(new MyonclickXian(mylag));
                    baidu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                LatLng startLatLng = new LatLng(39.940387, 116.29446);
                                LatLng endLatLng = new LatLng(39.87397, 116.529025);
                                String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
                                                "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
                                        marker.getPosition().latitude, marker.getPosition().longitude);
                                Intent intent = new Intent();
                                intent.setData(Uri.parse(uri));
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
//                    ToastUtil.showShort(this, "请安装百度地图");
//                                Toast.makeText(MainActivity.this, "请安装百度地图", Toast.LENGTH_SHORT).show();
                                MyToast.showToast("请安装百度地图");
                            }
                        }
                    });

                    gaode.setOnClickListener(new MyonclickXian(sl, Mylatitude, Mylongitude, MainActivity.this));
                    //将布局设置给Dialog
                    dialog2.setContentView(inflate);
                    //获取当前Activity所在的窗体
                    Window dialogWindow = dialog2.getWindow();
                    //设置Dialog从窗体底部弹出
                    dialogWindow.setGravity(Gravity.BOTTOM);
                    //获得窗体的属性
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                    dialogWindow.setAttributes(lp);
                    dialog2.show();//显示对话框
                }
            });
            bt_quanjing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, PanoramaDemoActivityMain.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("lat", String.valueOf(marker.getPosition().latitude));
                    bundle.putString("lon", String.valueOf(marker.getPosition().longitude));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            final InfoWindow mInfoWindow = new InfoWindow(view, marker.getPosition(), -47);
            mBaiduMap.showInfoWindow(mInfoWindow);
//构造InfoWindow
//point 描述的位置点
//-100 InfoWindow相对于point在y轴的偏移量
            final InfoWindow mInfoWindows = new InfoWindow(view, marker.getPosition(), -47);

//使InfoWindow生效
            mBaiduMap.showInfoWindow(mInfoWindows);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void Mapinit(int i) {//百度地图跟随状态
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务
        if (i == 1) {
            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//普通态
        }
        if (i == 2) {
            mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//跟随态
        }
//        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
//        MapStatus.Builder builder1 = new MapStatus.Builder();
//        builder1.overlook(0);
//        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void findView() throws Exception {
        bt_jizhan = findViewById(R.id.bt_jizhan);
        bt_jizhan.setOnClickListener(this);//基站的监听
        bt_qiehuan = findViewById(R.id.bt_qiehuan);
        bt_qiehuan.setOnClickListener(this);//切换的监听
        bt_clear = findViewById(R.id.bt_clear);
        bt_clear.setOnClickListener(this);
        bt_location = findViewById(R.id.bt_location);
        bt_location.setOnClickListener(this);
        bt_jt = findViewById(R.id.bt_jt);
        bt_jt.setOnClickListener(this);
        bt_hot = findViewById(R.id.bt_hot);
        bt_hot.setOnClickListener(this);
        bt_juli = findViewById(R.id.bt_juli);
        bt_juli.setOnClickListener(this);
        bt_jingbao = findViewById(R.id.bt_jingbao);
        bt_jingbao.setOnClickListener(this);
        bt_jia = findViewById(R.id.bt_jia);
        bt_jia.setOnClickListener(this);
        bt_jian = findViewById(R.id.bt_jian);
        bt_jian.setOnClickListener(this);
        //新Ui的View ID
        ll_qiehuan = findViewById(R.id.ll_qiehuan);
        ll_qiehuan.setOnClickListener(this);//切换图层的监听
        ll_clear = findViewById(R.id.ll_clear);
        ll_clear.setOnClickListener(this);//清空地图的监听
        ll_jt = findViewById(R.id.ll_jt);
        ll_jt.setOnClickListener(this);//切换交通的监听
        ll_rl = findViewById(R.id.ll_rl);
        ll_rl.setOnClickListener(this);//切换热力图的监听
        ll_baojing = findViewById(R.id.ll_baojing);
        ll_baojing.setOnClickListener(this);//切换报警的监听
//        Button
        bt_uilocation = findViewById(R.id.bt_uilocation);//当前位置
        bt_uilocation.setOnClickListener(this);
        bt_uilocation.setOnLongClickListener(view -> {
            Log.d(TAG, "onLongClick: " + "zoule");
            if (MapinitFlag == false) {

                Mapinit(2);
                MapinitFlag = true;
//                    Toast.makeText(MainActivity.this, "切换为跟随状态", Toast.LENGTH_LONG).show();
                MyToast.showToast("切换为跟随状态");
            } else if (MapinitFlag = true) {
                Mapinit(1);
                MapinitFlag = false;
                MyToast.showToast("切换为普通状态");
//                   Toast.makeText(MainActivity.this, "切换为普通状态", Toast.LENGTH_LONG).show();
            }

            return false;
        });
        bt_uiceliang = findViewById(R.id.bt_uiceliang);//测量
        bt_uiceliang.setOnClickListener(this);
        bt_uisearch = findViewById(R.id.bt_uisearch);//查询
        bt_uisearch.setOnClickListener(this);
        bt_jizhan0 = findViewById(R.id.bt_jizhan0);
        bt_jizhan0.setOnClickListener(this);
        //ImageButton
//        ib_qiehuan, ib_clear,ib_jt,ib_rl,ib_baojing
        ib_qiehuan = findViewById(R.id.ib_qiehuan);
        ib_clear = findViewById(R.id.ib_clear);
        ib_jt = findViewById(R.id.ib_jt);
        ib_rl = findViewById(R.id.ib_rl);
        ib_baojing = findViewById(R.id.ib_baojing);
        ib_screen = findViewById(R.id.ib_screen);
        iv_gjstart = findViewById(R.id.iv_gjstart);
        iv_viewstart = findViewById(R.id.iv_viewstart);
        ib_gensui = findViewById(R.id.ib_gensui);
        ib_fugai = findViewById(R.id.ib_fugai);

        //轨迹查询，轨迹查看
        bt_text = findViewById(R.id.bt_text);//- 代表 不展示
        bt_text.setOnClickListener(this);//-
        bt_ikan = findViewById(R.id.bt_ikan);//-
        bt_ikan.setOnClickListener(this);
        bt_uiceliangclear = findViewById(R.id.bt_uiceliangclear);
        bt_uiceliangclear.setOnClickListener(this);//测量标记点的清空
        iv_set = findViewById(R.id.iv_set);//-
        iv_set.setOnClickListener(this);
        ll_guijis = findViewById(R.id.ll_guijis);
        ll_guijis.setOnClickListener(this);//轨迹记录开始
        ll_gjview = findViewById(R.id.ll_gjview);
        ll_gjview.setOnClickListener(this);//轨迹动画的开始
        ll_gjclear = findViewById(R.id.ll_gjclear);
        ll_gjclear.setOnClickListener(this);//轨迹的清空

        ll_gensui = findViewById(R.id.ll_gensui);
        ll_gensui.setOnClickListener(this);
        ll_fugai = findViewById(R.id.ll_fugai);
        ll_fugai.setOnClickListener(this);
        ll_screen = findViewById(R.id.ll_screen);//屏幕滑动时的数据
        ll_screen.setOnClickListener(this);
        tv_screen = findViewById(R.id.tv_screen);//如果滑动屏幕有数据显示条数
//        Mapinit(1);//百度地图定位初始化

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);//获取传感器管理服务

//        if (i == 1) {
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;//普通态
//        }
//        if (i == 2) {
//            mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;//跟随态
//        }
        // 开启定位图层
        //通过设置enable为true或false 选择是否显示比例尺

        mBaiduMap.setMyLocationEnabled(true);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        MapStatus.Builder builder1 = new MapStatus.Builder();
        builder1.overlook(0);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder1.build()));


        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        initTTS();//语音初始化
        MyUtils.setStatBar(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_screen:
                if (jzGetData.getData() != null && jzGetData.getData().size() > 0) {
                    Myshow.Showscreen(MainActivity.this, jzGetData, screencallback);
                } else {
//                    Toast.makeText(MainActivity.this, "当前数据为0条", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bt_jizhan:
//                Toast.makeText(this, "你点击了查询基站按钮", Toast.LENGTH_SHORT).show();
                showDoliag();
                break;
            case R.id.bt_qiehuan:
                qiehuan();
//                Toast.makeText(this, "你点击了切换地图按钮", Toast.LENGTH_SHORT).show();

                break;
            case R.id.bt_location:
//                Toast.makeText(this, "你点击了当前位置按钮", Toast.LENGTH_SHORT).show();
                initMap();

                break;
            case R.id.bt_clear:
//                Toast.makeText(this, "你点击清空地图", Toast.LENGTH_SHORT).show();
                mBaiduMap.clear();
                points.clear();
                distance = 0;
                break;

            case R.id.bt_jt:
//                Toast.makeText(this, "你点击交通地图", Toast.LENGTH_SHORT).show();
                setmapJT();
                break;
            case R.id.bt_hot:
//                Toast.makeText(this, "你点击热力地图", Toast.LENGTH_SHORT).show();
                setmapHot();
                break;

            case R.id.bt_juli:
//                Toast.makeText(this, "你点击测量距离", Toast.LENGTH_SHORT).show();
                setmapJuli();
                break;

            case R.id.bt_jingbao:
//                Toast.makeText(this, "你点击位置警报", Toast.LENGTH_SHORT).show();
                setjingbao();
                break;
            case R.id.ll_qiehuan:
//                Toast.makeText(MainActivity.this, "你点击了LL切换", Toast.LENGTH_LONG).show();
                qiehuan();
                break;
            case R.id.ll_clear:
                new CircleDialog.Builder()
                        .setTitle("")
                        .setText("确定要清空地图测量吗?")
                        .setTitleColor(Color.parseColor("#00acff"))
                        .setNegative("确定", new Positiv(0))
                        .setPositive("取消", null)

                        .show(getSupportFragmentManager());

                break;
            case R.id.ll_jt:
//                Toast.makeText(MainActivity.this, "你点击了LL交通", Toast.LENGTH_LONG).show();
                setmapJT();
                break;
            case R.id.ll_rl:
//                Toast.makeText(MainActivity.this, "你点击了LL热力", Toast.LENGTH_LONG).show();
                setmapHot();
                break;
            case R.id.ll_baojing:
//                Toast.makeText(MainActivity.this, "你点击了LL警报", Toast.LENGTH_LONG).show();
                setjingbao();
                break;
            case R.id.ll_gensui:
//                gensui();

                if (MapinitFlag == false) {
                    Mapinit(2);
                    MapinitFlag = true;

                    ib_gensui.setBackground(getResources().getDrawable(R.mipmap.gen2));
//                    Toast.makeText(MainActivity.this, "切换为跟随状态", Toast.LENGTH_LONG).show();
                } else if (MapinitFlag = true) {
                    Mapinit(1);
                    MapinitFlag = false;
//                    Toast.makeText(MainActivity.this, "切换为普通状态", Toast.LENGTH_LONG).show();
                    ib_gensui.setBackground(getResources().getDrawable(R.mipmap.gen1));
                }
                break;
            case R.id.bt_uilocation:
//                Toast.makeText(MainActivity.this, "你点击了LL当前位置", Toast.LENGTH_LONG).show();
                initMap();
//                mLocClient.requestLocation();
//                Mapinit();
                break;
            case R.id.bt_uiceliang:
//                Toast.makeText(MainActivity.this, "你点击了LL测量", Toast.LENGTH_LONG).show();
                setmapJuli();
                break;
            case R.id.bt_uisearch:

                int remainder = 1;
                if (!TextUtils.isEmpty(ACacheUtil.getNumberremainder())) {
                    remainder = Integer.parseInt(ACacheUtil.getNumberremainder());
                }
                if (remainder == 0) {
//                    Toast.makeText(MainActivity.this, "查询次数已用尽!", Toast.LENGTH_SHORT).show();
                    MyToast.showToast("查询次数已用尽!");
                    break;
                }
                list.clear();
                showDoliag();
                break;
            case R.id.bt_jia:
                float izoomjia = mBaiduMap.getMapStatus().zoom;
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                        .zoom(izoomjia + 1) //以当前缩放级别的基础上放大,
                        .build()));

                break;

            case R.id.bt_jian:
                float izoomjian = mBaiduMap.getMapStatus().zoom;
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder()
                        .zoom(izoomjian - 1) //以当前缩放级别的基础上放大,
                        .build()));
                break;

            case R.id.bt_adddilao:
                if (TextUtils.isEmpty(et_taclac.getText().toString())) {
//                    Toast.makeText(this, "TAC/LAC不能为空", Toast.LENGTH_SHORT).show();
                    MyToast.showToast("TAC/LAC不能为空");
                    break;
                }
                if (TextUtils.isEmpty(et_eci.getText().toString())) {
//                    Toast.makeText(this, "ECI不能为空", Toast.LENGTH_SHORT).show();
                    MyToast.showToast("ECI不能为空");
                    break;
                }
                if(jizhanFlag==4){
                    if(TextUtils.isEmpty(et_sid.getText().toString())){
                        MyToast.showToast("Sid不能为空");
                        break;
                    }
                }

//                if (TextUtils.isEmpty(et_ta.getText().toString())) {
//                    Toast.makeText(this, "TA不能为空", Toast.LENGTH_SHORT).show();
//                    break;
//                }
                if (list.size() > 0) {
                } else {
                    list.add(0.0);
//                    MyToast.showToast("请添加至少一个TA值");
                }
                //是否有类型未选中
                if (rb_yidong.isChecked() == false && rb_liantong.isChecked() == false && rb_ldainxin4.isChecked() == false && rb_cdma.isChecked() == false && rb_bdjz1.isChecked() == false && rb_bdjz2.isChecked() == false) {
                    Log.d(TAG, "onClick: " + rb_liantong.isChecked());
                    MyToast.showToast("请选择基站类型");
                    break;
                } else {
//                    Toast.makeText(MainActivity.this,"22",Toast.LENGTH_LONG).show();
                    saveIsShow();//保存发送的数据
//                    mBaiduMap.clear();
                    sendPost();
                    Log.d("2ilssDetail144", "onClick: ");
//
                }
                break;
            case R.id.iv_finish:
                dialog.dismiss();
                break;

            case R.id.ll_guijis://开始轨迹记录
                if (guijistart == false) {
                    guijistart = true;
//                    MyToast.showToast("开始轨迹记录");
                    Log.d(TAG, "guijistart: " + guijistart);
                    iv_gjstart.setBackground(getResources().getDrawable(R.mipmap.huizhi_0));
                    GuijiViewBean guijiViewBean = new GuijiViewBean();
                    if (mylag != null) {
                        guijiViewBean.setLon(mylag.longitude);
                        guijiViewBean.setLat(mylag.latitude);
                    }
                    try {
                        dbManagerGuijiView.insertStudent(guijiViewBean);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (guijistart == true) {
                    guijistart = false;
                    Log.d(TAG, "guijistart: " + guijistart);
//                    MyToast.showToast("关闭轨迹记录");
                    iv_gjstart.setBackground(getResources().getDrawable(R.mipmap.huizhi_1));
                }
                break;
            case R.id.ll_gjview://轨迹动画演示
//                Toast.makeText(MainActivity.this, "你点击了轨迹记录", Toast.LENGTH_LONG).show();
                if (guijiFlag == false) {//轨迹显示标识
                    if (jingbaoflag == true) {
                        MyToast.showToast("请先关闭报警");
                        break;
                    }
                    if (juliFlage == true) {
                        MyToast.showToast("请先关闭测量");
                        break;
                    }
//aaa
                    guijiFlag = true;
                    iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_0));
                    /**
                     * 刷新
                     */
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            System.out.println("Timer is running");
                            Message message = new Message();
                            handler.sendMessage(message);
                            message.what = 1;
                            Log.d(TAG, "handlerrun: " + 1);
                        }
                    }, 0, 1000);
//                    Viewgjs();//绘制
                } else if (guijiFlag == true) {
                    guijiFlag = false;
                    Log.d(TAG, "guijiFlagonClick: " + "关闭了");
                    iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_1));
                    mMapView.showZoomControls(false);
                    mBaiduMap.clear();
//                    MyUtils.Viewjizhan(markerMy, mBaiduMap, dataBean);
                    if (timer != null) {
                        timer.cancel();
                    }
                    initdatas2();
                }


                break;
            case R.id.ll_gjclear:
                if (guijiFlag == true) {
//
                    MyToast.showToast("请先关闭显示");
                    break;
                }
                new CircleDialog.Builder()
                        .setTitle("")
                        .setText("确定要清空轨迹记录吗")
                        .setTitleColor(Color.parseColor("#00acff"))
                        .setNegative("确定", new Positiv(2))
                        .setPositive("取消", null)
                        .show(getSupportFragmentManager());
                break;

            case R.id.bt_uiceliangclear:
                if (guijiFlag == true) {
                    MyToast.showToast("请先关闭显示");
                    break;
                }
//                Toast.makeText(MainActivity.this, "你点击了测量清空", Toast.LENGTH_LONG).show();
                new CircleDialog.Builder()
                        .setTitle("")
                        .setText("确定要清空测量标记吗?")
                        .setTitleColor(Color.parseColor("#00acff"))
                        .setNegative("确定", new Positiv(1))
                        .setPositive("取消", null)
                        .show(getSupportFragmentManager());
                break;
            case R.id.bt_ikan:
//                Toast.makeText(MainActivity.this, "你点击了轨迹查看", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, TrackShowDemo.class);
                startActivity(intent);
                break;

            case R.id.iv_set:
//                Toast.makeText(MainActivity.this, "你点击了设置", Toast.LENGTH_LONG).show();
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, view);
                Menu menu = popupMenu.getMenu();
                popupMenu.getMenuInflater().inflate(R.menu.menutc, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener((PopupMenu.OnMenuItemClickListener) MainActivity.this);
                popupMenu.show();
                break;

            case R.id.bt_adddilaomenu: //menu 确认弹出窗 确认按钮
                ACacheUtil.putjguitime(et_guijitime.getText().toString());//保存轨迹时间
                ACacheUtil.putjbaojingtime(et_baojingtime.getText().toString());//保存报警时间

                if (!TextUtils.isEmpty(ACacheUtil.getbaojingtime())) {
                    String getbaojingtime = ACacheUtil.getbaojingtime();
                    int as = Integer.parseInt(getbaojingtime);
                    ScanSpan = as * 1000;
                    LocationClientOption option = new LocationClientOption();
                    option.setOpenGps(true); // 打开gps
                    option.setCoorType("bd09ll"); // 设置坐标类型
                    option.setScanSpan(1000);//一秒更新一次
                    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
                    mLocClient.setLocOption(option);
                    mLocClient.start();
                } else {
                    LocationClientOption option = new LocationClientOption();
                    option.setOpenGps(true); // 打开gps
                    option.setCoorType("bd09ll"); // 设置坐标类型
                    option.setScanSpan(1000);//一秒更新一次
                    option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
                    mLocClient.setLocOption(option);
                    mLocClient.start();
                }
                ACacheUtil.putFugaiKG(et_kg.getText().toString());
                dialogmenu.dismiss();
                MyToast.showToast("保存成功");
                if (timer2 != null) {
                    if (jingbaoflag == true) {
                        timer2.cancel();
                        startJb();//保存后重新启动
                    }

                }

                break;
            case R.id.bt_qx://配置取消按钮
                dialogmenu.dismiss();
                break;

            case R.id.bt_jizhan0:
                initdatas();//基站数量查询
                break;

            case R.id.ll_fugai:

//                Toast.makeText(MainActivity.this, "你点击了基站覆盖", Toast.LENGTH_LONG).show();
                cover();//覆盖设置
                ////
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void cover() {
        if (fugaiFlag == false) {
            fugaiFlag = true;
            ib_fugai.setBackground(getResources().getDrawable(R.mipmap.radius1));
            initdatas();
        } else if (fugaiFlag == true) {
            fugaiFlag = false;
            ib_fugai.setBackground(getResources().getDrawable(R.mipmap.radius0));
            initdatas();
        }
    }

    private void gensui() {
//        ib_baojing.setBackground(getResources().getDrawable(R.mipmap.baojing_up));
        if (gensuiFlag == false) {

            gensuiFlag = true;
        } else if (gensuiFlag == true) {

            gensuiFlag = false;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void Viewgjs() {
        List<GuijiViewBean> guijiViewBeans = null;
        try {
            guijiViewBeans = dbManagerGuijiView.guijiViewBeans();
            Log.d(TAG, "Viewgjs: aa" + guijiViewBeans);
            if (guijiViewBeans.size() > 1 && guijiViewBeans != null) {
                mBaiduMap.clear();
                List<GuijiViewBeanjizhan> resultBeans = null;
                try {
                    mBaiduMap.clear();
                    resultBeans = dbManagerJZ.guijiViewBeans();
                    Log.d(TAG, "查询到的resultBeansonCreate: " + resultBeans);
                    Log.d(TAG, "resultBeansonResponse1: " + resultBeans);
                    if (resultBeans.size() > 0 && resultBeans != null) {
                        mBaiduMap.clear();
                        DataAll(resultBeans);
                    } else {
                        Log.d(TAG, "initdatas: aa" + "1111");

//                        MyToast.showToast("请先添加基站");


                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(new LatLng(guijiViewBeans.get(0).getLat(), guijiViewBeans.get(0).getLon()));
                builder.zoom(19.0f);
//                mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));//跳转到当前
                // 添加多纹理分段的折线绘制

                List<LatLng> points11 = new ArrayList<>();
                for (int i = 0; i < guijiViewBeans.size(); i++) {
                    points11.add(new LatLng(guijiViewBeans.get(i).getLat(), guijiViewBeans.get(i).getLon()));
                }
                List<BitmapDescriptor> textureList = new ArrayList<>();
//                            textureList.add(mRedTexture);
                textureList.add(mBlueTexture);
//                            textureList.add(mGreenTexture);
                List<Integer> textureIndexs = new ArrayList<>();
                textureIndexs.add(0);
//                            textureIndexs.add(1);
//                            textureIndexs.add(2);
                //画线
                OverlayOptions ooPolyline11 = new PolylineOptions()
                        .width(10)
                        .points(points11)
                        .dottedLine(true)
                        .customTextureList(textureList)
                        .textureIndex(textureIndexs);
                Polyline mTexturePolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline11);

                guijiFlag = true;
                Log.d(TAG, "guijiFlagonClick: " + "开启了");
                iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_0));
                guijiViewBeans.clear();
            } else {
                guijiFlag = false;
                if (timer != null) {
                    timer.cancel();
                }
//                Toast.makeText(MainActivity.this, "无轨迹数据", Toast.LENGTH_LONG).show();
                MyToast.showToast("无轨迹数据");
                iv_viewstart.setBackground(getResources().getDrawable(R.mipmap.kaishi_1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Log.d(TAG, "Viewgjs: erro" + e.getMessage());
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double x = sensorEvent.values[SensorManager.DATA_X];
        if (Math.abs(x - lastX) > 1.0) {
            mCurrentDirection = (int) x;
            locData = new MyLocationData.Builder()
                    .accuracy(mCurrentAccracy)
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(mCurrentLat)
                    .longitude(mCurrentLon).build();
            mBaiduMap.setMyLocationData(locData);
        }
        lastX = x;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setjingbao() {
        //判断测量是否启动
        if (juliFlage == false) {
            Log.d(TAG, "setjingbao: " + "false");
            if (guijiFlag == true) {
                MyToast.showToast("请先关闭显示");
                return;
            }
            if (jingbaoflag == false) {
                if (markerMy == null) {
//                    Toast.makeText(MainActivity.this, "警报启动失败,无查询到的基站位置", Toast.LENGTH_LONG).show();
//                    aaaaaa
                    List<GuijiViewBeanjizhan> guijiViewBeanjizhans = null;
                    try {
                        guijiViewBeanjizhans = dbManagerJZ.queryType();
                        if (guijiViewBeanjizhans.size() == 0) {
//                            Toast.makeText(MainActivity.this, , Toast.LENGTH_LONG).show();
                            MyToast.showToast("请先设置报警基站");
                            return;
                        }
                        Log.d(TAG, "JingBaos:Type " + guijiViewBeanjizhans);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    startJb();
                }

            } else if (jingbaoflag == true) {
                jingbaoflag = false; //关闭
                Log.d(TAG, "setjingbao: " + jingbaoflag);
                bt_jingbao.setTextColor(Color.BLACK);
                ib_baojing.setBackground(getResources().getDrawable(R.mipmap.baojing_up));
                timer2.cancel();
            }
        } else {

            Log.d(TAG, "setjingbao: " + "true");
//            Toast.makeText(MainActivity.this, "请先关闭距离测量", Toast.LENGTH_LONG).show();
            MyToast.showToast("请先关闭距离测量");

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void startJb() {
        jingbaoflag = true;//启动
        bt_jingbao.setTextColor(Color.RED);
        Log.d(TAG, "setjingbao: " + jingbaoflag);
        ib_baojing.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
//
        int times = 4000;
        if (!TextUtils.isEmpty(ACacheUtil.getbaojingtime())) {
            String getguitime = ACacheUtil.getbaojingtime();
            int i = Integer.parseInt(getguitime + "");
            times = 1000 * i;
        } else {
            times = 4000;
        }
        timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer is running");
                Message message = new Message();
                handler.sendMessage(message);
                message.what = 2;
                Log.d(TAG, "handlerrun: " + 1);
            }
        }, 0, times);
//        aaaa
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setmapJuli() {
        if (jingbaoflag == false) {
            if (guijiFlag == true) {
                MyToast.showToast("请先关闭显示");
                return;
            }
            if (juliFlage == false) {
                //开启距离
                Log.d(TAG, "setmapJuli:开启距离");
                juliFlage = true;
                bt_juli.setTextColor(Color.RED);
                bt_uiceliang.setBackground(getResources().getDrawable(R.mipmap.celiang_1));
            } else if (juliFlage == true) {
                //关闭距离
                Log.d(TAG, "setmapJuli:关闭距离");
                juliFlage = false;
                bt_juli.setTextColor(Color.BLACK);
                bt_uiceliang.setBackground(getResources().getDrawable(R.mipmap.celiang));
            }
        } else {
            MyToast.showToast("请先关闭报警");

        }


    }

    private void saveIsShow() {
        ACacheUtil.putTl(et_taclac.getText().toString());
        ACacheUtil.putEci(et_eci.getText().toString());
        ACacheUtil.putSID(et_sid.getText().toString() + "");
//        ACacheUtil.putTa(et_ta.getText().toString());
//        ACacheUtil.putTa(MyUtils.listToString(list));
        ACacheUtil.putjzType(jizhanFlag + "");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    private void setmapHot() {
        if (hottype == 2) {
            //开启热力图
            bt_hot.setTextColor(Color.BLACK);
            mBaiduMap.setBaiduHeatMapEnabled(false);
            hottype = 1;
            ib_rl.setBackground(getResources().getDrawable(R.mipmap.reli_up));

        } else if (hottype == 1) {
            //开启热力图

            bt_hot.setTextColor(Color.RED);
            mBaiduMap.setBaiduHeatMapEnabled(true);
            hottype = 2;

            ib_rl.setBackground(getResources().getDrawable(R.mipmap.reli_down));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressLint("ResourceAsColor")
    private void setmapJT() {
        if (hjttype == 2) {
            //关闭
            mBaiduMap.setTrafficEnabled(false);
            hjttype = 1;
            bt_jt.setTextColor(Color.BLACK);

            ib_jt.setBackground(getResources().getDrawable(R.mipmap.jiaotong_up));
        } else if (hjttype == 1) {
            mBaiduMap.setTrafficEnabled(true);
            hjttype = 2;
            bt_jt.setTextColor(Color.RED);


            ib_jt.setBackground(getResources().getDrawable(R.mipmap.jiaotong_down));
        }
    }

    private void qiehuan() {
        int mapType = mBaiduMap.getMapType();
        if (mapType == 1) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        } else if (mapType == 2) {
            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        }
        Log.d(TAG, "qiehuan: " + mapType);
        Log.d(TAG, "qiehuan: " + mapType);
    }

    //发送查询基站请求
    private void sendPost() {
//        if(DATATYPE==6){
//            //阿里云数据查询
////            sendMax();
//            String key = "APPCODE eb358bdc0f28475e95ccbe24eb2e5ee7";
//            Log.d(TAG, "sendPostjizhanFlag:" + jizhanFlag);
//            int Flag;
//            if (jizhanFlag == 4) {
//                Flag = Integer.parseInt(et_sid.getText().toString());
//            } else {
//                Flag = jizhanFlag;
//            }
//            MyUtils.getNumber(ACacheUtil.getID());//次数更新
//            dialog.dismiss();
//
//
//            if(jizhanFlag==4||jizhanFlag==11){//判断当前是电信4G或是2G基站
//                Call<DataAliBean> call = RetrofitFactory.getInstence().API().GETBaseStationAliCdm(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()), key, "application/json; charset=UTF-8");
//                call.enqueue(new Callback<DataAliBean>() {
//                    @Override
//                    public void onResponse(Call<DataAliBean> call, Response<DataAliBean> response) {
//                        dataAliBean = response.body();
//                        Log.d("杨路通", "nzqonResponse: " + response.toString());
//                        if (dataAliBean!=null){
//                            if (dataAliBean.getMsg().equals("ok") && dataAliBean.getStatus() == 0) {
//                                try {
//                                    String latresult = dataAliBean.getResult().getLat();
//                                    String lonresult = dataAliBean.getResult().getLng();
//                                    LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                                    Log.d("杨路通", "dataBeanisShowjizhan: " + dataAliBean.getResult());
//                                    CoordinateConverter converter = new CoordinateConverter()
//                                            .from(CoordinateConverter.CoordType.GPS)
//                                            .coord(latLngresult);
//
//                                    //转换坐标
//                                    LatLng desLatLngBaidu = converter.convert();
//                                    GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//
//                                    if(jizhanFlag==4){
//                                        d.setSid(et_sid.getText().toString());
//                                    }else{
//                                        d.setSid("");
//                                    }
//                                    d.setTypes("");
//                                    d.setBand("");
//                                    d.setPci("");
//                                    d.setDown("");
//                                    d.setId(1);
//                                    d.setAddress(dataAliBean.getResult().getAddr());
//                                    d.setCi(et_eci.getText().toString());
//                                    d.setLac(et_taclac.getText().toString());
//                                    d.setMcc("460");
//                                    if(jizhanFlag==4){
//                                        d.setMnc(et_sid.getText().toString());
//                                    }else{
//                                        d.setMnc("11");
//                                    }
//                                    d.setRadius("0");//阿里的数据源半徑為0
//                                    d.setTa(MyUtils.listToString(list));
//                                    d.setType(0);
//                                    d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                                    d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                                    d.setResources("公开数据");
//                                    int i = dbManagerJZ.insertStudent(d);
//                                    MapStatus.Builder builder = new MapStatus.Builder();
//                                    builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                    initdatas();
//                                    list.clear();
//                                    dialog.dismiss();
//                                    isShowAliJz(true);//展示阿里基站
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                    Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                                }
//
//                            }else if(dataAliBean.getMsg().equals("没有信息")){
////                                MyToast.showToast("查询不到该基站信息使用聚合查询");
//                                //如果阿里的移动联通获取不到数据用聚合
//                                getJuHeData();
////                                 dialog.dismiss();
//                            }
//                        }else {
////                            MyToast.showToast("请求阿里接口失败使用聚合查询");
//                            getJuHeData();//接口请求失败用聚合
////                                dialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<DataAliBean> call, Throwable t) {
//
//                    }
//                });
//            }else{//移动联通基站查询
//                try {
//                    RetrofitFactory.getInstence().API().GETBaseStationAli(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()),key, "application/json; charset=UTF-8").enqueue(new Callback<DataAliBean>() {
//                        @Override
//                        public void onResponse(Call<DataAliBean> call, Response<DataAliBean> response) {
//                            dataAliBean = response.body();
//                            Log.d("杨路通", "nzqonResponse: " + response.toString());
//                            if (dataAliBean!=null){
//                                if (dataAliBean.getMsg().equals("ok") && dataAliBean.getStatus() == 0) {
//                                    try {
//                                        String latresult = dataAliBean.getResult().getLat();
//                                        String lonresult = dataAliBean.getResult().getLng();
//                                        LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                                        Log.d("杨路通", "dataBeanisShowjizhan: " + dataAliBean.getResult());
//                                        CoordinateConverter converter = new CoordinateConverter()
//                                                .from(CoordinateConverter.CoordType.GPS)
//                                                .coord(latLngresult);
//                                        //转换坐标
//                                        LatLng desLatLngBaidu = converter.convert();
//                                        GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                                        if (jizhanFlag == 0||jizhanFlag==1) {
//                                            d.setSid("");
//                                        }
//                                        d.setTypes("");
//                                        d.setBand("");
//                                        d.setPci("");
//                                        d.setDown("");
//                                        d.setId(1);
//                                        d.setAddress(dataAliBean.getResult().getAddr());
//                                        d.setCi(et_eci.getText().toString());
//                                        d.setLac(et_taclac.getText().toString());
//                                        if(Flag==0){//查询移动
//                                            d.setMcc("460");
//                                            d.setMnc("0");
//                                        }
//                                        if(Flag==1){//查询联通基站
//                                            d.setMcc("460");
//                                            d.setMnc("1");
//                                        }
//                                        d.setRadius("0");//阿里的数据源半徑為0
//                                        Log.d("杨路通", "onResponse:list" + list);
//                                        d.setTa(MyUtils.listToString(list));
//                                        d.setType(0);
//                                        d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                                        d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                                        d.setResources("公开数据");
//                                        int i = dbManagerJZ.insertStudent(d);
//                                        MapStatus.Builder builder = new MapStatus.Builder();
//                                        builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                        initdatas();
//                                        list.clear();
//                                        dialog.dismiss();
//                                        isShowAliJz(true);//展示阿里基站
//                                    } catch (SQLException e) {
//                                        e.printStackTrace();
//                                        Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                                    }
//                                }else if(dataAliBean.getMsg().equals("没有信息")){
////                                    MyToast.showToast("查询不到该基站信息使用聚合查询");
//                                    //如果阿里的移动联通获取不到数据用聚合
//                                    getJuHeData();
////                                    dialog.dismiss();
//                                }
//
//                            }else {
////                                MyToast.showToast("请求阿里接口失败使用聚合查询");
//                                getJuHeData();//接口请求失败用聚合
////                                dialog.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DataAliBean> call, Throwable t) {
//                            Log.d("杨路通", "nzqonResponse: " + t.getMessage());
//                        }
//                    });
//                } catch (Exception e) {
//                    MyToast.showToast("输入异常请检查输入信息是否有误");
//                }
//            }
////            Toast.makeText(MainActivity.this, "阿里云数据", Toast.LENGTH_SHORT).show();
//        }
        if(DATATYPE==1){
            //cellMap
//            sendMax();
            int Flag;
            if (jizhanFlag == 4) {
                Log.e(TAG, "sendPost: "+et_sid.getText().toString() );
                Flag = Integer.parseInt(et_sid.getText().toString());
            } else {
                Flag = jizhanFlag;
            }
            MyUtils.getNumber(ACacheUtil.getID());//次数更新
            dialog.dismiss();

            ArrayList<String> list = new ArrayList<>();
            if(jizhanFlag==4){//电信CDMA基站
                list.add(et_sid.getText().toString());
                list.add(et_taclac.getText().toString());
                list.add(et_eci.getText().toString());
                list.add(appKey);
                getRetrofit3(list);
            }else{
                list.add(jizhanFlag+"");
                list.add(et_taclac.getText().toString());
                list.add(et_eci.getText().toString());
                list.add(appKey);
                getRetrofit(list);
            }
//            if(jizhanFlag==4||jizhanFlag==11){//判断当前是电信4G或是2G基站
//                Call<DataAliBean> call = RetrofitFactory.getInstence().API().GETBaseStationAliCdm(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()), key, "application/json; charset=UTF-8");
//                call.enqueue(new Callback<DataAliBean>() {
//                    @Override
//                    public void onResponse(Call<DataAliBean> call, Response<DataAliBean> response) {
//                        dataAliBean = response.body();
//                        Log.d("杨路通", "nzqonResponse: " + response.toString());
//                        if (dataAliBean!=null){
//                            if (dataAliBean.getMsg().equals("ok") && dataAliBean.getStatus() == 0) {
//                                try {
//                                    String latresult = dataAliBean.getResult().getLat();
//                                    String lonresult = dataAliBean.getResult().getLng();
//                                    LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                                    Log.d("杨路通", "dataBeanisShowjizhan: " + dataAliBean.getResult());
//                                    CoordinateConverter converter = new CoordinateConverter()
//                                            .from(CoordinateConverter.CoordType.GPS)
//                                            .coord(latLngresult);
//
//                                    //转换坐标
//                                    LatLng desLatLngBaidu = converter.convert();
//                                    GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//
//                                    if(jizhanFlag==4){
//                                        d.setSid(et_sid.getText().toString());
//                                    }else{
//                                        d.setSid("");
//                                    }
//                                    d.setTypes("");
//                                    d.setBand("");
//                                    d.setPci("");
//                                    d.setDown("");
//                                    d.setId(1);
//                                    d.setAddress(dataAliBean.getResult().getAddr());
//                                    d.setCi(et_eci.getText().toString());
//                                    d.setLac(et_taclac.getText().toString());
//                                    d.setMcc("460");
//                                    if(jizhanFlag==4){
//                                        d.setMnc(et_sid.getText().toString());
//                                    }else{
//                                        d.setMnc("11");
//                                    }
//                                    d.setRadius("0");//阿里的数据源半徑為0
//                                    d.setTa(MyUtils.listToString(list));
//                                    d.setType(0);
//                                    d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                                    d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                                    d.setResources("公开数据");
//                                    int i = dbManagerJZ.insertStudent(d);
//                                    MapStatus.Builder builder = new MapStatus.Builder();
//                                    builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                    initdatas();
//                                    list.clear();
//                                    dialog.dismiss();
//                                    isShowAliJz(true);//展示阿里基站
//                                } catch (SQLException e) {
//                                    e.printStackTrace();
//                                    Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                                }
//
//                            }else if(dataAliBean.getMsg().equals("没有信息")){
////                                MyToast.showToast("查询不到该基站信息使用聚合查询");
//                                //如果阿里的移动联通获取不到数据用聚合
//                                getJuHeData();
////                                 dialog.dismiss();
//                            }
//                        }else {
////                            MyToast.showToast("请求阿里接口失败使用聚合查询");
//                            getJuHeData();//接口请求失败用聚合
////                                dialog.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<DataAliBean> call, Throwable t) {
//
//                    }
//                });
//            }else{//移动联通基站查询
//                try {
//                    RetrofitFactory.getInstence().API().GETBaseStationAli(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()),key, "application/json; charset=UTF-8").enqueue(new Callback<DataAliBean>() {
//                        @Override
//                        public void onResponse(Call<DataAliBean> call, Response<DataAliBean> response) {
//                            dataAliBean = response.body();
//                            Log.d("杨路通", "nzqonResponse: " + response.toString());
//                            if (dataAliBean!=null){
//                                if (dataAliBean.getMsg().equals("ok") && dataAliBean.getStatus() == 0) {
//                                    try {
//                                        String latresult = dataAliBean.getResult().getLat();
//                                        String lonresult = dataAliBean.getResult().getLng();
//                                        LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                                        Log.d("杨路通", "dataBeanisShowjizhan: " + dataAliBean.getResult());
//                                        CoordinateConverter converter = new CoordinateConverter()
//                                                .from(CoordinateConverter.CoordType.GPS)
//                                                .coord(latLngresult);
//                                        //转换坐标
//                                        LatLng desLatLngBaidu = converter.convert();
//                                        GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                                        if (jizhanFlag == 0||jizhanFlag==1) {
//                                            d.setSid("");
//                                        }
//                                        d.setTypes("");
//                                        d.setBand("");
//                                        d.setPci("");
//                                        d.setDown("");
//                                        d.setId(1);
//                                        d.setAddress(dataAliBean.getResult().getAddr());
//                                        d.setCi(et_eci.getText().toString());
//                                        d.setLac(et_taclac.getText().toString());
//                                        if(Flag==0){//查询移动
//                                            d.setMcc("460");
//                                            d.setMnc("0");
//                                        }
//                                        if(Flag==1){//查询联通基站
//                                            d.setMcc("460");
//                                            d.setMnc("1");
//                                        }
//                                        d.setRadius("0");//阿里的数据源半徑為0
//                                        Log.d("杨路通", "onResponse:list" + list);
//                                        d.setTa(MyUtils.listToString(list));
//                                        d.setType(0);
//                                        d.setLat(String.valueOf(desLatLngBaidu.latitude));
//                                        d.setLon(String.valueOf(desLatLngBaidu.longitude));
//                                        d.setResources("公开数据");
//                                        int i = dbManagerJZ.insertStudent(d);
//                                        MapStatus.Builder builder = new MapStatus.Builder();
//                                        builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
//                                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//                                        initdatas();
//                                        list.clear();
//                                        dialog.dismiss();
//                                        isShowAliJz(true);//展示阿里基站
//                                    } catch (SQLException e) {
//                                        e.printStackTrace();
//                                        Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
//                                    }
//                                }else if(dataAliBean.getMsg().equals("没有信息")){
////                                    MyToast.showToast("查询不到该基站信息使用聚合查询");
//                                    //如果阿里的移动联通获取不到数据用聚合
//                                    getJuHeData();
////                                    dialog.dismiss();
//                                }
//                            }else {
////                                MyToast.showToast("请求阿里接口失败使用聚合查询");
//                                getJuHeData();//接口请求失败用聚合
////                                dialog.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DataAliBean> call, Throwable t) {
//                            Log.d("杨路通", "nzqonResponse: " + t.getMessage());
//                        }
//                    });
//                } catch (Exception e) {
//                    MyToast.showToast("输入异常请检查输入信息是否有误");
//                }
//            }
//            Toast.makeText(MainActivity.this, "阿里云数据", Toast.LENGTH_SHORT).show();
        }
        if (DATATYPE == 2) {//聚合
            if (jizhanFlag == 11) {
//                Toast.makeText(MainActivity.this, "查询不到基站信息", Toast.LENGTH_LONG).show();
                MyToast.showToast("查询不到基站信息");
                dialog.dismiss();
                return;
            }
            if (jizhanFlag == 4) {
                MyToast.showToast("查询不到基站信息");
                dialog.dismiss();
                return;
            }
            RetrofitFactory.getInstence().API().QuerySm(String.valueOf(jizhanFlag), Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString())).enqueue(new Callback<JzDataQury>() {
                @Override
                public void onResponse(Call<JzDataQury> call, Response<JzDataQury> response) {
                    Log.d(TAG, "DATATYPEonResponse: " + "" + response.body().toString());
                    JzDataQury jzGetData = response.body();
                    if (jzGetData.getCode() == 1 && jzGetData.getData() != null) {
                        Log.d(TAG, "onResponse:jzgetData" + "有数据啦" + jzGetData.getData());
                        try {
                            String latresult = String.valueOf(jzGetData.getData().getLatitude());
                            String lonresult = String.valueOf(jzGetData.getData().getLongitude());
                            LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
//                            Log.d(TAG, "dataBeanisShowjizhan: " + dataBean.getResult());
//            LatLng latLngresult = new LatLng(Double.parseDouble("38.031242"), Double.parseDouble("114.450186"));

                            CoordinateConverter converter = new CoordinateConverter()
                                    .from(CoordinateConverter.CoordType.GPS)
                                    .coord(latLngresult);
                            //转换坐标
                            LatLng desLatLngBaidu = converter.convert();
                            GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
                            if (jizhanFlag == 4) {
                                d.setSid(et_sid.getText().toString());
                            } else {
                                d.setSid("");
                            }
                            d.setTypes("" + jzGetData.getData().getType());
                            d.setBand("" + jzGetData.getData().getBand());
                            d.setPci("" + jzGetData.getData().getPci());
                            d.setDown("" + jzGetData.getData().getDownFrequencyPoint());
                            d.setId(1);
                            d.setId(1);
                            d.setAddress(jzGetData.getData().getAreaName() + "");
                            d.setCi(et_eci.getText().toString() + "");
                            d.setLac(et_taclac.getText().toString() + "");
                            d.setMcc("");
                            d.setMnc(jzGetData.getData().getMnc() + "");
                            d.setRadius("0");
//                        d.setTa(et_ta.getText().toString());
                            d.setTa(MyUtils.listToString(list));
                            d.setType(0);
                            d.setResources("内部数据");
                            d.setLat(String.valueOf(desLatLngBaidu.latitude));
                            d.setLon(String.valueOf(desLatLngBaidu.longitude));
                            dbManagerJZ.insertStudent(d);
                            initdatas();
                            //视角跳转
                            MapStatus.Builder builder = new MapStatus.Builder();
                            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                            list.clear();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
                        }
                    }
                    dialog.dismiss();
                    if (jzGetData.getCode() == 0 && jzGetData.getData() == null) {
                        MyToast.showToast("查询不到基站信息");
                        dialog.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<JzDataQury> call, Throwable t) {

                }
            });
        }
        if (DATATYPE == 3) {//手动输入
//            LatLng latLng = new LatLng(Double.parseDouble("40.050716"), Double.parseDouble("116.33068"));
            if (TextUtils.isEmpty(ets_lon.getText().toString())) {
                ToastUtils.showShort("经度不能为空");
                return;
            }
            if (TextUtils.isEmpty(ets_lat.getText().toString())) {
                ToastUtils.showShort("纬度不能为空");
                return;
            }
            LatLng latLng = new LatLng(Double.parseDouble(ets_lat.getText().toString()), Double.parseDouble(ets_lon.getText().toString()));
            SdmSearch.reverseGeoCode(new ReverseGeoCodeOption().location(latLng).pageNum(0).pageSize(100));

            try {
                dbManagerJZ = new DBManagerJZ(MainActivity.this);
            } catch (SQLException e) {
                e.printStackTrace();
            }
//            LatLng latLng=new LatLng(Double.parseDouble(ets_lon.getText().toString()),Double.parseDouble(ets_lat.getText().toString()));
//                LatLng latLng = new LatLng(Double.parseDouble("40.050716"), Double.parseDouble("116.33068"));
            CoordinateConverter converter = new CoordinateConverter()
                    .from(CoordinateConverter.CoordType.GPS)
                    .coord(latLng);
            //转换坐标
            LatLng desLatLngBaidu = converter.convert();
            GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
//                if (jizhanFlag == 4) {
//                    d.setSid(et_sid.getText().toString());
//                } else {
//                    d.setSid("");
//                }
            d.setTypes("");
            d.setBand("");
            d.setPci("");
            d.setDown("");
            d.setId(1);
            d.setAddress("");
            d.setCi(et_eci.getText().toString() + "");
            d.setLac(et_taclac.getText().toString() + "");
            d.setMcc("");
            d.setMnc("");
            d.setRadius("");
//                        d.setTa(et_ta.getText().toString());
            Log.d(TAG, "onResponse:aalist" + list);
            d.setTa(MyUtils.listToString(list));
            d.setType(0);
            d.setLat(String.valueOf(desLatLngBaidu.latitude));
            d.setLon(String.valueOf(desLatLngBaidu.longitude));
            d.setResources("手动输入");
            try {
                dbManagerJZ.insertStudent(d);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            initdatas();


//            Log.d("2ilssDetail1", "sendPost: ");
            dialog.dismiss();
        }

    }
    private String TAG ="ylt";
    private void getRetrofit(ArrayList<String> list) {
        String url;
//        http://www.cellmap.cn/api/cell2gps.aspx?mnc=0&lac=12795&cell=241734401&key=09f9433abbe7406aa5da1d3290d36c1d

        url = getBaseUrl+"?mnc="+ list.get(0)+"&lac="+ list.get(1)+"&cell="+ list.get(2)+"&key="+ list.get(3);

        Log.e(TAG, "getRetrofit: "+url );
        OkHttpClient client=new OkHttpClient();
        Request request=new Request
                .Builder()
                .url(url)//要访问的链接
                .get()
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("yltylt", "onResponse: "+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.showToast("查询失败");
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                String string = response.body().string();
                Log.e("yltylt", "onResponse: "+string);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(string.equals("null")){
                            MyToast.showToast("查询不到该基站");
                        }else{
                            String[] split = string.split(",");
                            Log.e("yltylt", "onResponse3: "+string);
                            try {
                                String latresult = split[0];
                                String lonresult = split[1];
                                LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
                                CoordinateConverter converter = new CoordinateConverter()
                                        .from(CoordinateConverter.CoordType.GPS)
                                        .coord(latLngresult);
                                //转换坐标
                                LatLng desLatLngBaidu = converter.convert();
                                GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
                                if (jizhanFlag == 4) {
                                    d.setSid(et_sid.getText().toString());
                                } else {
                                    d.setSid("");
                                }
//                        d.setTypes("" + jzGetData.getData().getType());
//                        d.setBand("" + jzGetData.getData().getBand());
//                        d.setPci("" + jzGetData.getData().getPci());
//                        d.setDown("" + jzGetData.getData().getDownFrequencyPoint());
                                d.setId(1);
                                d.setId(1);
                                d.setAddress(split[4]);
                                d.setCi(et_eci.getText().toString() + "");
                                d.setLac(et_taclac.getText().toString() + "");
                                d.setMcc("");
                                d.setMnc(jizhanFlag + "");
                                d.setRadius("0");
//                        d.setTa(et_ta.getText().toString());
                                d.setTa(MyUtils.listToString(MainActivity.this.list));
                                d.setType(0);
                                d.setResources("公开数据");
                                d.setLat(String.valueOf(desLatLngBaidu.latitude));
                                d.setLon(String.valueOf(desLatLngBaidu.longitude));
                                dbManagerJZ.insertStudent(d);
                                initdatas();
                                //视角跳转
                                MapStatus.Builder builder = new MapStatus.Builder();
                                builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
                                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                                list.clear();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
                            }
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }
    private void getRetrofit3(ArrayList<String> list) {
        String url;
//        http://www.cellmap.cn/api/cell2gps.aspx?mnc=0&lac=12795&cell=241734401&key=09f9433abbe7406aa5da1d3290d36c1d

        url = getBaseUrl3+"?sid="+ list.get(0)+"&nid="+ list.get(1)+"&bid="+ list.get(2)+"&key="+ list.get(3);

        Log.e(TAG, "getRetrofit3: "+url );
        OkHttpClient client=new OkHttpClient();
        Request request=new Request
                .Builder()
                .url(url)//要访问的链接
                .get()
                .build();
        okhttp3.Call call = client.newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.e("yltylt", "onResponse3: "+e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.showToast("查询失败");
                    }
                });
            }
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
               String string = response.body().string();
                runOnUiThread(new Runnable() {
                    public void run() {
                        if(string.equals("null")){
                            MyToast.showToast("查询不到该基站");
                        }else{
                            String[] split = string.split(",");
                            Log.e("yltylt", "onResponse3: "+string);
                            try {
                                String latresult = split[0];
                                String lonresult = split[1];
                                LatLng latLngresult = new LatLng(Double.parseDouble(latresult), Double.parseDouble(lonresult));
                                CoordinateConverter converter = new CoordinateConverter()
                                        .from(CoordinateConverter.CoordType.GPS)
                                        .coord(latLngresult);
                                //转换坐标
                                LatLng desLatLngBaidu = converter.convert();
                                GuijiViewBeanjizhan d = new GuijiViewBeanjizhan();
                                if (jizhanFlag == 4) {
                                    d.setSid(et_sid.getText().toString());
                                } else {
                                    d.setSid("");
                                }
//                        d.setTypes("" + jzGetData.getData().getType());
//                        d.setBand("" + jzGetData.getData().getBand());
//                        d.setPci("" + jzGetData.getData().getPci());
//                        d.setDown("" + jzGetData.getData().getDownFrequencyPoint());
                                d.setId(1);
                                d.setId(1);
                                d.setAddress(split[4]);
                                d.setCi(et_eci.getText().toString() + "");
                                d.setLac(et_taclac.getText().toString() + "");
                                d.setMcc("");
                                d.setMnc(jizhanFlag + "");
                                d.setRadius("0");
//                        d.setTa(et_ta.getText().toString());
                                d.setTa(MyUtils.listToString(MainActivity.this.list));
                                d.setType(0);
                                d.setResources("公开数据");
                                d.setLat(String.valueOf(desLatLngBaidu.latitude));
                                d.setLon(String.valueOf(desLatLngBaidu.longitude));
                                dbManagerJZ.insertStudent(d);
                                initdatas();
                                //视角跳转
                                MapStatus.Builder builder = new MapStatus.Builder();
                                builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
                                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                                list.clear();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
                            }
                            dialog.dismiss();
                        }
                    }
                });

            }
        });
    }

    private void getJuHeData() {
//        if (DATATYPE == 1) {//公开数据查询 聚合
//            sendMax();
        String key = "4283a37b42d1e381f4ffa6bf9e8ecc96";
        Log.d(TAG, "sendPostjizhanFlag:" + jizhanFlag);
        int Flag = 0;
        if (jizhanFlag == 4) {
            Flag = Integer.parseInt(et_sid.getText().toString());
        } else {
            Flag = jizhanFlag;
        }
        MyUtils.getNumber(ACacheUtil.getID());//次数更新
        dialog.dismiss();
        try {
            RetrofitFactory.getInstence().API().GETBaseStation(Flag, Integer.parseInt(et_taclac.getText().toString()), Integer.parseInt(et_eci.getText().toString()), key).enqueue(new Callback<DataBean>() {
                @Override
                public void onResponse(Call<DataBean> call, Response<DataBean> response) {
                    Log.d(TAG, "nzqonResponse: " + response.toString());

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
                            if (jizhanFlag == 4) {
                                d.setSid(et_sid.getText().toString());
                            } else {
                                d.setSid("");
                            }
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
                            Log.d(TAG, "onResponse:aalist" + list);
                            d.setTa(MyUtils.listToString(list));
                            d.setType(0);
                            d.setLat(String.valueOf(desLatLngBaidu.latitude));
                            d.setLon(String.valueOf(desLatLngBaidu.longitude));
                            d.setResources("公开数据");
                            int i = dbManagerJZ.insertStudent(d);
                            MapStatus.Builder builder = new MapStatus.Builder();
                            builder.target(new LatLng(desLatLngBaidu.latitude, desLatLngBaidu.longitude)).zoom(18.0f);
                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                            initdatas();
                            list.clear();
                        } catch (SQLException e) {
                            e.printStackTrace();
                            Log.d(TAG, "resultBeansonResponse1: " + e.getMessage());
                        }

                    }
                    dialog.dismiss();
                    isShowjizhan(true);//展示基站
                }

                @Override
                public void onFailure(Call<DataBean> call, Throwable t) {
                    Log.d(TAG, "nzqonResponse: " + t.getMessage());

                }
            });
        } catch (Exception e) {
            MyToast.showToast("输入异常请检查输入信息是否有误");
        }

//        }
    }

//    aaaaaaaaaaaaaaaaaa

    //展示阿里基站信息
    private void isShowAliJz(boolean b){
        if(dataAliBean.getStatus()==0&&dataAliBean.getMsg().equals("ok")){
            MyToast.showToast("增加成功");
//            MyToast.showToast(dataAliBean.getResult().toString());
        }
        if(dataAliBean.getStatus()==201){
            dialog.dismiss();
        }
        if(dataAliBean.getStatus()==202){
            dialog.dismiss();

        }
        if(dataAliBean.getStatus()==210){
            MyToast.showToast(dataAliBean.getMsg());
            dialog.dismiss();
        }
    }
    //展示基站
    private void isShowjizhan(boolean tag) {
        if (dataBean.getReason().equals("查询成功") && dataBean.getError_code() == 0) {

            MyToast.showToast("增加成功");
            Log.d(TAG, "onResponse: " + dataBean.getResult());
//

        }
        if (dataBean.getError_code() == 200801) {
            MyToast.showToast("错误的LAC或CELLID");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 200803) {
            MyToast.showToast("查询不到该基站信息");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10001) {
            MyToast.showToast("错误的请求KEY");
//            Toast.makeText(MainActivity.this, "错误的请求KEY", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10002) {
            MyToast.showToast("该KEY无请求权限");
//            Toast.makeText(MainActivity.this, "该KEY无请求权限", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10003) {
//            Toast.makeText(MainActivity.this, "KEY过期", Toast.LENGTH_LONG).show();
            MyToast.showToast("KEY过期");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10004) {
//            Toast.makeText(MainActivity.this, "错误的OPENID", Toast.LENGTH_LONG).show();
            MyToast.showToast("错误的OPENID");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10005) {
//            Toast.makeText(MainActivity.this, "应用未审核超时，请提交认证", Toast.LENGTH_LONG).show();
            MyToast.showToast("应用未审核超时，请提交认证");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10007) {
            MyToast.showToast("未知的请求源");
//            Toast.makeText(MainActivity.this, "未知的请求源", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10008) {
            MyToast.showToast("被禁止的IP");
//            Toast.makeText(MainActivity.this, "被禁止的IP", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10009) {
//            Toast.makeText(MainActivity.this, "被禁止的KEY", Toast.LENGTH_LONG).show();
            MyToast.showToast("被禁止的KEY");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10011) {
//            Toast.makeText(MainActivity.this, "当前IP请求超过限制", Toast.LENGTH_LONG).show();
            MyToast.showToast("当前IP请求超过限制");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10012) {
            MyToast.showToast("请求超过次数限制");
//            Toast.makeText(MainActivity.this, "请求超过次数限制", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10013) {
//            Toast.makeText(MainActivity.this, "测试KEY超过请求限制", Toast.LENGTH_LONG).show();
            MyToast.showToast("测试KEY超过请求限制");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10014) {
//            Toast.makeText(MainActivity.this, "系统内部异常", Toast.LENGTH_LONG).show();
            MyToast.showToast("系统内部异常");
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10020) {
            MyToast.showToast("接口维护");
//            Toast.makeText(MainActivity.this, "接口维护", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
        if (dataBean.getError_code() == 10022) {
//            Toast.makeText(MainActivity.this, "接口停用", Toast.LENGTH_LONG).show();
            MyToast.showToast("接口停用");
            dialog.dismiss();
        }

    }


    private void showDoliag() {//底部弹出窗 增加基站
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.item_dibushow, null);
        RadioGroup rgp_main = inflate.findViewById(R.id.rg_main);
        //聚合数据
        RadioButton rb_open1 = inflate.findViewById(R.id.rb_open_check1);
//        RadioButton rb_open2 = inflate.findViewById(R.id.rb_open_check2);
        RadioButton rb_oneself = inflate.findViewById(R.id.rb_oneself);
        RadioButton rb_Manuallyenter = inflate.findViewById(R.id.rb_Manuallyenter);
        //初始化控件
        ll_sid = inflate.findViewById(R.id.ll_sid);
        iv_finish = inflate.findViewById(R.id.iv_finish);
        iv_finish.setOnClickListener(this);
        et_sid = inflate.findViewById(R.id.et_sid);
        et_taclac = inflate.findViewById(R.id.et_taclac);
        et_eci = inflate.findViewById(R.id.et_eci);
        et_ta = inflate.findViewById(R.id.et_ta);


        //手动输入的 view
        ll_location = inflate.findViewById(R.id.ll_location);//手动输入经纬度布局
        ets_lon = inflate.findViewById(R.id.ets_lon);//经度
        ets_lat = inflate.findViewById(R.id.ets_lat);//纬度
        final RecyclerView recyclerView = inflate.findViewById(R.id.recylerview);

        ll_location.setVisibility(View.GONE);

        if(DATATYPE==1){
            rb_open1.setChecked(true);
        }
       /* if (DATATYPE == 1) {
            rb_open2.setChecked(true);
        }*/
        if (DATATYPE == 2) {
            rb_oneself.setChecked(true);
        }
        rgp_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    //选中公开数据1为阿里云数据
                    case R.id.rb_open_check1:
//   Toast.makeText(MainActivity.this, "公开数据", Toast.LENGTH_LONG).show();
//                        MyToast.showToast("公开数据1--阿里");
                        Log.d(TAG, "qonCheckedChanged: " + "6");
                        DATATYPE = 1;
                        ll_location.setVisibility(View.GONE);
                        break;
                   /* case R.id.rb_open_check2:
//                        Toast.makeText(MainActivity.this, "公开数据", Toast.LENGTH_LONG).show();
//                        MyToast.showToast("公开数据2--聚合");
                        Log.d(TAG, "qonCheckedChanged: " + "1");
                        DATATYPE = 1;
                        ll_location.setVisibility(View.GONE);
                        break;*/
                    case R.id.rb_oneself:
//                        Toast.makeText(MainActivity.this, "内部数据", Toast.LENGTH_LONG).show();
//                        MyToast.showToast("内部数据");
                        Log.d(TAG, "qonCheckedChanged: " + "2");
                        DATATYPE = 2;
                        ll_location.setVisibility(View.GONE);
                        break;
                    case R.id.rb_Manuallyenter:
//                        Toast.makeText(MainActivity.this, "内部数据", Toast.LENGTH_LONG).show();
//                        MyToast.showToast("内部数据");
                        Log.d(TAG, "qonCheckedChanged: " + "2");
                        DATATYPE = 3;
                        ll_location.setVisibility(View.VISIBLE);
                        break;

                }
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        demoAdapter = new DemoAdapter(list, callBack);
        recyclerView.setAdapter(demoAdapter);
        Button btadd = inflate.findViewById(R.id.btadd);
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(et_ta.getText().toString())) {
//                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_LONG).show();
                    MyToast.showToast("输入不能为空");
                    return;
                }
//                if (et_ta.getText().length() > 3) {
//                    Toast.makeText(MainActivity.this, "输入格式不正确", Toast.LENGTH_LONG).show();
//                    return;
//                }


                if(list.size()>0){//TA值默认添加的一条清除
                    list.clear();
                }
                list.add(Double.parseDouble(et_ta.getText().toString()));
//                mAdapter.notifyDataSetChanged();
                et_ta.setText("");
                demoAdapter.notifyDataSetChanged();
//                aaa
            }
        });
        String sid = ACacheUtil.getSID();
        et_sid.setText(sid);
        String tl = ACacheUtil.getTl();
        et_taclac.setText(tl);
        String eci = ACacheUtil.getEci();
        et_eci.setText(eci);
        String ta = ACacheUtil.getTa();
//        et_ta.setText(ta);
        rb_yidong = inflate.findViewById(R.id.rb_yidong);
        rb_yidong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    rb_liantong.setChecked(false);
                    rb_ldainxin4.setChecked(false);
                    rb_cdma.setChecked(false);
                    rb_bdjz1.setChecked(false);
                    rb_bdjz2.setChecked(false);
                    jizhanFlag = 0;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                    ll_sid.setVisibility(View.GONE);
                } else {
                    jizhanFlag = 44;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                }

            }
        });

        rb_liantong = inflate.findViewById(R.id.rb_liantong);
        rb_liantong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    rb_yidong.setChecked(false);
                    rb_ldainxin4.setChecked(false);
                    rb_cdma.setChecked(false);
                    rb_bdjz1.setChecked(false);
                    rb_bdjz2.setChecked(false);
                    jizhanFlag = 1;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                    ll_sid.setVisibility(View.GONE);
                } else {
                    jizhanFlag = 44;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                }
//
            }
        });
        rb_ldainxin4 = inflate.findViewById(R.id.rb_ldainxin4);
        rb_ldainxin4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    rb_yidong.setChecked(false);
                    rb_liantong.setChecked(false);
                    rb_cdma.setChecked(false);
                    rb_bdjz1.setChecked(false);
                    rb_bdjz2.setChecked(false);
                    jizhanFlag = 11;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                    ll_sid.setVisibility(View.GONE);
                } else {
                    jizhanFlag = 44;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                }


            }
        });
        rb_cdma = inflate.findViewById(R.id.rb_cdma);

        rb_cdma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    rb_yidong.setChecked(false);
                    rb_liantong.setChecked(false);
                    rb_ldainxin4.setChecked(false);
                    rb_bdjz1.setChecked(false);
                    rb_bdjz2.setChecked(false);

                    jizhanFlag = 4;
                    ll_sid.setVisibility(View.VISIBLE);
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                } else {
                    jizhanFlag = 44;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                }

            }
        });
        rb_bdjz1 = inflate.findViewById(R.id.rb_bdjzl);
        rb_bdjz1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    rb_yidong.setChecked(false);
                    rb_liantong.setChecked(false);
                    rb_cdma.setChecked(false);
                    rb_ldainxin4.setChecked(false);
                    rb_bdjz2.setChecked(false);

//                    jizhanFlag = 4;
                    ll_sid.setVisibility(View.GONE);
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                    List<SpBean> gsmInfoList = DtUtils.getGsmInfoList(MainActivity.this);
                    List<SpBean> list11 = new ArrayList<>();
                    if (gsmInfoList.size() > 0 && gsmInfoList != null) {//有数据
                        if (gsmInfoList.size() == 1) {//只有一条
                            for (int i = 0; i < gsmInfoList.size(); i++) {

                                int cid = gsmInfoList.get(i).getCid();
                                int tac = gsmInfoList.get(i).getTac();
                                if (cid != 2147483647 && tac != 2147483647) {
//                                gsmInfoList.remove(i);
                                    list11.add(gsmInfoList.get(i));
                                }
                            }

                            et_taclac.setText(gsmInfoList.get(0).getTac() + "");
                            et_eci.setText(gsmInfoList.get(0).getCid() + "");
                            jizhanFlag = DtUtils.YY2(gsmInfoList.get(0).getPlmn());
                            Log.d("gsmInfoList", "onCheckedChanged:一条 ");
                        } else {//多条
                            for (int i = 0; i < gsmInfoList.size(); i++) {
                                int cid = gsmInfoList.get(i).getCid();
                                int tac = gsmInfoList.get(i).getTac();
                                if (cid != 2147483647 && tac != 2147483647) {
//                                gsmInfoList.remove(i);
                                    list11.add(gsmInfoList.get(i));
                                }
                            }
                            if (list11.size() > 0) {
                                et_taclac.setText(list11.get(0).getTac() + "");
                                et_eci.setText(list11.get(0).getCid() + "");
                                jizhanFlag = DtUtils.YY2(list11.get(0).getPlmn());
                                Log.d("gsmInfoList", "onCheckedChanged:多条 ");
                            } else {
                                et_taclac.setText("");
                                et_eci.setText("");
                                jizhanFlag = 44;
                            }

                        }

                    } else {
                        et_taclac.setText("");
                        et_eci.setText("");
                        jizhanFlag = 44;
                    }

                } else {
                    et_taclac.setText("");
                    et_eci.setText("");
                    jizhanFlag = 44;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                }

            }
        });
        rb_bdjz2 = inflate.findViewById(R.id.rb_bdjz2);
        rb_bdjz2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b == true) {
                    rb_yidong.setChecked(false);
                    rb_liantong.setChecked(false);
                    rb_ldainxin4.setChecked(false);
                    rb_bdjz1.setChecked(false);
                    rb_cdma.setChecked(false);
//                    jizhanFlag = 4;
                    ll_sid.setVisibility(View.GONE);
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);


                    List<SpBean> gsmInfoList = DtUtils.getGsmInfoList(MainActivity.this);
                    List<SpBean> gsmInfoListremove = new ArrayList<>();
                    List<SpBean> list11 = new ArrayList<>();
                    List<SpBean> list22 = new ArrayList<>();
                    if (gsmInfoList.size() > 0 && gsmInfoList != null) {//有数据
                        if (gsmInfoList.size() == 1) {//只有一条
                            for (int i = 0; i < gsmInfoList.size(); i++) {

                                int cid = gsmInfoList.get(i).getCid();
                                int tac = gsmInfoList.get(i).getTac();
                                if (cid != 2147483647 && tac != 2147483647) {
//                                gsmInfoList.remove(i);
                                    list11.add(gsmInfoList.get(i));
                                }
                            }

                            et_taclac.setText(gsmInfoList.get(0).getTac() + "");
                            et_eci.setText(gsmInfoList.get(0).getCid() + "");
                            jizhanFlag = DtUtils.YY2(gsmInfoList.get(0).getPlmn());
                            Log.d("gsmInfoList", "onCheckedChanged:一条 ");
                        } else {//多条
                            for (int i = 0; i < gsmInfoList.size(); i++) {
                                int cid = gsmInfoList.get(i).getCid();
                                int tac = gsmInfoList.get(i).getTac();

                                if (cid != 2147483647 && tac != 2147483647) {
//                                gsmInfoList.remove(i);
                                    gsmInfoListremove.add(gsmInfoList.get(i));
                                }

                            }
                            if (gsmInfoListremove.size() > 1) {
                                list11.add(gsmInfoListremove.get(0));
                                list22.add(gsmInfoListremove.get(1));
                            } else if (gsmInfoListremove.size() == 1) {
                                list11.add(gsmInfoListremove.get(0));
                            }
                            if (list22.size() > 0) {
                                et_taclac.setText(list22.get(0).getTac() + "");
                                et_eci.setText(list22.get(0).getCid() + "");
                                jizhanFlag = DtUtils.YY2(list22.get(0).getPlmn());
                                Log.d("gsmInfoList", "onCheckedChanged:多条 ");
                            } else {
                                et_taclac.setText("");
                                et_eci.setText("");
                                jizhanFlag = 44;
                            }

                        }

                    } else {
                        et_taclac.setText("");
                        et_eci.setText("");
                        jizhanFlag = 44;
                    }
                } else {
                    et_taclac.setText("");
                    et_eci.setText("");
                    jizhanFlag = 44;
                    Log.d(TAG, "onCheckedChanged: " + jizhanFlag);
                }

            }
        });
        bt_adddilao = inflate.findViewById(R.id.bt_adddilao);
        bt_adddilao.setOnClickListener(this);
        String s = ACacheUtil.getjzType();
        if (TextUtils.isEmpty(s)) {


        } else {
            jizhanFlag = Integer.parseInt(s + "");
        }

        if (jizhanFlag == 0) {//如果是0 ，是移动
            rb_yidong.setChecked(true);
            rb_liantong.setChecked(false);
            rb_cdma.setChecked(false);
            rb_ldainxin4.setChecked(false);
        }
        if (jizhanFlag == 1) {//如果是1 ，是联通
            rb_yidong.setChecked(false);
            rb_liantong.setChecked(true);
            rb_cdma.setChecked(false);
            rb_ldainxin4.setChecked(false);
        }
        if (jizhanFlag == 11) {//如果是11 ，是电信
            rb_yidong.setChecked(false);
            rb_liantong.setChecked(false);
            rb_cdma.setChecked(false);
            rb_ldainxin4.setChecked(true);
        }
        if (jizhanFlag == 4) {//如果是4 ，是cdma
            rb_yidong.setChecked(false);
            rb_liantong.setChecked(false);
            rb_cdma.setChecked(true);
            rb_ldainxin4.setChecked(false);
        }

        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.ta_correction:
                Intent intents = new Intent(MainActivity.this, CorrectionActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("lat", mylag.latitude + "");
                bundle.putString("lon", mylag.longitude + "");
                intents.putExtras(bundle);
                startActivity(intents);
                break;
            case R.id.config: //配置管理弹出窗
                //底部弹出窗
                dialogmenu = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                //填充对话框的布局
                inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dibushow_config, null);
                et_guijitime = inflate.findViewById(R.id.et_guijitime);//轨迹间隔
                et_baojingtime = inflate.findViewById(R.id.et_baojingtime);//报警间隔
                et_queryMax = inflate.findViewById(R.id.et_queryMax);//允许查询次数
                et_querynum = inflate.findViewById(R.id.et_querynum);//已使用查询次数
                et_data = inflate.findViewById(R.id.et_data);//剩余使用时长
                if(LoginActivity.dataBean.getData()!=null){
                    et_data.setText(LoginActivity.dataBean.getData());
                }else{
                    et_data.setText("未知");
                }
                bt_adddilaomenu = inflate.findViewById(R.id.bt_adddilaomenu);//确认按钮
                bt_adddilaomenu.setOnClickListener(this);
                et_guijitime.setText(ACacheUtil.getguitime());//设置轨迹数据
                et_baojingtime.setText(ACacheUtil.getbaojingtime());//设置报警数据
                et_fw = inflate.findViewById(R.id.et_fw);//覆盖范围范围
                et_kg = inflate.findViewById(R.id.et_kg);//覆盖范围开关
                if (!TextUtils.isEmpty(ACacheUtil.getFugaiKG())) {
                    et_kg.setText(ACacheUtil.getFugaiKG() + "");
                }
                ImageView iv_finish = inflate.findViewById(R.id.iv_finish);
                iv_finish.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                Button bt_qx = inflate.findViewById(R.id.bt_qx);
                bt_qx.setOnClickListener(this);

                setquery();//允许查询次数 和已使用查询次数 设置
//                sendMax();

                //将布局设置给Dialog
                dialogmenu.setContentView(inflate);
                //获取当前Activity所在的窗体
                Window dialogWindow = dialogmenu.getWindow();
                //设置Dialog从窗体底部弹出
                dialogWindow.setGravity(Gravity.CENTER);
                //获得窗体的属性
                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                lp.y = 20;//设置Dialog距离底部的距离
//              将属性设置给窗体
                dialogWindow.setAttributes(lp);
                dialogmenu.show();//显示对话框
                break;
//            case R.id.reli:
//                setmapHot();
//                break;
//            case R.id.jt:
//                setmapJT();
//                break;

            case R.id.guanyu:
                startActivity(new Intent(MainActivity.this, introduceActivity.class));
                break;

            case R.id.config_clear:
                mBaiduMap.clear();
                break;
            case R.id.config_message:
//                //基站界面
//                Toast.makeText(this, "你点击了基站信息", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(this, BaseNrLteActivity.class);
                if(longitude!=null&&latitude!=null){
                    Log.i("杨路通", "Main给BaseMsg经度: "+longitude);
                    Log.i("杨路通", "Main给BaseMsg纬度: "+latitude);
                    intent1.putExtra("longitude", longitude+"");
                    intent1.putExtra("latitude", latitude+"");
                }
                startActivity(intent1);
                break;
//                case R.id.config_drive:
            //基站界面
//                Toast.makeText(this, "你点击了基站信息", Toast.LENGTH_SHORT).show();
               /* Intent intent2 = new Intent(this, DriveTestActivity.class);
                if(longitude!=null&&latitude!=null){
                    Log.i("杨路通", "Main给BaseMsg经度: "+longitude);
                    Log.i("杨路通", "Main给BaseMsg纬度: "+latitude);
                    intent2.putExtra("longitude", longitude+"");
                    intent2.putExtra("latitude", latitude+"");
                }
                startActivity(intent2);*/
//                break;
            case R.id.jzxs:
//                Toast.makeText(MainActivity.this, "你点击了基站巡视", Toast.LENGTH_LONG).show();
                Myshow.jizhanShow(MainActivity.this, mycallback, xunFlag, BUSINESS, ll_screen, mBaiduMap, uiSettings);

                Log.d(TAG, "onMenuItemClick:BUSINESS " + BUSINESS);
                xunFlag = Myshow.xunFlags;
                break;

            case R.id.ta_show:
//                Toast.makeText(MainActivity.this, "你点击了TA值圈", Toast.LENGTH_LONG).show();

                Myshow.ShowTa(MainActivity.this, MAXTA, MINTA, UNIFORMTA, callshow, fugaiFlag);
                break;
            case R.id.jzlist://基站列表
                Intent intent = new Intent(MainActivity.this, JzListActivity.class);
                startActivityForResult(intent, 1);
                break;

            case R.id.dataimport://数据导入

                Intent intent3 = new Intent(MainActivity.this, ExcelImportActivity.class);
                startActivity(intent3);
        }
        return false;
    }

    private void sendMax() {
        RetrofitFactory.getInstence().API().NUMBER(ACacheUtil.getID()).enqueue(new Callback<NumberBean>() {
            @Override
            public void onResponse(Call<NumberBean> call, Response<NumberBean> response) {
                NumberBean numberBean = response.body();
                et_queryMax.setText(numberBean.getData().getTotal() + "");
                ACacheUtil.putNumberMax(numberBean.getData().getTotal() + "");
                et_querynum.setText(numberBean.getData().getRemainder() + "");
                ACacheUtil.putNumberremainder(numberBean.getData().getTotal() + "");
            }

            @Override
            public void onFailure(Call<NumberBean> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        MenuItem item = menu.findItem(R.id.reli);
//        item.setTitle("111");
        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * 设置查询的数据 允许查询次数和已查询次数
     */
    private void setquery() {
        et_queryMax.setText(ACacheUtil.getNumberMax());
        et_querynum.setText(ACacheUtil.getNumberremainder() + "");
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner extends BDAbstractLocationListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "onReceiveLocation1: " + mylag);

            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }

//            Toast.makeText(MainActivity.this, ""+mylag, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onReceiveLocation2: " + mylag);
            mCurrentLat = location.getLatitude();
            mCurrentLon = location.getLongitude();
            mCurrentAccracy = location.getRadius();
            locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentDirection).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            mylag = new LatLng(location.getLatitude(), location.getLongitude());//当前的位置
            longitude=location.getLongitude()+"";
            latitude=location.getLatitude()+"";
            Log.i("杨路通", "onReceiveLocation: "+longitude);
            Log.i("杨路通", "onReceiveLocation: "+latitude);
            Log.d(TAG, "位置mylag" + mylag);

            if (guijistart == true) {//轨迹开始添加记录
                List<GuijiViewBean> guijiViewBeans = null;
                try {
                    guijiViewBeans = dbManagerGuijiView.guijiViewBeans();
                    Log.d(TAG, "guijiViewBeansonReceiveLocation: " + guijiViewBeans);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (guijiViewBeans.size() > 0 && guijiViewBeans != null) {
                    GuijiViewBean guijiViewBean = guijiViewBeans.get(guijiViewBeans.size() - 1);
                    double lat = guijiViewBean.getLat();
                    double lon = guijiViewBean.getLon();
                    LatLng latLngceliang = new LatLng(lat, lon);
                    double distance = DistanceUtil.getDistance(mylag, latLngceliang);//当前和上一个记录点的位置
                    Double getguis = 0.0;
                    if (!TextUtils.isEmpty(ACacheUtil.getguitime())) {
                        String getguitime = ACacheUtil.getguitime();
                        getguis = Double.parseDouble(getguitime);
                    } else {
                        getguis = 10.0;
                    }
                    if (distance > getguis) { //大于设置的 轨迹间隔 添加
                        Log.d(TAG, "ASonReceiveLocation:大于");
                        GuijiViewBean guijiViewBeanS = new GuijiViewBean();
                        guijiViewBeanS.setLat(location.getLatitude());
                        guijiViewBeanS.setLon(location.getLongitude());
                        try {
                            dbManagerGuijiView.insertStudent(guijiViewBeanS);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.d(TAG, "ASonReceiveLocation:小于");

                    }
                }
            }


//            if (1 == 1) {  //判断是否启动警报
            Log.d(TAG, "onReceiveLocation: " + "启动了警报");
            pointsJingbao = new ArrayList<LatLng>();//绘制线的list

            if (markerMy != null) {
                positionjingbaojizhan = markerMy.getPosition();//基站的位置


                Log.d(TAG, "onReceiveLocation: markerMy" + "有数据");


//                    //警报是否发开
//                    if (jingbaoflag == true) {
//                                //显示dialog
//                                JingBaos(pointsJingbao, positionjingbaojizhan,mylag,false);
//
//                  //5秒
//
//                    } else {
//                        Log.d(TAG, "onReceiveLocation: " + "没走" + jingbaoflag);
//                    }
            } else {
                Log.d(TAG, "onReceiveLocation: markerMy" + "没有数据");
            }
//            } else {
//                Log.d(TAG, "onReceiveLocation: " + "未启动警报");
//
//            }


        }

        public void onReceivePoi(BDLocation poiLocation) {

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    //线集合        基站点的位置                   自己当前的位置   是否开启警报
    private void JingBaos(List<LatLng> pointsJingbao, LatLng positionjingbaojizhan, LatLng mylag, boolean bb) {
        List<LatLng> pointsJingbaoLines = new ArrayList<>();

        if (bb == false) {

        } else {

//aaaa
            List<GuijiViewBeanjizhan> guijiViewBeanjizhans = null;
            try {
                guijiViewBeanjizhans = dbManagerJZ.queryType();

                Log.d(TAG, "JingBaos:Type " + guijiViewBeanjizhans);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (guijiViewBeanjizhans.size() == 0) {
                MyToast.showToast("请先设置报警基站");
//                Toast.makeText(MainActivity.this, "请先设置报警基站", Toast.LENGTH_LONG).show();
                Log.d(TAG, "1JingBaoguijiViewBeanjizhanss: " + guijiViewBeanjizhans + "''''" + guijiViewBeanjizhans.size());
                jingbaoflag = false; //关闭
                Log.d(TAG, "setjingbao: " + jingbaoflag);
                bt_jingbao.setTextColor(Color.BLACK);
                ib_baojing.setBackground(getResources().getDrawable(R.mipmap.baojing_up));
                timer2.cancel();
                return;
            } else {
                Log.d(TAG, "2JingBaoguijiViewBeanjizhanss: " + guijiViewBeanjizhans);
            }
            mBaiduMap.clear();
            //查询已存储的基站
            List<GuijiViewBeanjizhan> resultBeans = null;
            try {
                resultBeans = dbManagerJZ.guijiViewBeans();
                Log.d(TAG, "查询到的resultBeansonCreate: " + resultBeans);
                if (resultBeans.size() > 0 && resultBeans != null) {
                    DataAll(resultBeans);
                    for (int i = 0; i < resultBeans.size(); i++) {
                        pointsJingbaoLines.clear();
                        pointsJingbaoLines.add(mylag);
                        pointsJingbaoLines.add(new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon())));
                        OverlayOptions mOverlayOptions = new PolylineOptions()
                                .width(5)
                                .color(Color.rgb(65, 105, 225))
                                .points(pointsJingbaoLines);
                        //在地图上绘制折线
                        //mPloyline 折线对象
                        Overlay mPolylines = mBaiduMap.addOverlay(mOverlayOptions);
                        double distancejizhan = DistanceUtil.getDistance(mylag, new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon())));//当前距离基站的位置
                        Log.d(TAG, "distanceonMapClicks: " + "distancejizhan" + distancejizhan + "米");
                        //距离
                        final String tallk = Math.round(distancejizhan) + "米";
                        //构建TextOptions对象
                        OverlayOptions mTextOptions = new TextOptions()
                                .text("" + tallk) //文字内容
                                .bgColor(Color.rgb(224, 255, 255)) //背景色
                                .fontSize(26) //字号
                                .fontColor(Color.rgb(0, 0, 0)) //文字颜色
                                .rotate(0) //旋转角度
                                .position(new LatLng(Double.parseDouble(resultBeans.get(i).getLat()), Double.parseDouble(resultBeans.get(i).getLon())));
                        //在地图上显示文字覆盖物
                        Overlay mText = mBaiduMap.addOverlay(mTextOptions);
                        //添加文字 lac eci
//                        LatLng desLatLngBaiduD = new LatLng(Double.parseDouble(resultBeans.get(i).getLat())-0.00002, Double.parseDouble(resultBeans.get(i).getLon()));
//构建TextOptions对象
                        OverlayOptions mTextOptionss = new TextOptions()
                                .text(resultBeans.get(i).getLac() + "-" + resultBeans.get(i).getCi()) //文字内容
//                    .bgColor(getResources().getColor(R.color.color_f25057)) //背景色
                                .fontSize(32) //字号
                                .fontColor(getResources().getColor(R.color.color_f25057)) //文字颜色
                                .rotate(0) //旋转角度
                                .position(new LatLng(Double.parseDouble(resultBeans.get(i).getLat()) - 0.00002, Double.parseDouble(resultBeans.get(i).getLon())));

//在地图上显示文字覆盖物
                        mBaiduMap.addOverlay(mTextOptionss);
                    }


                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onReceiveLocation: " + "走了");
            DecimalFormat df = new DecimalFormat(".");
            //构建折线点坐标
            pointsJingbao.add(mylag);
            pointsJingbao.add(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())));

//            List<Double> lists = MyUtils.StringTolist(guijiViewBeanjizhans.get(0).getTa());
//            int sum = 0;
//            for (int j = 0; j < lists.size(); j++) {
//                sum += lists.get(j);
//            }
//
//
//            //平均值的圈
//            int size = lists.size();
//
//            double sum_db = sum;
//            double size_db = size;
//            double Myradius_db = sum_db / size_db * 78;
//            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
//            int Myradius = new Double(Myradius_db).intValue();
//            Log.d(TAG, "JingBaos:Myradius " + Myradius);
//            if (UNIFORMTA == true) {
//                OverlayOptions ooCircle = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(40, 255,
//                                0,
//                                0))
//                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
//                        .stroke(new Stroke(2, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius(Myradius);
//                LatLng center = ((CircleOptions) ooCircle).getCenter();
//                Log.d("nzq", "onCreate: " + center);
//                mBaiduMap.addOverlay(ooCircle);
//            }
//            //小圆
////                    LatLng llDot = new LatLng(39.90923, 116.447428);
//            OverlayOptions ooDot = new DotOptions().center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon()))).radius(6).color(0xFF0000FF);
//            mBaiduMap.addOverlay(ooDot);

            List<Double> lists = MyUtils.StringTolist(guijiViewBeanjizhans.get(0).getTa());
            Double sum = Double.valueOf(0);
            for (int j = 0; j < lists.size(); j++) {
                sum += lists.get(j);
            }
            //平均值的圈
            int size = lists.size();
            double sum_db = sum;
            double size_db = size;
            double Myradius_db = sum_db / size_db * 78;

            Log.d(TAG, "DataAlla: " + sum_db + "--" + size_db);
            Log.d(TAG, "DataAll: aaa0" + sum_db / size_db * 78);
            Log.d(TAG, "JingBaos: sum" + sum_db + "---Myradius_db" + Myradius_db + "size_db--" + size_db);
            int Myradius = new Double(Myradius_db).intValue();
            Log.d(TAG, "JingBaos:Myradius " + Myradius);
            if (UNIFORMTA == true) {
                OverlayOptions ooCirclepingjun = new CircleOptions()
//                            .fillColor(0x000000FF)
                        .fillColor(Color.argb(40, 255,
                                0,
                                0))
                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
                        .stroke(new Stroke(5, Color.rgb(255,
                                0,
                                0)))
                        .radius((int) Myradius_db);
                mBaiduMap.addOverlay(ooCirclepingjun);
                Log.e(TAG, "DataAll:最大 " + Collections.max(lists));
                Log.d(TAG, "DataAlla:pingjun" + (int) Myradius_db);
            }

//            //最大ta圈
//            if (MAXTA == true) {
//                OverlayOptions ooCircleaMAx = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(0, 255,
//                                0,
//                                0))
//                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
//                        .stroke(new Stroke(2, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius((int) (Collections.max(lists) * 78));
//                Log.d(TAG, "DataAlla:MAXTA" + (int) (Collections.max(lists) * 78));
//                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                mBaiduMap.addOverlay(ooCircleaMAx);
//            }
//            Log.d(TAG, "DataAllsum: " + sum);
//            //最小ta圈
//            if (MINTA == true) {
//                OverlayOptions ooCircleaMin = new CircleOptions()
////                            .fillColor(0x000000FF)
//                        .fillColor(Color.argb(0, 255,
//                                0,
//                                0))
//                        .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
//                        .stroke(new Stroke(2, Color.rgb(255,
//                                0,
//                                0)))
//                        .radius((int) (Collections.min(lists) * 78));
//                Log.d(TAG, "DataAlla:min" + (int) (Collections.min(lists) * 78));
//                Log.e(TAG, "DataAll:最大 " + Collections.min(lists));
////            Log.d("nzq", "onCreate: " + center);
//                mBaiduMap.addOverlay(ooCircleaMin);
//            }


            //构建Marker图标
//            jizhan1
            BitmapDescriptor bitmap = BitmapDescriptorFactory
                    .fromResource(R.drawable.arrow);
            //构建MarkerOption，用于在地图上添加Marker
            Bundle bundles = new Bundle();
            bundles.putString("id", String.valueOf(guijiViewBeanjizhans.get(0).getId()));
            bundles.putString("type", String.valueOf(guijiViewBeanjizhans.get(0).getType()));
            bundles.putString("mcc", guijiViewBeanjizhans.get(0).getMcc());
            bundles.putString("mnc", guijiViewBeanjizhans.get(0).getMnc());
            bundles.putString("lac", guijiViewBeanjizhans.get(0).getLac());
            bundles.putString("ci", guijiViewBeanjizhans.get(0).getCi());
            bundles.putString("lat", String.valueOf(guijiViewBeanjizhans.get(0).getLat()));
            bundles.putString("lon", String.valueOf(guijiViewBeanjizhans.get(0).getLon()));
            bundles.putString("radius", guijiViewBeanjizhans.get(0).getRadius());
            bundles.putString("address", guijiViewBeanjizhans.get(0).getAddress());
            OverlayOptions optiona = new MarkerOptions()
                    .anchor(10, 30)
                    .extraInfo(bundles)
                    .position(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon()))) //必传参数
                    .perspective(true)
                    .icon(bitmap) //必传参数
                    .draggable(true)
                    .draggable(true)
                    //设置平贴地图，在地图中双指下拉查看效果
                    .flat(true)
                    .alpha(0.5f);
            //在地图上添加Marker，并显示
//            markerMy = (Marker) mBaiduMap.addOverlay(optiona);//百度地图可移动拖拽的  Marker marker
            //构建Marker图标

            //覆盖范围
            String tas = guijiViewBeanjizhans.get(0).getRadius();
            int is = Integer.parseInt(tas);
            if (!TextUtils.isEmpty(ACacheUtil.getFugaiKG())) {
                int kg = Integer.parseInt(ACacheUtil.getFugaiKG());
                if (kg == 0) {//0 展示覆盖范围 其余不展示
                    OverlayOptions ooCirclefugai = new CircleOptions()
//                            .fillColor(0x000000FF)
                            .fillColor(Color.argb(40, 255,
                                    165,
                                    0))
                            .center(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())))
                            .stroke(new Stroke(2, Color.rgb(255,
                                    165,
                                    0)))
                            .radius(is);
                    mBaiduMap.addOverlay(ooCirclefugai);
                }
            }

            Log.d(TAG, "pointsonMapClick: " + points.size());
            //当前与目标基站的距离线
            if (pointsJingbao.size() > 1) {
                //设置折线的属性
                OverlayOptions mOverlayOptions = new PolylineOptions()
                        .width(5)
                        .color(Color.rgb(255, 0, 0))
                        .points(pointsJingbao);
                //在地图上绘制折线
                //mPloyline 折线对象
                Overlay mPolylines = mBaiduMap.addOverlay(mOverlayOptions);


                double distancejizhan = DistanceUtil.getDistance(mylag, new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())));//当前距离基站的位置
                Log.d(TAG, "distanceonMapClicks: " + "distancejizhan" + distancejizhan + "米");
                //语音播报
                Math.round(distancejizhan);
                //距离
                final String tallk = Math.round(distancejizhan) + "米";
                Log.d(TAG, "onReceiveLocation: " + tallk);
                if (Myradius >= Math.round(distancejizhan)) {
//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            /**
//                             *要执行的操作
//                             */
                    startAuto("已进入范围内");
//                        }
//                    };
//                    Timer timer = new Timer();
//                    timer.schedule(task, 3000);//3秒后执行TimeTask的run方法

                } else {

//                    TimerTask task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            /**
//                             *要执行的操作
//                             */
                    startAuto(tallk);
//                        }
//                    };
//                    Timer timer = new Timer();
//                    timer.schedule(task, 3000);//3秒后执行TimeTask的run方法
                }

                //文字覆盖物位置坐标
                //构建TextOptions对象
                OverlayOptions mTextOptions = new TextOptions()
                        .text("" + tallk) //文字内容
                        .bgColor(Color.rgb(224, 255, 255)) //背景色
                        .fontSize(26) //字号
                        .fontColor(Color.rgb(0, 0, 0)) //文字颜色
                        .rotate(0) //旋转角度
                        .position(new LatLng(Double.parseDouble(guijiViewBeanjizhans.get(0).getLat()), Double.parseDouble(guijiViewBeanjizhans.get(0).getLon())));
                //在地图上显示文字覆盖物
                Overlay mText = mBaiduMap.addOverlay(mTextOptions);
                //                   latLngOnclicek = latLng;

            }
        }

    }

    //初始化地图
    private void initMap() {
        if (mylag != null) {
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(mylag).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            ViewLoading.dismiss(MainActivity.this);
        }

    }

    /**
     * @param
     */
    private class MyonclickXian implements View.OnClickListener {
        private String lon;
        private String lat;
        private LatLng mylag;

        public MyonclickXian(LatLng mylag) {
            this.mylag = mylag;

        }

        public MyonclickXian(LatLng mylag, String lat, String lon, Activity activity) {
            this.mylag = mylag;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.baidu:
                    try {
                        LatLng startLatLng = new LatLng(39.940387, 116.29446);
                        LatLng endLatLng = new LatLng(39.87397, 116.529025);
                        String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
                                        "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
                                lat, lon);
                        Intent intent = new Intent();
                        intent.setData(Uri.parse(uri));
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
//                    ToastUtil.showShort(this, "请安装百度地图");
//                        Toast.makeText(MainActivity.this, "请安装百度地图", Toast.LENGTH_SHORT).show();
                        MyToast.showToast("请安装百度地图");
                    }
                    dialog2.dismiss();
                    break;

                case R.id.gaode://角色

                    try {
//                    double gdLatitude = 39.92848272
//                    double gdLongitude = 116.39560823
                        //初始化坐标转换工具类，设置源坐标类型和坐标数据
                        //初始化坐标转换工具类，设置源坐标类型和坐标数据
                        LatLng latLngs = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        CoordinateConverter converter = new CoordinateConverter()
                                .from(BD09LL)
                                .coord(latLngs);

//转换坐标
                        LatLng desLatLng = converter.convert();

                        String uri = String.format("amapuri://route/plan/?dlat=%s&dlon=%s&dname=终点&dev=0&t=0",
                                desLatLng.latitude, desLatLng.longitude);
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setData(Uri.parse(uri));
                        intent.setPackage("com.autonavi.minimap");
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
//                        Toast.makeText(MainActivity.this, "请安装高德地图", Toast.LENGTH_SHORT).show();
                        MyToast.showToast("请安装高德地图");
                    }

                    dialog2.dismiss();
                    break;
            }
        }
    }

    //弹窗窗口
    class Positiv implements View.OnClickListener {
        private final int t;
        private Bundle extraInfo;
        private String dele;

        public Positiv(int t) {
            this.t = t;
        }

        public Positiv(int t, String dele) {
            this.t = t;
            this.dele = dele;
        }

        public Positiv(int t, Bundle extraInfo) {
            this.t = t;
            this.extraInfo = extraInfo;
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View v) {
            if (t == 2) {
                try {
                    dbManagerGuijiView.deleteall();
                    if (guijistart == true) {
                        GuijiViewBean guijiViewBean = new GuijiViewBean();
                        if (mylag != null) {
                            guijiViewBean.setLon(mylag.longitude);
                            guijiViewBean.setLat(mylag.latitude);
                        }
                        try {
                            dbManagerGuijiView.insertStudent(guijiViewBean);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (t == 1) {//传1清空测量标记
                if (points != null) {
                    points.clear();
                    initdatas2();
                } else {

                }

//                Toast.makeText(MainActivity.this, "清空成功", Toast.LENGTH_LONG).show();

//                MyUtils.Viewjizhan(markerMy, mBaiduMap, dataBean);
            } else {
                Log.d(TAG, "onClick: 没有查询的基站位置所以不用画基站");
            }
            if (t == 3) {

                int id = 0;
                try {
                    id = dbManagerJZ.deleteGuanyu(dele);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (id == 1) {
//                    Toast.makeText(MainActivity.this, "删除基站成功", Toast.LENGTH_LONG).show();
                    initdatas();
                    Log.d(TAG, "panderonClick: " + "1");
                } else {
//                    Toast.makeText(MainActivity.this, "删除基站失败", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "panderonClick: " + "2");
                }
            }
            if (t == 4) {
                try {
                    GuijiViewBeanjizhan guijiViewBeanjizhan = dbManagerJZ.forid(Integer.parseInt(extraInfo.getString("id")));
                    guijiViewBeanjizhan.setType(1);
                    int i = dbManagerJZ.updateType(guijiViewBeanjizhan);
//                        判断
                    if (guijiFlag == true) {
                        MyToast.showToast("请先关闭轨显示");
//                        Toast.makeText(MainActivity.this, "请先关闭轨绘制轨迹", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (juliFlage == true) {
                        MyToast.showToast("请先关闭距离测量");
//                        Toast.makeText(MainActivity.this, "请先关闭距离测量", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (i == 1) {
                        MyToast.showToast("设置报警基站成功");
//                        Toast.makeText(MainActivity.this, "设置默认成功", Toast.LENGTH_LONG).show();
                        bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
                        initdatas();//刷新下数据库
                        startJb();//开始警报
//                            mBaiduMap.hideInfoWindow();
                    } else {
                        MyToast.showToast("设置默认失败");
//                        Toast.makeText(MainActivity.this, "设置默认失败", Toast.LENGTH_LONG).show();
                    }
//                        Log.d(TAG, "guijiViewBeanjizhanonClick: " + guijiViewBeanjizhan + "剩下的集合数据" + dbManagerJZ.guijiViewBeans());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private long mPressedTime = 0;


    @Override
    public void onBackPressed() {//重写返回键方法
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if ((mNowTime - mPressedTime) > 2000) {//比较两次按键时间差
            MyToast.showToast("再按一次退出程序");
            mPressedTime = mNowTime;
        } else {//退出程序
            this.finish();
            System.exit(0);
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_UI);
//        initdatas2();
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
        mSensorManager.unregisterListener(this);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        mSearch.destroy();
        mLocClient.disableLocInForeground(true);
        mLocClient.unRegisterLocationListener(myListener);
        mLocClient.stop();
        if (timer != null) {
            timer.cancel();
        }
        if (timer2 != null) {
            timer2.cancel();
        }
        super.onDestroy();
    }
    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 11) {//JzListActivity  返回来的 id 和经纬度
            initdatas2();//刷新当前基站的信息 必要 初始化一下

            try {
                String id = data.getStringExtra("id");
                final GuijiViewBeanjizhan guijiViewBeanjizhan = dbManagerJZ.forid(Integer.parseInt(id));
                Log.d(TAG, "aaonActivityResult: " + guijiViewBeanjizhan);

                View view = View.inflate(MainActivity.this, R.layout.activity_map_info2, null);

                TextView tv_title = view.findViewById(R.id.tv_title);
                String str = "";
                if (guijiViewBeanjizhan.getMnc().equals("0")) {
                    str = "移动";
                } else if (guijiViewBeanjizhan.getMnc().equals("1")) {
                    str = "联通";
                } else if (guijiViewBeanjizhan.getMnc().equals("11")) {
                    str = "电信";
                } else {//如果是cdma显示 sid数据
                    str = "CDMA";
                    TextView tv_sid = view.findViewById(R.id.tv_sid);
                    tv_sid.setText(guijiViewBeanjizhan.getSid());
                    LinearLayout llsid = view.findViewById(R.id.llsid);
                    llsid.setVisibility(View.VISIBLE);
                }
                TextView tv_resources = view.findViewById(R.id.tv_resources);
//            aaa
                if (guijiViewBeanjizhan.getResources().equals("内部数据")) {
                    LinearLayout ll_types = view.findViewById(R.id.ll_types);
                    LinearLayout ll_pci = view.findViewById(R.id.ll_pci);
                    LinearLayout ll_band = view.findViewById(R.id.ll_band);
                    LinearLayout ll_down = view.findViewById(R.id.ll_down);
                    ll_types.setVisibility(View.VISIBLE);
                    ll_pci.setVisibility(View.VISIBLE);
                    ll_band.setVisibility(View.VISIBLE);
                    ll_down.setVisibility(View.VISIBLE);

                    TextView tv_band = view.findViewById(R.id.tv_band);
                    TextView tv_types = view.findViewById(R.id.tv_types);
                    TextView tv_pci = view.findViewById(R.id.tv_pci);
                    TextView tv_down = view.findViewById(R.id.tv_down);
                    tv_band.setText(guijiViewBeanjizhan.getBand() + "");
                    tv_types.setText(guijiViewBeanjizhan.getType() + "");
                    tv_pci.setText(guijiViewBeanjizhan.getPci() + "");
                    tv_down.setText(guijiViewBeanjizhan.getDown() + "");

                    tv_resources.setText(guijiViewBeanjizhan.getResources() + "");
                }
                tv_resources.setText(guijiViewBeanjizhan.getResources() + "");

                tv_title.setText(str + "");
                TextView tv_fugai = view.findViewById(R.id.tv_fugai);
                tv_fugai.setText(guijiViewBeanjizhan.getRadius() + "");
                TextView tv_mnc = view.findViewById(R.id.tv_mnc);
                tv_mnc.setText(guijiViewBeanjizhan.getMnc());
                TextView tv_lac = view.findViewById(R.id.tv_lac);
                tv_lac.setText(guijiViewBeanjizhan.getLac());
                TextView tv_cid = view.findViewById(R.id.tv_cid);
                tv_cid.setText(guijiViewBeanjizhan.getCi());
                TextView tv_address = view.findViewById(R.id.tv_address);
                tv_address.setText(guijiViewBeanjizhan.getAddress());
                TextView tv_lat_lon = view.findViewById(R.id.tv_lat_lon);

                DecimalFormat df = new DecimalFormat(".######");
                final String lats = guijiViewBeanjizhan.getLat();
                final String lons = guijiViewBeanjizhan.getLon();
                double dlat = Double.parseDouble(lats);
                double dlons = Double.parseDouble(lons);
                tv_lat_lon.setText("纬度:" + df.format(dlat) + ",经度:" + df.format(dlons));
                ImageButton bt_openMap = view.findViewById(R.id.bt_openMap);
                Button bt_quanjing = view.findViewById(R.id.bt_quanjing);
                bt_m_locations = view.findViewById(R.id.bt_m_location);//设为警报的点
                if (String.valueOf(guijiViewBeanjizhan.getType()).equals("1")) {
                    bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojing_down));
                } else {
                    bt_m_locations.setBackground(getResources().getDrawable(R.mipmap.baojinglan1));
                }
                bt_m_locations.setVisibility(View.VISIBLE);
                bt_m_locations.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle extraInfo = new Bundle();
                        extraInfo.putString("id", guijiViewBeanjizhan.getId() + "");
//                    aaaaa
                        new CircleDialog.Builder()
                                .setTitle("")
                                .setText("确定要设为报警基站吗")
                                .setTitleColor(Color.parseColor("#00acff"))
                                .setNegative("确定", new Positiv(4, extraInfo))
                                .setPositive("取消", null)
                                .show(getSupportFragmentManager());
                    }
                });
                Button bt_taset = view.findViewById(R.id.bt_taset);//TA值改变数据
                bt_taset.setVisibility(View.VISIBLE);
                bt_taset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Bundle bundle = new Bundle();
                        bundle.putString("id", String.valueOf(guijiViewBeanjizhan.getId()));
                        Intent intent = new Intent(MainActivity.this, TaActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        startActivityForResult(intent, 13);
                    }
                });
                Button bt_m_dele = view.findViewById(R.id.bt_m_dele);
                bt_m_dele.setVisibility(View.VISIBLE);
                if (String.valueOf(guijiViewBeanjizhan.getType()).equals("2")) {
                    bt_m_dele.setBackground(getResources().getDrawable(R.mipmap.markeradd));
                }
                bt_m_dele.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (String.valueOf(guijiViewBeanjizhan.getType()).equals("2")) {//是要增加的基站
                            listMarker.clear();
                            demoAdapteradd = new DemoAdapteradd(listMarker, addcallBack);
//                            Myshow.show(MainActivity.this, extraInfo, dbManagerJZ, mycallback, addcallBack, listMarker, demoAdapteradd);


                        } else {
                            try {
                                String dele = guijiViewBeanjizhan.getId() + "";
                                new CircleDialog.Builder()
                                        .setTitle("")
                                        .setText("确定要删除基站吗")
                                        .setTitleColor(Color.parseColor("#00acff"))
                                        .setNegative("确定", new Positiv(3, dele))
                                        .setPositive("取消", null)
                                        .show(getSupportFragmentManager());

                            } catch (Exception e) {
                                Log.d(TAG, "panderonClick: " + "3" + e.getMessage());
                                e.printStackTrace();
                            }
                        }

                    }
                });
                ImageButton iv_finishs = view.findViewById(R.id.iv_finishs);
                iv_finishs.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mBaiduMap.hideInfoWindow();

                    }
                });
                bt_openMap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        //底部弹出窗

                        dialog2 = new Dialog(MainActivity.this, R.style.ActionSheetDialogStyle);
                        //填充对话框的布局
                        inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_dibushowdaohao, null);
                        //初始化控件
                        TextView baidu = (TextView) inflate.findViewById(R.id.baidu);
//        choosePhoto.setVisibility(View.GONE);
                        TextView gaode = (TextView) inflate.findViewById(R.id.gaode);
//                            baidu.setOnClickListener(new MyonclickXian(mylag));
                        baidu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    LatLng startLatLng = new LatLng(39.940387, 116.29446);
                                    LatLng endLatLng = new LatLng(39.87397, 116.529025);
                                    String uri = String.format("baidumap://map/direction?origin=%s,%s&destination=" +
                                                    "%s,%s&mode=driving&src=com.34xian.demo", mylag.latitude, mylag.longitude,
                                            guijiViewBeanjizhan.getLat(), guijiViewBeanjizhan.getLon());
                                    Intent intent = new Intent();
                                    intent.setData(Uri.parse(uri));
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    MyToast.showToast("请安装百度地图");
                                }
                            }
                        });
                        gaode.setOnClickListener(new MyonclickXian(mylag, guijiViewBeanjizhan.getLat(), guijiViewBeanjizhan.getLon(), MainActivity.this));
                        //将布局设置给Dialog
                        dialog2.setContentView(inflate);
                        //获取当前Activity所在的窗体
                        Window dialogWindow = dialog2.getWindow();
                        //设置Dialog从窗体底部弹出
                        dialogWindow.setGravity(Gravity.BOTTOM);
                        //获得窗体的属性
                        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.y = 20;//设置Dialog距离底部的距离
//       将属性设置给窗体
                        dialogWindow.setAttributes(lp);
                        dialog2.show();//显示对话框
                    }
                });
                bt_quanjing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, PanoramaDemoActivityMain.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("lat", guijiViewBeanjizhan.getLat());
                        bundle.putString("lon", guijiViewBeanjizhan.getLon());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(new LatLng(Double.parseDouble(guijiViewBeanjizhan.getLat()), Double.parseDouble(guijiViewBeanjizhan.getLon()))).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                final InfoWindow mInfoWindow = new InfoWindow(view, new LatLng(Double.parseDouble(guijiViewBeanjizhan.getLat()), Double.parseDouble(guijiViewBeanjizhan.getLon())), -47);
                mBaiduMap.showInfoWindow(mInfoWindow);


            } catch (SQLException e) {
                e.printStackTrace();
                Log.d(TAG, "onActivityResult: " + e.getMessage());
            }

        }
        if (resultCode == 12) {
            initdatas2();
            Log.d(TAG, "onActivityResult: 12");
        }
        if (resultCode == 13) {
            initdatas2();
            Log.d(TAG, "onActivityResult: 12");
        }
        if (resultCode == 14) {
            initdatas2();
            ll_screen.setVisibility(View.VISIBLE);
            Log.d(TAG, "onActivityResult: 12");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private boolean isIgnoringBatteryOptimizations() {
        boolean isIgnoring = false;
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            isIgnoring = powerManager.isIgnoringBatteryOptimizations(getPackageName());
        }
        return isIgnoring;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestIgnoreBatteryOptimizations() {
        try {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            mLocClient = new LocationClient(this);
        } catch (Exception e) {
            Log.e("TAG_onRestart", "onRestart: "+e.getMessage() );
            e.printStackTrace();
        }
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        Log.i("杨路通", "onRestart: ");
    }
}