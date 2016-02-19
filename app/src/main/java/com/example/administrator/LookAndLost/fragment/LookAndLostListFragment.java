package com.example.administrator.LookAndLost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.activity.LookAndLostDetailActivity;
import com.example.administrator.LookAndLost.adapter.AdapterLookAndLost;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/18.
 */
public class LookAndLostListFragment extends BaseListFragment {

    public final static String TYPE_KEY="typeKey";


    public static BaseListFragment newInstance(int type) {
        LookAndLostListFragment fragment=new LookAndLostListFragment();
        Bundle args = new Bundle();
        args.putInt(TYPE_KEY,type);
        fragment.setArguments(args);
        return fragment;
    }

    public LookAndLostListFragment() {

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int type=getArguments().getInt(TYPE_KEY);
        if (type==0){
            recyclerView.setBackgroundColor(0xff555555);
        }
        if (type==1){
            recyclerView.setBackgroundColor(0xffaaaaaa);
        }

        AdapterLookAndLost adapterLookAndLost=new AdapterLookAndLost(getData(),getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterLookAndLost);
    }

    @Override
    public void onLoadMore() {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                this.cancel();
                swipeToLoadLayout.setLoadingMore(false);
            }

        }, 500);
    }

    @Override
    public void onRefresh() {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                this.cancel();
                swipeToLoadLayout.setRefreshing(false);
            }

        }, 500);
    }

    public List<LookAndLostEntity> getData() {
        List<LookAndLostEntity> data=new ArrayList<>();
        LookAndLostEntity entity=new LookAndLostEntity();
        for (int i=0;i<30;i++){
            entity.setTitle("这是第"+i+"个标题");
            data.add(entity);
        }
        return data;
    }

    @Override
    public void onItemClick(View view, int position) {
        startActivity(new Intent(getContext(), LookAndLostDetailActivity.class));
    }

    @Override
    protected int getCreateView() {
        return R.layout.layout_look_and_lost;
    }
}
