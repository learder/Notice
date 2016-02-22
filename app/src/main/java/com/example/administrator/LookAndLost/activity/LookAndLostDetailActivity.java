package com.example.administrator.LookAndLost.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;

import com.example.administrator.LookAndLost.R;

import butterknife.InjectView;

/**
 * Created by Administrator on 2016/2/19.
 */
public class LookAndLostDetailActivity extends BaseBarActivity {

    @InjectView(R.id.ctl)
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBarTitle("详情");
        collapsingToolbarLayout.setTitle("12345");

    }

    @Override
    protected int getContentView() {
        return R.layout.layout_look_and_lost_detail;
    }


}
