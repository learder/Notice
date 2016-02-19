package com.example.administrator.LookAndLost.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.utils.SystemBarTintManager;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/18.
 */
public abstract class BaseActivity extends AppCompatActivity{
    SystemBarTintManager tintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.inject(this);
        if(Build.VERSION.SDK_INT <= 19){
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    protected abstract int getContentView();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setStatusColor(int color){
        if(Build.VERSION.SDK_INT > 19){
            getWindow().setStatusBarColor(color);
        }else{
            tintManager.setStatusBarTintColor(color);
        }
    }
    public void setNavigationColor(int color){
        tintManager.setNavigationBarTintColor(color);
    }
}
