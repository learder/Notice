package com.example.administrator.LookAndLost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.FileUtils;
import com.example.administrator.LookAndLost.utils.SPUtils;

/**
 * Created by Administrator on 2016/2/20.
 */
public class SplashActivity extends BaseActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String city=SPUtils.get4Sp(this, Constants.KEY_CITY,"");
        if (TextUtils.isEmpty(city)){
            new Thread() {

                public void run() {
//                    try {
                        if (BuildConfig.DEBUG) Log.d("SplashActivity", "城市数据库拷贝到文件夹开始");
//                        FileUtils.copyAssetFileToFiles(context,"prov_city_area_street_db-journal","prov_city_area_street.db-journal");
                        FileUtils.copy(context,"prov_city_area_street_db-journal", Environment.getExternalStorageDirectory().getPath()+"/LookAndLost/","prov_city_area_street.db-journal");
                        FileUtils.copy(context,"prov_city_area_street_db", Environment.getExternalStorageDirectory().getPath()+"LookAndLost","prov_city_area_street.db");

//                        FileUtils.copyAssetFileToFiles(context,"prov_city_area_street_db","prov_city_area_street.db");
//                        FileUtil.copyBigDataToSD(context, "prov_city_area_street_db-journal",
//                                FileUtil.createSDFile("LookAndLost/dbDir","prov_city_area_street.db-journal").getPath());
//                        FileUtil.copyBigDataToSD(context, "prov_city_area_street_db",
//                                FileUtil.createSDFile("LookAndLost/dbDir","prov_city_area_street.db").getPath());
                        SPUtils.save2Sp(context,"prov_city_area_street.db-journal", true);
                        if (BuildConfig.DEBUG) Log.d("SplashActivity", "城市数据库拷贝到文件夹结束");
                        startActivity(new Intent(context,CityActivity.class));
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//
//                    }
                }

            }.start();
        }else {
            new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
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
}
