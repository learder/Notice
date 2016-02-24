package com.example.administrator.LookAndLost.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.activity.LookAndLostDetailActivity;
import com.example.administrator.LookAndLost.activity.MainActivity;
import com.example.administrator.LookAndLost.adapter.AdapterLookAndLost;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.network.CommandIdManager;
import com.example.administrator.LookAndLost.utils.network.NetRequest;
import com.example.administrator.LookAndLost.utils.network.ParamManager;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/2/18.
 */
public class LookAndLostListFragment extends BaseListFragment {

    public final static String TYPE_KEY="typeKey";
    private LostListResultCallBack lostListResultCallBack=new LostListResultCallBack();
    private LookListResultCallBack lookListResultCallBack=new LookListResultCallBack();
    AdapterLookAndLost adapterLookAndLost;


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
        if (type== Constants.TYPE_LOOK){
            recyclerView.setBackgroundColor(0xff555555);
        }
        if (type==Constants.TYPE_LOST){
            recyclerView.setBackgroundColor(0xffaaaaaa);
        }

        adapterLookAndLost=new AdapterLookAndLost(null,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapterLookAndLost);

    }

    @Override
    public void onLoadMore() {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                JSONObject jsonObject=new JSONObject();
                if (getArguments().getInt(TYPE_KEY)==Constants.TYPE_LOOK){
                    try {
                        jsonObject.put(ParamManager.Common.PAGE,1);
                        jsonObject.put(ParamManager.Common.SIZE,20);
                        CommandIdManager.getLookList(jsonObject,lookListResultCallBack,false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        jsonObject.put(ParamManager.Common.PAGE,1);
                        jsonObject.put(ParamManager.Common.SIZE,20);
                        CommandIdManager.getLostList(jsonObject,lostListResultCallBack,false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, 500);
    }


    private class LookListResultCallBack extends NetRequest.ResultCallback<List<LookAndLostEntity>> {

        @Override
        public void onError(int error, String str) {
            if (BuildConfig.DEBUG) Log.d("onError", "error-->"+error+"  str-->"+str);
            swipeToLoadLayout.setRefreshing(false);
        }

        @Override
        public void onResponse(final List<LookAndLostEntity> response) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setRefreshing(false);
                    adapterLookAndLost.setDatas(response);
                }
            });


        }
    }

    private class LostListResultCallBack extends NetRequest.ResultCallback<List<LookAndLostEntity>> {

        @Override
        public void onError(int error, String str) {
            swipeToLoadLayout.setRefreshing(false);
        }

        @Override
        public void onResponse(final List<LookAndLostEntity> response) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    swipeToLoadLayout.setRefreshing(false);
                    adapterLookAndLost.setDatas(response);
                }
            });

        }
    }

    @Override
    public void onRefresh() {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                JSONObject jsonObject=new JSONObject();
                if (getArguments().getInt(TYPE_KEY)==Constants.TYPE_LOOK){
                    try {
                        jsonObject.put(ParamManager.Common.PAGE,1);
                        jsonObject.put(ParamManager.Common.SIZE,20);
                        CommandIdManager.getLookList(jsonObject,lookListResultCallBack,false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    try {
                        jsonObject.put(ParamManager.Common.PAGE,1);
                        jsonObject.put(ParamManager.Common.SIZE,20);
                        CommandIdManager.getLostList(jsonObject,lostListResultCallBack,false);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        }, 500);
    }


    @Override
    public void onItemClick(RecyclerView recyclerView, View childView, int position) {
        AdapterLookAndLost adapter= (AdapterLookAndLost) recyclerView.getAdapter();
        LookAndLostEntity entity=adapter.getItem(position);
        if (BuildConfig.DEBUG)
            Log.d("LookAndLostListFragment", "position-->" + position + " entity-->" + entity.toString());
        HashMap hashMap=new HashMap();
        hashMap.put("Id",entity.getEventId());
        hashMap.put("title",entity.getTitle());
        MobclickAgent.onEvent(getContext(),"onItemClick",hashMap);
        Intent intent=new Intent(getContext(), LookAndLostDetailActivity.class);
        intent.putExtra(Constants.KEY_LOOK_AND_LOST_ENTITY,entity);
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
