package com.example.administrator.LookAndLost.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.fragment.MainFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BarBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.nav_view)
    public NavigationView navNv;

    @InjectView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    @InjectView(R.id.main_fab)
    public FloatingActionButton mainFab;
    @OnClick(R.id.main_fab)
    public void fabOnClick(View view){
//        Snackbar.make(view,"ni hao ",Snackbar.LENGTH_SHORT).show();
        startActivity(new Intent(this,LookAndLostReleaseActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navNv.setNavigationItemSelectedListener(this);

        Fragment fragment= MainFragment.newInstance();
//        Fragment fragment= LookAndLostListFragment.newInstance(1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fl,fragment);
        fragmentTransaction.commitAllowingStateLoss();


//        ListView listview= (ListView) findViewById(R.id.listview);
//        listview.setAdapter(new SimpleAdapter(this,getData(),android.R.layout.simple_list_item_1,new String[]{"string"}, new int[]{android.R.id.text1}));
        List<Map<String,String>> ls=getData();
        TextView tv;
//        for (Map<String,String> map:ls){
//            tv=new TextView(this);
//            tv.setText(map.get("string"));
//            ll.addView(tv);
//        }
        AppBarLayout app_abl= (AppBarLayout) findViewById(R.id.app_abl);
        app_abl.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (BuildConfig.DEBUG) Log.d("MainActivity", "verticalOffset-->" + verticalOffset);
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    private List<Map<String,String>> getData(){
        List<Map<String,String>> list=new ArrayList<>();
        Map<String,String> map;
        for (int i=0;i<100;i++){
            map=new HashMap<>();
            map.put("string",i+"");
            list.add(map);
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
