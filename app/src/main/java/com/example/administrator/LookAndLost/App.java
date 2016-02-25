package com.example.administrator.LookAndLost;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by Administrator on 2016/2/18.
 */
public class App extends Application {
    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        setI(this);
        Fresco.initialize(this);
    }

    /**
     * findbugs 不能将常量直接复制给静态变量 所以这里绕了一圈
     * @param wlanApplication
     */
    public static void setInstance(App wlanApplication){
        App.instance=wlanApplication;
    }

    /**
     * findbugs 不能将常量直接复制给静态变量 所以这里绕了一圈
     * @param wlanApplication
     */
    public void setI (App wlanApplication) {
        setInstance(wlanApplication);
    }


    public static Context getAppContext(){
        return instance.getApplicationContext();
    }
}
