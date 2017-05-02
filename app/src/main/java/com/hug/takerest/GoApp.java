package com.hug.takerest;

import android.app.Activity;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hug.takerest.http.RetrofitManager;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by HStan on 2017/3/2.
 */

public class GoApp extends Application {
    public static RetrofitManager retrofit;
    protected String TAG = "DBMovieLog";
    private static GoApp instance;
    private List<Activity> mList = new LinkedList<Activity>();
    @Override
    public void onCreate() {
        super.onCreate();
        retrofit = RetrofitManager.newInstance();
        Bugly.init(getApplicationContext(), getString(R.string.bugly_app_id), false);
        Fresco.initialize(this);
        Logger.init(TAG)                 // default PRETTYLOGGER or use just init()
                .methodCount(3)                 // default 2
                .hideThreadInfo()               // default shown
                .logLevel(LogLevel.FULL)        // default LogLevel.FULL
                .methodOffset(2);
    }

    public static GoApp getInstance() {
        if (null == instance) {
            instance = new GoApp();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        mList.add(activity);
    }

    public void exit() {
        try {
            for (Activity activity : mList) {
                if (activity != null)
                    activity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
