package com.sm.smmap.smmap.drive_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sm.smmap.smmap.OrmSqlLite.Bean.Student;

import java.util.ArrayList;

public class MySql extends SQLiteOpenHelper {
    //数据版本号 version
    //创建数据库
    public MySql(Context context) {
        super(context, "person.db", null, 1);
    }
    //在应用第一次使用数据库的时候，系统默认调用这个方法
    //创建数据库的表格，只运行一次，创建表格，除非卸载否则不再运行
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table stu(id integer primary key autoincrement,lteMccString varchar," +
                "lteMncString varchar,lteTac varchar,ltePci varchar," +
                "lteCi varchar,lteEarfac varchar,lteBand varchar,operatorName varchar,operatorType varchar)";
        //执行
        db.execSQL(sql);
    }
    //用于版本号的升级
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //插入方法
    public void insertAdd(DriveTest4GBean p){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("lteMccString",p.getLteMccString());
        values.put("lteMncString",p.getLteMncString());
        values.put("lteTac",p.getLteTac());
        values.put("ltePci",p.getLtePci());
        values.put("lteCi",p.getLteCi());
        values.put("lteEarfac",p.getLteEarfac());
        values.put("lteBand",p.getLteBand());
        values.put("operatorName",p.getOperatorName());
        values.put("operatorType",p.getOperatorType());
        db.insert("stu","name",values);
    }
    //查询方法
    public ArrayList<DriveTest4GBean> selectAdd(){
        ArrayList<DriveTest4GBean> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str="select * from stu";
        Cursor cursor = db.rawQuery(str, null);
        /*private String lteMccString;
        private String lteMncString;
    private String lteTac;
    private String ltePci;
    private String lteCi;
    private String lteEarfac;
    private String lteBand;
    private String operatorName;
    private String operatorType;*/
        while(cursor.moveToNext()){
            String lteMccString = cursor.getString(cursor.getColumnIndex("lteMccString"));
            String lteMncString = cursor.getString(cursor.getColumnIndex("lteMncString"));
            String lteTac = cursor.getString(cursor.getColumnIndex("lteTac"));
            String ltePci = cursor.getString(cursor.getColumnIndex("ltePci"));
            String lteCi = cursor.getString(cursor.getColumnIndex("lteCi"));
            String lteEarfac = cursor.getString(cursor.getColumnIndex("lteEarfac"));
            String lteBand = cursor.getString(cursor.getColumnIndex("lteBand"));
            String operatorName = cursor.getString(cursor.getColumnIndex("operatorName"));
            String operatorType = cursor.getString(cursor.getColumnIndex("operatorType"));
            list.add(new DriveTest4GBean(lteMccString,lteMncString,lteTac,ltePci,lteCi,lteEarfac,lteBand,operatorName,operatorType));
        }
        return list;
    }
    //根据名字删除内容
    public void deleteAdd(String name){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("stu","name=?",new String[]{name});
    }
    //根据姓名修改年龄
    public void updateAdd(String name,int age){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("age",age);
        db.update("sut",values,"name=?",new  String[]{name});
    }
}