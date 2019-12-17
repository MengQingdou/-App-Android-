package com.example.weatherapplication.city_manager;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView errorIv;
    private ImageView rightIv;
    private ListView deleteLv;
    private List<String> mDatas;  //这是listView的数据源
    private List<String> deleteCities;  //表示存储了删除的城市信息
    private deleteCityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_city);
        errorIv = findViewById(R.id.delete_iv_error);
        rightIv = findViewById(R.id.delete_iv_right);
        deleteLv = findViewById(R.id.delete_lv);
        mDatas = new ArrayList<>();
        deleteCities = new ArrayList<>();
//        设置监听事件
        errorIv.setOnClickListener(this);
        rightIv.setOnClickListener(this);
//        关于适配器的设置
        adapter = new deleteCityAdapter(this, mDatas, deleteCities);
        deleteLv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> cityList = DBManager.queryAllCityName();
        mDatas.addAll(cityList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.delete_iv_error:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示信息").setMessage("您确定要舍弃更改嘛？");
                builder.setPositiveButton("舍弃更改", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();  // 关闭当前的activity
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            case R.id.delete_iv_right:
                for(int i=0; i<deleteCities.size(); i++){
                    String city = deleteCities.get(i);
//                    调用删除城市的函数
                    DBManager.deleteInfoByCity(city);
                }
//                删除成功，返回上一级页面
                finish();
                break;
        }
    }
}
