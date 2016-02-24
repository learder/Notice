package com.example.administrator.LookAndLost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.FileUtil;
import com.example.administrator.LookAndLost.utils.SPUtils;
import com.example.administrator.LookAndLost.utils.StatusUtils;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/20.
 */
public class SplashActivity extends BaseActivity {

    boolean isCopySuccess=true;
    String city;

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusUtils.hideStatusBar(this);
        city=SPUtils.get4Sp(this, Constants.KEY_CITY,"");
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        if (TextUtils.isEmpty(city)){
            new Thread() {

                public void run() {
                    try {
                        if (BuildConfig.DEBUG) Log.d("SplashActivity", "城市数据库拷贝到文件夹开始");
                    FileUtil.copyBigDataToSD(context, "prov_city_area_street_db-journal",
                            FileUtil.createSDFile("wlan/dbDir", Constants.LOCATION_CITY_DB_NAME_JOURNAL).getPath());
                    FileUtil.copyBigDataToSD(context, "prov_city_area_street_db",
                            FileUtil.createSDFile("wlan/dbDir", Constants.LOCATION_CITY_DB_NAME_DB).getPath());
                    SPUtils.save2Sp(context, Constants.LOCATION_SPUTILS_COPY_DB_KEY, true);
                        if (BuildConfig.DEBUG) Log.d("SplashActivity", "城市数据库拷贝到文件夹结束");
                        skipActivity();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        isCopySuccess=false;
                        Snackbar.make(View.inflate(context,getContentView(), Layou),"复制城市地址失败，请重试，或者点这里，使用默认城市“杭州”来跳过！",Snackbar.LENGTH_INDEFINITE).setAction("", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SPUtils.save2Sp(context, Constants.KEY_CITY,"杭州市");
                                startActivity(new Intent(context,MainActivity.class));
                            }
                        });

                    }
                }

            }.start();
        }else {
            skipActivity();
        }
    }




    private void skipActivity(){
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                this.cancel();
                if (TextUtils.isEmpty(city)){
                    startActivity(new Intent(context,CityActivity.class));
                }else {
                    startActivity(new Intent(context,MainActivity.class));
                }
                finish();
            }

        }, 2000);
    }

}
