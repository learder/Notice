package com.example.administrator.LookAndLost.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.LookAndLost.AnimHelp;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.entity.LookAndLostEntity;
import com.example.administrator.LookAndLost.utils.Constants;
import com.example.administrator.LookAndLost.utils.ShareUtils;
import com.example.administrator.LookAndLost.utils.TimeUtils;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/2/19.
 */
public class LookAndLostDetailActivity extends BaseBarActivity {

    @InjectView(R.id.ctl)
    public CollapsingToolbarLayout collapsingToolbarLayout;

    @InjectView(R.id.detail_img_iv)
    ImageView detailImgIv;
    @InjectView(R.id.detail_title_tv)
    TextView detailTitleTv;
    @InjectView(R.id.detail_type_tv)
    TextView detailTypeTv;
    @InjectView(R.id.detail_content_tv)
    TextView detailContentTv;
    @InjectView(R.id.detail_reward_tv)
    TextView detailRewardTv;
    @InjectView(R.id.detail_name_tv)
    TextView detailNameTv;
    @InjectView(R.id.detail_contact_tv)
    TextView detailContactTv;
    @InjectView(R.id.detail_notes_tv)
    TextView detailNotesTv;
    @InjectView(R.id.detail_timeout_tv)
    TextView detailTimeoutTv;

    @InjectView(R.id.detail_scroll_nsv)
    NestedScrollView detail_scroll_nsv;

    LookAndLostEntity entity;

    @OnClick(R.id.detail_img_iv)
    public void myClick(){
        AnimHelp.alphaOutAnim(detailImgIv);
        AnimHelp.alphaOutAnim(detail_scroll_nsv);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewCompat.setTransitionName(detailImgIv, MainActivity.TRANSITION);
        entity=getIntent().getParcelableExtra(Constants.KEY_LOOK_AND_LOST_ENTITY);
        setDetailInfo(entity);
        setTitle("");
        AnimHelp.bottonInAnim(detail_scroll_nsv);
//        AnimHelp.alphaOutAnim(detail_scroll_nsv);


    }

    private void setDetailInfo(LookAndLostEntity entity){
        if (entity!=null){
            detailTitleTv.setText(entity.getTitle());
            detailTypeTv.setText(entity.getEventType());
            detailContentTv.setText(entity.getContent());
            detailRewardTv.setText(entity.getReward());
            detailNameTv.setText(entity.getUserName());
            detailContactTv.setText(entity.getContact());
            detailNotesTv.setText(entity.getNotes());
            detailTimeoutTv.setText(TimeUtils.getTimeFromLong(entity.getTimeout()));
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.layout_look_and_lost_detail;
    }

    @Override
    protected void setBarTitle(String str) {
        if (collapsingToolbarLayout == null) {
            super.setBarTitle(str);
        } else {
            collapsingToolbarLayout.setTitle(str);
        }

    }

    @Override
    public void onBackPressed() {
        AnimHelp.alphaOutAnim(detailImgIv);
//        detailImgIv.setAlpha(0);
        AnimHelp.alphaOutAnim(detail_scroll_nsv);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id==R.id.release_share){
            if (entity!=null){
//                String str=entity.getImgs().get(0).getImg();
                ShareUtils.share(context,entity.getTitle(),getShareString(entity));
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private String getShareString(LookAndLostEntity entity){
        StringBuffer sb=new StringBuffer();
        sb.append("我在").append(entity.getAddress()).append(entity.getEventType()).append(entity.getContent());

        return sb.toString();
    }
}
