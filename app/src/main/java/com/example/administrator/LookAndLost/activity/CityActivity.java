package com.example.administrator.LookAndLost.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;

import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.adapter.LocationCityListViewAdapter;
import com.example.administrator.LookAndLost.entity.CityEntity;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.SPUtils;
import com.example.administrator.LookAndLost.utils.manger.LocationCitySearchQuery;
import com.example.administrator.LookAndLost.view.SidebarView;
import com.example.administrator.LookAndLost.view.WifiPinnedSectionListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * Created by Administrator on 2016/2/20.
 */
public class CityActivity extends BaseBarActivity implements LocationCitySearchQuery.SearchQueryCallBack{

    @InjectView(R.id.location_city_activity_slide_ssv)
    public SidebarView location_city_activity_slide_ssv;

    @InjectView(R.id.location_city_activity_listview)
    public WifiPinnedSectionListView location_city_activity_listview;

    @InjectView(R.id.location_city_activity_search_query_edt)
    public EditText  location_city_activity_search_query_edt;

    LocationCitySearchQuery activitySearchQuery;
    LocationCityListViewAdapter adapter;

    private static final String CITY_ERROR="未能查找到您所查找的城市！";

    @OnClick({R.id.location_city_activity_search_query_edt})
    protected void clickEvent(View view) {
        switch (view.getId()) {
            case R.id.location_city_activity_search_query_edt:
                location_city_activity_search_query_edt.setFocusable(true);
                location_city_activity_search_query_edt.requestFocus();
                break;

            default:
                break;
        }
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_city;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListener();
        fillDatas();
    }


    protected void initView() {
//        setTitleLeftIv(R.drawable.titile_left_iv_img);
        //右边侧栏 A-Z的字母
        location_city_activity_slide_ssv.setListView(location_city_activity_listview);
    }


    protected void setListener() {
        activitySearchQuery=new LocationCitySearchQuery(context,location_city_activity_search_query_edt);
        activitySearchQuery.setSearchListener(this);
    }

    protected void fillDatas() {
        adapter=new LocationCityListViewAdapter(context,activitySearchQuery.getCityEntityList());
        location_city_activity_listview.setAdapter(adapter);
    }



    /**
     * 数据搜索查询回调
     */
    @Override
    public void searchQueryCallBack(boolean isSearchMode,List<CityEntity> contactList) {
        if (adapter!=null) {
            //获得的数据进行刷新
            if (isSearchMode) {
//				showCommonToast("搜索模式", 0, Gravity.CENTER);
            }else {
//				showCommonToast("正常模式", 0, Gravity.CENTER);
            }
            if (contactList==null) {
                contactList=new ArrayList<CityEntity>();
            }
            if (contactList.isEmpty()) {
                CityEntity cityEntity=new CityEntity();
                cityEntity.setFullName(CITY_ERROR);
                contactList.add(cityEntity);
            }
            adapter.setData(contactList);
        }
    }



    @OnItemClick(R.id.location_city_activity_listview)
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        CityEntity cityEntity=(CityEntity) parent.getAdapter().getItem(position);
        if (cityEntity!=null) {
            if (cityEntity.type==CityEntity.TYPE_NORMAL||cityEntity.type==CityEntity.TYPE_CURRENT_CITY) {
                SPUtils.save2Sp(context, Constants.KEY_CITY,cityEntity.getFullName());
                startActivity(new Intent(context,MainActivity.class));
                finish();
            }
        }
    }

//    public void setResult(CityEntity cityEntity) {
//        if (cityEntity!=null) {
//            Intent intent = new Intent();
//            intent.putExtra(Constants.LOCATION_CITY_ENTITY_KEY, cityEntity);
//            setResult(Constants.LOCATION_CITY_ACTIVITY_RESULT_CODE, intent);
//        }
//    }

    /**
     * 用于点击其他地方缩回软键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                location_city_activity_slide_ssv.requestFocus();//随意一个控件获得焦点
                return true;
            }
        }
        return false;
    }
}
