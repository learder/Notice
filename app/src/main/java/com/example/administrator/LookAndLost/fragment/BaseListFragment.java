package com.example.administrator.LookAndLost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.utils.RecyclerItemClickListener;

import butterknife.InjectView;


/**
 * Created by Administrator on 2016/2/18.
 */
public abstract class BaseListFragment extends BaseFragment implements OnRefreshListener,OnLoadMoreListener,RecyclerItemClickListener.OnItemClickListener {

    @InjectView(R.id.swipe_target)
    public RecyclerView recyclerView;

    @InjectView(R.id.swipeToLoadLayout)
    public SwipeToLoadLayout swipeToLoadLayout;


    public BaseListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getActivity().getWindow().getDecorView().post(new Runnable() {
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(),this));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
