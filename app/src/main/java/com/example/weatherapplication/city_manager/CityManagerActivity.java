package com.example.weatherapplication.city_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.weatherapplication.R;
import com.example.weatherapplication.db.DBManager;
import com.example.weatherapplication.db.DatabaseBean;

import java.util.ArrayList;
import java.util.List;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "CityManagerActivity";

    private ImageView addIv;
    private ImageView backIv;
    private ImageView deleteIv;
    private ListView cityLv;
    private List<DatabaseBean> mDatas; //显示列表数据源
    private CityManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
        addIv = (ImageView)this.findViewById(R.id.city_iv_add);
        backIv = (ImageView)this.findViewById(R.id.city_iv_back);
        deleteIv = (ImageView)this.findViewById(R.id.city_iv_delete);
        cityLv = (ListView)this.findViewById(R.id.city_lv);
        mDatas = new ArrayList<>();
//        添加点击事件
        addIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
//        设置适配器
        adapter = new CityManagerAdapter(this, mDatas);
        cityLv.setAdapter(adapter);
    }
/*获取数据库当中真实数据源，添加到原有数据源当中，提示适配器更新*/
    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        List<DatabaseBean> list = DBManager.queryAllInfo();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.city_iv_add:
                int cityCount = DBManager.getCityCount();
                if(cityCount < 5){
                    Intent intent = new Intent(this, SearchCityActivity.class);
                    startActivity(intent);
                }else
                    Toast.makeText(this, "存储城市数量已达上限，请删除后再增加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.city_iv_back:
                finish(); //直接返回到上一级，主Activity
                break;
            case R.id.city_iv_delete:
                Intent intent1 = new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
