package com.example.weatherapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.weatherapplication.city_manager.CityManagerActivity;
import com.example.weatherapplication.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView addCityIv;
    private ImageView moreIv;
    private LinearLayout pointLayout;
    private ViewPager mainVp;
    private RelativeLayout outLayout;

//    Viewpager的数据源，这个List是装的什么呢？就是整体一进去那个界面，因为会有好多城市，所以是一个List
    private List<Fragment> fragmentList;
//    表示需要显示的城市的集合，我要显示的城市
    List<String> cityList;
//    表示ViewPager的页数，指示器显示集合
    List<ImageView> imgList;
//    Adapter适配器
    private CityFragmentPagerAdapter adapter;
    private SharedPreferences pref;
    private int bgNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addCityIv = (ImageView) this.findViewById(R.id.main_iv_add);
        moreIv = (ImageView)this.findViewById(R.id.main_iv_more);
        pointLayout = (LinearLayout)this.findViewById(R.id.main_layout_point);
        mainVp = (ViewPager)this.findViewById(R.id.main_vp);
        outLayout = (RelativeLayout) this.findViewById(R.id.main_out_layout);
        exchangeBg();
//      添加点击事件
        addCityIv.setOnClickListener(this);
        moreIv.setOnClickListener(this);
//
        fragmentList = new ArrayList<>();
//        cityList = new ArrayList<>();
        cityList = DBManager.queryAllCityName();   //获取数据库包含的城市信息列表
        imgList = new ArrayList<>();
//        下面这段如果为空的函数，可以在queryAllCityName()函数中设置，如果数据库中查询出来就是0，那么就给他填上北京
//        if(cityList.size() == 0){
//            cityList.add("北京");
//            cityList.add("上海");
//            cityList.add("沈阳");
//        }
        /*因为可能搜索界面点击跳转到此界面会传值，所以此处获取一下*/
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        if(!cityList.contains(city)&&!TextUtils.isEmpty(city)){
            cityList.add(city);
        }

//        初始化ViewPager页面的方法
        initPager();
        adapter = new CityFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        mainVp.setAdapter(adapter);
//        创建小圆点指示器
        initPoint();
//        设置最后一个城市信息
        mainVp.setCurrentItem(fragmentList.size()-1);
//        设置ViewPager页面监听
        setPagerListener();
    }


    //        换壁纸的函数
    public void exchangeBg(){
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
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

//    这个函数就是为了设置小圆点的变化情况的
    private void setPagerListener() {
        /*设置监听事件*/
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (int j =0; j<imgList.size();j++){
                    imgList.get(j).setImageResource(R.mipmap.a1);
                }
                imgList.get(i).setImageResource(R.mipmap.a2);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

//    创建小圆点指示器
    private void initPoint() {

        for (int i = 0 ; i<fragmentList.size(); i++){
            ImageView pIv = new ImageView(this);
            pIv.setImageResource(R.mipmap.a1);
            pIv.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)pIv.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(pIv);
            pointLayout.addView(pIv);
        }
        imgList.get(imgList.size()-1).setImageResource(R.mipmap.a2);
    }

//    初始化所有城市的ViewPager
    private void initPager() {
        /*创建Fragment对象添加到ViewPager数据源当中*/
        for(int i=0; i< cityList.size(); i++){
            CityWeatherFragment cwFrag = new CityWeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putString("city", cityList.get(i));
            cwFrag.setArguments(bundle);
            fragmentList.add(cwFrag);
        }
    }


//    左下角的添加&右下角的更多的监听事件，应该就是响应另一个Activity
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch(v.getId()){
            case R.id.main_iv_add:
                intent.setClass(this, CityManagerActivity.class);
                break;
            case R.id.main_iv_more:
                intent.setClass(this, MoreActivity.class);
                break;
        }
        startActivity(intent);
    }

    /*页面重新加载时，会调用的函数，这个函数在页面获取焦点之前进行调用，此处完成ViewPager页数的更新*/
    @Override
    protected void onRestart() {
        super.onRestart();
//        获取数据库当中的还剩下的城市集合
        List<String> list = DBManager.queryAllCityName();
//        if(list.size() == 0){
//            list.add("淄博");
//        }
        cityList.clear();  //重新加载之前，先清空之前的数据源
        cityList.addAll(list);
//        剩余城市也要创建对应的Fragment页面
        fragmentList.clear();
        initPager();
        adapter.notifyDataSetChanged();
//        页面数量发生改变，指示器的数量也会发生变化,重新设置添加指示器
        imgList.clear();
        pointLayout.removeAllViews();  // 将布局当中所有元素全部移除
        initPoint();
        mainVp.setCurrentItem(fragmentList.size()-1);
    }
}
