package com.example.administrator.LookAndLost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.LookAndLost.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Administrator on 2016/2/18.
 */
public class MainFragment extends BaseFragment{

    @InjectView(R.id.fragment_main_vp)
    public ViewPager vp;

    @InjectView(R.id.fragment_main_tl)
    public TabLayout tabLayout;


    public static BaseFragment newInstance() {
        MainFragment fragment=new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public MainFragment() {

    }

    @Override
    protected int getCreateView() {
        return R.layout.fragment_main;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewpagerAdapter adapter=new ViewpagerAdapter(getChildFragmentManager(),getFragmentList());
        vp.setAdapter(adapter);
        tabLayout.setTabMode(adapter.getCount()>4?TabLayout.MODE_SCROLLABLE:TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp);
//        tabLayout.setTabsFromPagerAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public List<Fragment> getFragmentList(){
        List<Fragment> list=new ArrayList<>();
        Fragment fragment=LookAndLostListFragment.newInstance(0);
        Fragment fragment1=LookAndLostListFragment.newInstance(1);
        list.add(fragment);
        list.add(fragment1);
        return list;
    }

    class TestAdapter extends PagerAdapter{

        List<View> views=new ArrayList<>();

        public TestAdapter() {
            this.views = views;
            TextView textView=new TextView(getContext());
            textView.setBackgroundColor(0xff885522);
            TextView textView1=new TextView(getContext());
            textView1.setBackgroundColor(0xff556633);
            views.add(textView);
            views.add(textView1);
        }

        @Override
        public int getCount() {
            return views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object==view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=views.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView(views.get(position));
        }
    }

    class ViewpagerAdapter extends FragmentPagerAdapter{

        List<Fragment> fragments;

        public ViewpagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((Fragment) object).getView();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position==0){
                return "寻物";
            }else {
                return "启示";
            }
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments==null?0:fragments.size();
        }


    }


}
