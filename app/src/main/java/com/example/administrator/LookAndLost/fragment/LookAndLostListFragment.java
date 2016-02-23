package com.example.administrator.LookAndLost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.activity.LookAndLostDetailActivity;
import com.example.administrator.LookAndLost.activity.MainActivity;
import com.example.administrator.LookAndLost.adapter.AdapterLookAndLost;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
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
        LookAndLostEntity entity;
        for (int i=0;i<30;i++){
            entity=new LookAndLostEntity();
            entity.setTitle("这是第"+i+"个标题");
            data.add(entity);
        }
        return data;
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View childView, int position) {
        AdapterLookAndLost adapter= (AdapterLookAndLost) recyclerView.getAdapter();
        LookAndLostEntity entity=adapter.getItem(position);
        HashMap hashMap=new HashMap();
        hashMap.put("Id",entity.getEventId());
        hashMap.put("title",entity.getTitle());
        MobclickAgent.onEvent(getContext(),"onItemClick",hashMap);
        Intent intent=new Intent(getContext(), LookAndLostDetailActivity.class);
        ActivityOptionsCompat optionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),childView, MainActivity.TRANSITION);
        try {
            ActivityCompat.startActivity(getActivity(),intent,optionsCompat.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }

    @Override
    protected int getCreateView() {
        return R.layout.layout_look_and_lost;
    }
}
