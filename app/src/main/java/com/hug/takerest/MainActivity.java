package com.hug.takerest;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hug.takerest.base.BaseActivity;
import com.hug.takerest.gank.ui.DailyFragment;
import com.hug.takerest.gank.ui.MeiziFragment;
import com.hug.takerest.movie.ui.OnShownMovieFragment;
import com.hug.takerest.shots.ui.ShotsFragment;
import com.hug.takerest.util.FragmentUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fragment_container)
    FrameLayout container;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private Fragment mCurrentFragment;
    private OnShownMovieFragment movieFragment;
    private DailyFragment dailyFragment;
    private MeiziFragment meiziFragment;
    private ShotsFragment shotsFragment;
    private Toolbar mToolbar;
    private MenuItem mCalendar;
    private String[] titles = {"正在热映","每日干货","养眼妹纸","dribbble"};
    @Override
    protected void setView() {
        super.setView();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        initToolbar();
        mToolbar = getToolbar();
        setBackEnable(false);
    }

    @Override
    protected void bind() {
        mBottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId){
                    case R.id.tab_movie:
                        mToolbar.setTitle(titles[0]);
                        showFragment(movieFragment,0);
                        showHideCalendarMenu(false);
                    break;
                    case R.id.tab_developer:
                        mToolbar.setTitle(titles[1]);
                        showFragment(dailyFragment,1);
                        showHideCalendarMenu(true);
                        break;
                    case R.id.tab_girl:
                        mToolbar.setTitle(titles[2]);
                        showFragment(meiziFragment,2);
                        showHideCalendarMenu(false);
                        break;
                    default:
                        mToolbar.setTitle(titles[3]);
                        showFragment(shotsFragment,3);
                        showHideCalendarMenu(false);
                        break;
                }
            }
        });
        mBottomBar.selectTabAtPosition(0);
    }

    private void showFragment(Fragment fragment, int position){
        if (fragment == null){
            switch (position){
                case 0:
                    movieFragment = new OnShownMovieFragment();
                    fragment = movieFragment;
                break;
                case 1:
                    dailyFragment = new DailyFragment();
                    fragment = dailyFragment;
                    break;
                case 2:
                    meiziFragment = new MeiziFragment();
                    fragment = meiziFragment;
                    break;
                case 3:
                    shotsFragment = new ShotsFragment();
                    fragment = shotsFragment;
                    break;
                default:
                    break;
            }
            if (mCurrentFragment != null){
                FragmentUtils.hideFragment(mCurrentFragment);
            }
            FragmentUtils.addFragment(getSupportFragmentManager(),fragment, R.id.fragment_container,false);
            mCurrentFragment = fragment;
        }else {
            FragmentUtils.hideShowFragment(mCurrentFragment,fragment);
            mCurrentFragment = fragment;
        }
    }

    private void showHideCalendarMenu(boolean b) {
        if (mCalendar != null){
            mCalendar.setVisible(b);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about:
                Intent intent = new Intent(this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_share_app:
                Intent action_share = new Intent(Intent.ACTION_SEND);
                action_share.setType("text/plain");
                action_share.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_app));
                action_share.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text));
                action_share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(action_share, getString(R.string.share_app)));
                break;
            case R.id.action_calendar:
                //TODO:打开日历
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mCalendar = menu.findItem(R.id.action_calendar);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {
        if (isExit == false) {
            isExit = true;
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
            GoApp.getInstance().exit();
        }
    }
}
