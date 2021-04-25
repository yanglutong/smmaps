package com.sm.smmap.smmap.Utils;


import android.os.Environment;

public class AppConfig {
    public static final long dealDate = 146989440000000l;
    public static boolean isReturn = false;
    /**
     * 数据文件的根路径 目前定在外部存储卡中
     */
    public static final String BasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
    /**
     * 数据库文件夹名称
     */
//	public static final String DBDirectoryName = "prower";
    public static final String DBDirectoryName = "jclxGPS";
    /**
     * 临时证据文件夹
     */
    public static final String DBDirectoryNameL = "smmaplog";
    /**
     * 表箱照片
     */
    public static final String BOXIMG = "表箱照片";
    /**
     * 表箱照片
     */
    public static final String COLLECTIMG = "采集器照片";
    /**
     * 杆塔照片
     */
    public static final String GT_IMG = "杆塔照片";
    /**
     * 台区照片
     */
    public static final String TQ_IMG = "台区照片";
    /**
     * 集中器照片
     */
    public static final String JZQIMG = "集中器照片";
    /**
     * 台区名字
     */
    public static String TQName = "";
    /**
     * 总文件夹的路径
     */
    public static final String RootFile = BasePath + "/" + DBDirectoryName + "/";
    /**
     * 总文件夹的路径
     */
    public static final String RootFileL = BasePath + "/" + DBDirectoryNameL + "/";


    /**
     * 在手机里存放数据库的位置
     */
    public static final String DB_PATH = RootFile + "/jclxGps.db";

}
