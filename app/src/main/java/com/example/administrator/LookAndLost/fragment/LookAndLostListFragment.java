package com.example.administrator.LookAndLost.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.adapter.AdapterLookAndLost;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
@ContentView(R.layout.layout_look_and_lost)
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int type=getArguments().getInt(TYPE_KEY);
        if (type==0){
            recyclerView.setBackgroundColor(0xff555555);
        }
        if (type==1){
            recyclerView.setBackgroundColor(0xff777777);
        }

        AdapterLookAndLost adapterLookAndLost=new AdapterLookAndLost(getData(),getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterLookAndLost);

    }


    @Override
    public void onLoadMore() {

    }

    @Override
    public void onRefresh() {

    }

    public List<LookAndLostEntity> getData() {
        List<LookAndLostEntity> data=new ArrayList<>();
        LookAndLostEntity entity=new LookAndLostEntity();
        for (int i=0;i<100;i++){
            entity.setTitle("这是第"+i+"个标题");
            data.add(entity);
        }
        return data;
    }
}
