package com.example.administrator.LookAndLost.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.LookAndLost.BuildConfig;
import com.example.administrator.LookAndLost.R;
import com.example.administrator.LookAndLost.fragment.AboutFragment;
import com.example.administrator.LookAndLost.fragment.MainFragment;
import com.example.administrator.LookAndLost.utils.BitmapUtils;
import com.example.administrator.LookAndLost.utils.ShareUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateConfig;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseBarActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TRANSITION="transition_img";

    @InjectView(R.id.nav_view)
    public NavigationView navNv;

    @InjectView(R.id.drawer_layout)
    public DrawerLayout drawerLayout;

    private Fragment lookAndLostFragment;
    private Fragment aboutFragment;

    @InjectView(R.id.main_fl)
    public FrameLayout mainFl;

    @InjectView(R.id.main_fab)
    public FloatingActionButton mainFab;
    @OnClick(R.id.main_fab)
    public void fabOnClick(View view){
        MobclickAgent.onEvent(context,"onClic_main_fab");
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeScaleUpAnimation(view,view.getWidth()/2,view.getHeight()/2,0, 0);
        Intent intent=new Intent(context, LookAndLostReleaseActivity.class);
        try {
            ActivityCompat.startActivity(this, intent,
                    options.toBundle());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navNv.setNavigationItemSelectedListener(this);

        lookAndLostFragment= MainFragment.newInstance();
        aboutFragment=AboutFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.main_fl,lookAndLostFragment,lookAndLostFragment.getClass().getSimpleName());
        fragmentTransaction.add(R.id.main_fl,aboutFragment,aboutFragment.getClass().getSimpleName());
        fragmentTransaction.hide(aboutFragment);
        fragmentTransaction.commitAllowingStateLoss();
        UpdateConfig.setDebug(BuildConfig.DEBUG);
        updataApp();
    }


    /**
     * UMeng自动更新
     */
    private void updataApp(){
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                UmengUpdateAgent.update(context);
            }

        },10000);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ExitApp();
//            super.onBackPressed();
        }
    }
    private long exitTime = 0;
    public void ExitApp()
    {
        if ((System.currentTimeMillis() - exitTime) > 2000)
        {
            Toast.makeText(context, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else
        {
            finish();
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
        if (id==R.id.action_sponsor){
            MobclickAgent.onEvent(context,"onClickSponsor");
            ImageView imageView=new ImageView(context);
            imageView.setImageResource(R.drawable.zhifubao);
            new AlertDialog.Builder(context).setView(imageView)
                    .setCancelable(true)
                    .setMessage("如果您觉得该软件对您有帮助，你可以试着赞助我们。我们感谢您的支持，并且您将会出现在下个版本的某名单中。")
                    .setNeutralButton("保存图片到相册", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.zhifubao);
                            Uri uri= BitmapUtils.saveImageToGallery(context,bitmap);
                            String str="保存图片失败";
                            if (uri!=null){
                                str="已保存到xunwuqishi/img文件夹下";
                            }
//                            dialog.cancel();
                            Snackbar.make(mainFl,str,Snackbar.LENGTH_LONG).show();
//                            Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            getSupportFragmentManager().beginTransaction().hide(aboutFragment).show(lookAndLostFragment).commitAllowingStateLoss();
        }  else if (id == R.id.nav_about) {
            getSupportFragmentManager().beginTransaction().hide(lookAndLostFragment).show(aboutFragment).commitAllowingStateLoss();
        } else if (id == R.id.nav_share) {
            ShareUtils.share(context);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
