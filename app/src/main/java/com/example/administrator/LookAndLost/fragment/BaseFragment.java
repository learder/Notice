package com.example.administrator.LookAndLost.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/2/18.
 */
public abstract class BaseFragment extends Fragment{
    private boolean injected = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        injected = true;
        View view=inflater.inflate(getCreateView(),container,false);
        ButterKnife.inject(this,view);
        return view;
    }

    protected abstract int getCreateView();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
