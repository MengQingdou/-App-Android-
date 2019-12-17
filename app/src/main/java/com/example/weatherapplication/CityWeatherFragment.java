package com.example.weatherapplication;


import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.weatherapplication.base.BaseFragment;
import com.example.weatherapplication.bean.WeatherBean;
import com.example.weatherapplication.db.DBManager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class CityWeatherFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG = "CityWeatherFragment";
    private TextView tempTv;
    private TextView cityTv;
    private TextView conditionTv;
    private TextView windTv;
    private TextView tempRangeTv;
    private TextView dateTv;
    private TextView clothIndexTv;
    private TextView carIndexTv;
    private TextView coldIndexTv;
    private TextView sportIndexTv;
    private TextView raysIndexTv;
    private ImageView dayIv;
    private LinearLayout futureLayout;
    private ScrollView outLayout;
    private String url1 = "http://api.map.baidu.com/telematics/v3/weather/?location=";
    private String url2 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";
    private List<WeatherBean.ResultsBean.IndexBean> indexList;
    private String city;
    private SharedPreferences pref;
    private int bgNum;

    public CityWeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        exchangeBg();
//        可以通过Activity传值获取到当前fragment加载的是哪个城市的天气情况,利用Bundle
        Bundle bundle = getArguments();
        city = bundle.getString("city");
        String url = url1 + city + url2;
//        调用父类获取数据的方法
        loadData(url);
        return view;
    }

    @Override
    public void onSuccess(String result) {
//        解析并展示数据
        parseShowData(result);
//        更新数据
        int i = DBManager.updateInfoByCity(city,result);
        Log.d(TAG, "onSuccess: "+ city+i);
        if(i <= 0){
//            更新数据库失败，说明没有这条城市信息，增加这个城市记录
            DBManager.addCityInfo(city, result);
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
//        数据库当中查找上一次信息显示在Fragment当中
        String s = DBManager.queryInfoByCity(city);
        if(!TextUtils.isEmpty(s)){
            parseShowData(s);
        }
    }
//    以上我们对数据库进行增加修改以及查询的工作，在哪里面进行数据库的添加呢？
//    是在新的城市管理界面进行的，那么我们创建新的Activity及其相应的布局


    private void parseShowData(String result) {
//        使用Gson解析数据
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        WeatherBean.ResultsBean resultsBean = weatherBean.getResults().get(0);
//        获取指数信息集合列表
        indexList = resultsBean.getIndex();
//        设置TextView
        dateTv.setText(weatherBean.getDate());
        cityTv.setText(resultsBean.getCurrentCity());
//        获取今日天气情况
        WeatherBean.ResultsBean.WeatherDataBean todayDataBean = resultsBean.getWeather_data().get(0);
        windTv.setText(todayDataBean.getWind());
        tempRangeTv.setText(todayDataBean.getTemperature());
        conditionTv.setText(todayDataBean.getWeather());
//        获取实时天气温度情况，需要处理字符串
        String[] split = todayDataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        tempTv.setText(todayTemp);
//        设置显示的天气情况图片
        Picasso.with(getActivity()).load(todayDataBean.getDayPictureUrl()).into(dayIv);
//        获取未来三天的天气情况，加载到layout当中
        List<WeatherBean.ResultsBean.WeatherDataBean> futureList = resultsBean.getWeather_data();
        futureList.remove(0);
        for (int i = 0;i<futureList.size();i++){
            View itemView = LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            futureLayout.addView(itemView);
            TextView idataTv = itemView.findViewById(R.id.item_center_tv_data);
            TextView iconTv = itemView.findViewById(R.id.item_center_tv_con);
            TextView itemprangeTv = itemView.findViewById(R.id.item_center_tv_temp);
            ImageView iTv = itemView.findViewById(R.id.item_center_iv);
//            获取对应的位置的天气情况
            WeatherBean.ResultsBean.WeatherDataBean dataBean = futureList.get(i);
            idataTv.setText(dataBean.getDate());
            iconTv.setText(dataBean.getWeather());
            itemprangeTv.setText(dataBean.getTemperature());
            Picasso.with(getActivity()).load(dataBean.getDayPictureUrl()).into(iTv);
        }
    }




    private void initView(View view) {
//        用于初始化控件操作
        tempTv = (TextView)view.findViewById(R.id.frag_tv_currentTemp);
        cityTv = (TextView)view.findViewById(R.id.frag_tv_city);
        conditionTv = (TextView)view.findViewById(R.id.frag_tv_condition);
        windTv = (TextView)view.findViewById(R.id.frag_tv_wind);
        tempRangeTv = (TextView)view.findViewById(R.id.frag_tv_tempRange);
        dateTv = (TextView)view.findViewById(R.id.frag_tv_date);
        clothIndexTv = (TextView)view.findViewById(R.id.frag_index_tv_dress);
        carIndexTv = (TextView)view.findViewById(R.id.frag_index_tv_washcar);
        coldIndexTv = (TextView)view.findViewById(R.id.frag_index_tv_cold);
        sportIndexTv = (TextView)view.findViewById(R.id.frag_index_tv_sport);
        raysIndexTv = (TextView)view.findViewById(R.id.frag_index_tv_rays);
        dayIv = (ImageView)view.findViewById(R.id.frag_iv_today);
        futureLayout = (LinearLayout)view.findViewById(R.id.frag_center_layout);
        outLayout = (ScrollView) view.findViewById(R.id.frag_scrollview);
//        设置点击事件
        clothIndexTv.setOnClickListener(this);
        carIndexTv.setOnClickListener(this);
        coldIndexTv.setOnClickListener(this);
        sportIndexTv.setOnClickListener(this);
        raysIndexTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (v.getId()){
            case R.id.frag_index_tv_dress:
                builder.setTitle("穿衣指数");
                WeatherBean.ResultsBean.IndexBean indexBean = indexList.get(0);
                String msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_washcar:
                builder.setTitle("洗车指数");
                indexBean = indexList.get(1);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_cold:
                builder.setTitle("感冒指数");
                indexBean = indexList.get(2);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_sport:
                builder.setTitle("运动指数");
                indexBean = indexList.get(3);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
            case R.id.frag_index_tv_rays:
                builder.setTitle("紫外线指数");
                indexBean = indexList.get(4);
                msg = indexBean.getZs()+"\n"+indexBean.getDes();
                builder.setMessage(msg);
                builder.setPositiveButton("确定",null);
                break;
        }
//        让我们的对话框显示出来
        builder.create().show();
    }

    public void exchangeBg(){
        pref = getActivity().getSharedPreferences("bg_pref", MODE_PRIVATE);
        bgNum = pref.getInt("bg", 2);
        switch (bgNum){
            case 0:
                outLayout.setBackgroundResource(R.mipmap.bg);
                break;
            case 1:
                outLayout.setBackgroundResource(R.mipmap.bg2);
                break;
            case 2:
                outLayout.setBackgroundResource(R.mipmap.bg3);
                break;
        }
    }
}
