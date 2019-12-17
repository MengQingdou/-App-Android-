package com.example.weatherapplication.city_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.weatherapplication.R;
import com.example.weatherapplication.bean.WeatherBean;
import com.example.weatherapplication.db.DatabaseBean;
import com.google.gson.Gson;

import java.util.List;

public class CityManagerAdapter extends BaseAdapter {
    Context content;
    List<DatabaseBean> mDatas;

    public CityManagerAdapter(Context content, List<DatabaseBean> mDatas) {
        this.content = content;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    在getView当中对于每一个item的内容进行初始化，并且进行ItemView的设置
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(content).inflate(R.layout.item_city_manager, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        DatabaseBean bean = mDatas.get(position);
        holder.cityTv.setText(bean.getCity());
//        拿到bean中的content的Gson字符串，将其解析成WeatherBean，
        WeatherBean weatherBean = new Gson().fromJson(bean.getContent(), WeatherBean.class);
//        获取今日天气情况
        WeatherBean.ResultsBean.WeatherDataBean dataBean = weatherBean.getResults().get(0).getWeather_data().get(0);
        holder.conTv.setText("天气:" + dataBean.getWeather());
        String[] split = dataBean.getDate().split("：");
        String todayTemp = split[1].replace(")", "");
        holder.currentTv.setText(todayTemp);
        holder.windTv.setText(dataBean.getWind());
        holder.tempRangeTv.setText(dataBean.getTemperature());
        return convertView;
    }

    class ViewHolder{
        TextView cityTv, conTv, currentTv, windTv, tempRangeTv;
        public ViewHolder(View itemView){
            cityTv = (TextView)itemView.findViewById(R.id.item_city_tv_city);
            conTv = (TextView)itemView.findViewById(R.id.item_city_tv_condition);
            currentTv = (TextView)itemView.findViewById(R.id.item_city_tv_temp);
            windTv = (TextView)itemView.findViewById(R.id.item_city_tv_wind);
            tempRangeTv = (TextView)itemView.findViewById(R.id.item_city_tv_temprange);
        }
    }
}