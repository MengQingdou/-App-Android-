package com.example.weatherapplication.base;

import android.app.Application;

import com.example.weatherapplication.db.DBManager;

import org.xutils.x;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
