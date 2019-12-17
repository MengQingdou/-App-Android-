package com.example.weatherapplication.city_manager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.weatherapplication.MainActivity;
import com.example.weatherapplication.R;
import com.example.weatherapplication.base.BaseActivity;
import com.example.weatherapplication.bean.WeatherBean;
import com.example.weatherapplication.db.DBManager;
import com.google.gson.Gson;

import java.util.List;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener{
    private EditText searchEt;
    private ImageView submitIv;
    private GridView searchGv;
    String[] hotCities = {"北京","上海","广州","深圳","珠海","佛山","南京","苏州","厦门","长沙","成都","福州",
            "杭州","武汉","青岛","西安","太原","沈阳","重庆","天津","南宁"};
    String url1 = "http://api.map.baidu.com/telematics/v3/weather?location=";
    String url2 = "&output=json&ak=FkPhtMBK0HTIQNh7gG4cNUttSTyr0nzo";
    String city;
    private ArrayAdapter<String> adapter;
    private List<String> alreadyCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);
        submitIv.setOnClickListener(this);
//        设置适配器
        adapter = new ArrayAdapter<>(this, R.layout.item_hot_city, hotCities);
        searchGv.setAdapter(adapter);
        setListener();
    }
//    设置SearchGv的监听
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCities[position];
                alreadyCity = DBManager.queryAllCityName();
                if(alreadyCity.contains(city)){
                    Toast.makeText(getApplicationContext(), "该城市已经添加，请查看！",Toast.LENGTH_SHORT).show();
                }
                String url = url1 + city + url2;
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.search_iv_submit:
                city = searchEt.getText().toString();
                alreadyCity = DBManager.queryAllCityName();
                if(alreadyCity.contains(city)){
                    Toast.makeText(getApplicationContext(), "该城市已经添加，请查看！",Toast.LENGTH_SHORT).show();
                }
                if(!TextUtils.isEmpty(city)){
//                    判断是否能找到这个城市，利用gson中的error值，
                    String url = url1 + city + url2;
                    loadData(url);
                }else{
                    Toast.makeText(this, "输入内容不能为空",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        if(weatherBean.getError() == 0){
            Intent intent = new Intent(this, MainActivity.class);
//            清空之前的栈，并且创建一个新的栈
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city", city);
            startActivity(intent);
        }else{
            Toast.makeText(this, "暂时未收入此城市天气信息......",Toast.LENGTH_SHORT).show();
        }
    }
}
