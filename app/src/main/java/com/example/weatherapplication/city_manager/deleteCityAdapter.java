package com.example.weatherapplication.city_manager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapplication.R;

import java.util.List;


/*listView适配器的固定的格式
* 1.定义变量，写好构造函数
* 2.把重写的那些函数的return都写好
* 3.写好ViewHolder
* 4.getView方法的重写
*/

public class deleteCityAdapter extends BaseAdapter {
    private Context context;
    private List<String> mDatas;
    private List<String> deleteCities;

    public deleteCityAdapter(Context context, List<String> mDatas, List<String> deleteCities) {
        this.context = context;
        this.mDatas = mDatas;
        this.deleteCities = deleteCities;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_deletecity, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String city = mDatas.get(position);
        holder.tv.setText(city);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatas.remove(city);
                deleteCities.add(city);
                notifyDataSetChanged();  // 删除了提示适配器更新
            }
        });
        return convertView;
    }

    class ViewHolder{
        private TextView tv;
        private ImageView iv;

        public ViewHolder(View itemView) {
            tv = itemView.findViewById(R.id.item_delete_tv);
            iv = itemView.findViewById(R.id.item_delete_iv);
        }
    }
}
