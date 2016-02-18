package com.example.administrator.LookAndLost;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/2/18.
 */
public class FindApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
    }
}
