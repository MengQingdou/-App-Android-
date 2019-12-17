package com.example.weatherapplication;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CityFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragmentList;
    public CityFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    int childCount = 0;   //表示ViewPager包含的页数
//    当ViewPager的页面发生改变的时候呢，必须要重写两个函数，才能够做页面数量的更新


    @Override
    public void notifyDataSetChanged() {
        this.childCount = getCount();
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        if(childCount > 0){
            childCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
