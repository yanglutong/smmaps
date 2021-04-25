package com.sm.smmap.smmap.Retrofit;

/**
 * 作者：南志强 2016/11/14 14:39
 * 作用：配置各个页面联网地址
 */
public class Constants {
    public static int TIMEOUT=10;

    public static String BASE_URL = "http://192.168.10.204:1235";

    /**
     * 主页面的路径
     */
    public static String HOME_URL  = BASE_URL+"/json/HOME_URL.json";
    /**
     * 图片的基本路径
     */
    public static String BASE_URL_IMAGE  = BASE_URL+"/img";

}
