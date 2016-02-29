package com.example.administrator.LookAndLost.fragment;

import android.os.Bundle;

import com.example.administrator.LookAndLost.R;

/**
 * Created by Administrator on 2016/2/28.
 */
public class AboutFragment extends BaseFragment {


    public static BaseFragment newInstance() {
        AboutFragment fragment=new AboutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AboutFragment() {

    }


    @Override
    protected int getCreateView() {
        return R.layout.layout_about;
    }
}
