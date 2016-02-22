package com.example.administrator.LookAndLost.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.administrator.LookAndLost.R;

/**
 * Created by Administrator on 2016/2/19.
 */
public abstract class BaseBarActivity extends BaseActivity {

//    @InjectView(R.id.toolbar)
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        if(!TextUtils.isEmpty(NavUtils.getParentActivityName(this))){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//        toolbar.setNavigationIcon(R.mipmap.ic_launcher);


    }

    protected void setBarTitle(String str){
        toolbar.setTitle(str);
        setSupportActionBar(toolbar);
    }
}
