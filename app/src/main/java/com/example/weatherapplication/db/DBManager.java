package com.example.weatherapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
    private static final String TAG = "DBManager";
    public static SQLiteDatabase database;
//    初始化数据库信息
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    /*查找数据库当中城市列表*/
    public static List<String> queryAllCityName(){
        Cursor cursor = database.query("info", null, null, null, null, null,null);
        List<String> cityList = new ArrayList<>();
        while (cursor.moveToNext()){
            String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        if(cityList.size() == 0){
            cityList.add("北京");
        }
        return cityList;
    }

    /*根据城市名称，替换信息内容*/
    public static int updateInfoByCity(String city, String content){
        ContentValues values = new ContentValues();
        values.put("content", content);
        return database.update("info", values, "city=?", new String[]{city});
    }
    /*新增一条城市记录*/
    public static long addCityInfo(String city, String content){
        Log.d(TAG, "addCityInfo: ");
        ContentValues values = new ContentValues();
        values.put("city", city);
        values.put("content", content);
        return database.insert("info", null,values);
    }
    /*根据城市名查询数据库当中的内容*/
    public static String queryInfoByCity(String city){
        Cursor cursor = database.query("info", null, "city=?", new String[]{city}, null, null, null);
        if(cursor.getCount() >0 ){
            cursor.moveToFirst();
            String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    /*存储城市天气，要求最多存储5个城市的信息，一旦超过5个城市，就不能存储了，现在方法是获取目前已经存储的数量*/
    public static int getCityCount(){
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        int count = cursor.getCount();
        return count;
    }

    /*查询数据库当中的全部信息*/
    public static List<DatabaseBean> queryAllInfo(){
//        有个bug就是啥也查不出来，size()为零，奇怪为啥呢？对啊，我就没往数据库里加我怎么能查的到呢！！！
        Log.d(TAG, "queryAllInfo: ");
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        List<DatabaseBean> list = new ArrayList<>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String city = cursor.getString(cursor.getColumnIndex("city"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, content);
            list.add(bean);
        }
        Log.d(TAG, "queryAllInfo: "+list.size());
        return list;
    }

    /*根据城市名称，删除这个城市在数据库当中的数据*/
    public static int deleteInfoByCity(String city){
        return database.delete("info", "city=?" ,new String[]{city});
    }

    /*删除表当中所有的数据信息*/
    public static void deleteAllInfo(){
        String sql = "delete from info";
        database.execSQL(sql);
    }
}
