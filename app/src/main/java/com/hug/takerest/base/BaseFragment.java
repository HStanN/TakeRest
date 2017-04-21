package com.hug.takerest.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;

/**
 * Created by HStan on 2017/3/28.
 */

public class BaseFragment extends Fragment {
    protected Context HTTP_TAG = getContext();
    protected RetrofitManager retrofit = GoApp.retrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseArgs();
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    public void parseArgs(){

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
