package com.hug.takerest.base;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hug.takerest.GoApp;
import com.hug.takerest.R;
import com.hug.takerest.http.RetrofitManager;

/**
 * Created by HStan on 2017/3/2.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context HTTP_TAG = this;
    protected RetrofitManager retrofit;
    protected Toolbar mToolbar;

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 使statusbar透明
     *
     * */
    protected void transparentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrofit = GoApp.retrofit;
        GoApp.getInstance().addActivity(this);
        setView();
        initToolbar();
        init();
        bind();
    }

    protected void setView(){
    }

    protected void init(){

    }

    protected void bind(){

    }

    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    protected void setBackEnable(boolean b){
        if (b){
            if (mToolbar != null){
                mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
                mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onBackPressed();
                    }
                });
            }
        }
    }

    public Toolbar getToolbar(){
        return mToolbar;
    }

    public void setToolbarTitle(String title){
        if (mToolbar != null && title != null){
            setTitle(title);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
