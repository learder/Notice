package com.example.administrator.LookAndLost.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.LookAndLost.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/2/18.
 */
@ContentView(R.layout.fragment_main)
public class MainFragment extends BaseFragment{

    @ViewInject(R.id.fragment_main_vp)
    public ViewPager vp;

    @ViewInject(R.id.fragment_main_tl)
    public TabLayout tabLayout;



    public MainFragment() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewpagerAdapter adapter=new ViewpagerAdapter(getChildFragmentManager(),getFragmentList());
//        TestAdapter adapter = new TestAdapter();
        vp.setAdapter(adapter);
        tabLayout.setTabMode(adapter.getCount()>4?TabLayout.MODE_SCROLLABLE:TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(vp);
        tabLayout.setTabsFromPagerAdapter(adapter);

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

    class ViewpagerAdapter extends FragmentStatePagerAdapter{

        List<Fragment> fragments;

        public ViewpagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
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
