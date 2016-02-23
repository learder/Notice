package com.example.administrator.LookAndLost.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

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
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_close_clear_cancel);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }else {
            finish();
        }
    }

    protected void setBarTitle(String str){
        toolbar.setTitle(str);
        setSupportActionBar(toolbar);
    }
}
