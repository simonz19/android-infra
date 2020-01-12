package com.example.infra;

import android.app.Application;
import android.content.Context;

import com.example.infra.common.constant.Config;

public class ManagerApplication extends Application {

    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        initHttp();
    }

    private void initHttp() {
        Config.initNetWork(Config.IpServerce.UAT);
    }
}
