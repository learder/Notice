package com.example.administrator.LookAndLost.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.administrator.LookAndLost.R;

import butterknife.InjectView;


/**
 * Created by 颜厥共 on 2016/2/19.
 * email:644613693@qq.com
 */
public class LookAndLostReleaseActivity extends BaseBarActivity {
//    @ViewInject(R.id.release_fab)
//    private FloatingActionButton release_fab;
//
//    @Event(R.id.release_fab)
//    private void onRelease(View view){
//        Snackbar.make(view,"发布",Snackbar.LENGTH_SHORT).show();
//    }

    @InjectView(R.id.release_add_img_iv)
    ImageView release_add_img_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBarTitle("发布寻物启事");
//        ViewCompat.setTransitionName(release_add_img_iv,MainActivity.TRANSITION);

    }



    @Override
    protected int getContentView() {
        return R.layout.activity_look_and_lost_release;
    }




}
