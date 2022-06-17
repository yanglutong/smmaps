package com.sm.smmap.smmap.Retrofit;


import com.sm.smmap.smmap.Bean.NumberBean;
import com.sm.smmap.smmap.Login.LoginBena;
import com.sm.smmap.smmap.Retrofit.Bean.JzDataQury;
import com.sm.smmap.smmap.Retrofit.Bean.JzGetData;
import com.sm.smmap.smmap.Utils.Bean.DownBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


/**
 * Created by Administrator on 2018/1/29 0029.
 */

public interface RetrofitService {
    /**
     * 1.移动联通基站
     *
     * @param
     * @param
     * @param lac
     * @return
     */
    @POST(MyURL.UserLogin)
    retrofit2.Call<DataBean> GETBaseStation(@Query("mnc") int mnc, @Query("lac") int lac, @Query("ci") int ci, @Query("key") String key);



    @POST(MyURL.UserAli)
    retrofit2.Call<DataAliBean> GETBaseStationAli(@Query("mnc") int mnc, @Query("lac") int lac, @Query("cellid") int ci,@Header("Authorization") String appKey,@Header("Content-Type") String Content);


    @POST(MyURL.UserAli)
    retrofit2.Call<DataAliBean> GETBaseStationAliCdm(@Query("sid") int sid,@Query("nid") int nid,@Query("cellid") int ci,@Header("Authorization") String appKey,@Header("Content-Type") String Content);

    //    登陆
    @POST(MyURL.Login)
    retrofit2.Call<LoginBena> login(@Query("userName") String userName, @Query("passWord") String passWord);

    //    App更新
    @POST(MyURL.DOWNLOAD)
    retrofit2.Call<DownBean> download(@Query("appId") String appId);

    //    查询次数
    @POST(MyURL.NUMBER)
    retrofit2.Call<NumberBean> NUMBER(@Query("userId") String userId);

    //根据经纬度查询
    @POST(MyURL.SMJIZHAN)
    retrofit2.Call<JzGetData> JzData(@Query("business") String dwId,
                                     @Query("longitudeStart") String longitudeStart,//经度
                                     @Query("latitudeStart") String latitudeStart,//纬度
                                     @Query("longitudeEnd") String longitudeEnd,//经度
                                     @Query("latitudeEnd") String latitudeEnd);//纬度

    @POST(MyURL.BASESTATION)
    retrofit2.Call<JzDataQury> QuerySm(@Query("business") String business,//运营商
                                       @Query("tac") int tac,//tac
                                       @Query("ECI") int ECI//eci
    );
    @GET(MyURL.getBaseUrl)
    Call<Result> QueryJz(@Query("mnc") String mnc,//运营商
                         @Query("lac") int tac,//tac
                         @Query("cell") int ECI,//cid
                         @Query("key") int Apikey);//ke
    @GET(MyURL.getBaseUrl3)
    retrofit2.Call<RequestBody> QueryJz3(@Query("sid") int sid,//运营商
                                         @Query("nid") int nid,//tac
                                         @Query("bid") int bid,//cid
                                         @Query("key") int Apikey);//ke

}
