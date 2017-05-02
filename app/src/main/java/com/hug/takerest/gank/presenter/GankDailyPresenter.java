package com.hug.takerest.gank.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.gank.model.GankDaily;
import com.hug.takerest.http.RetrofitManager;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/3/29.
 */

public class GankDailyPresenter implements GankDailyContract.Presenter{
    private GankDailyContract.View mView;

    private RetrofitManager retrofit;

    private Disposable disposable;

    public GankDailyPresenter(GankDailyContract.View view){
        this.mView = view;
        retrofit = GoApp.retrofit;
        mView.setPresenter(this);
    }

    @Override
    public void start(int year, int month, int day) {
        mView.showProgress();
        disposable = retrofit.getGankDaily(year, month, day, new RetrofitManager.OnRetrofitCallback<GankDaily>() {
            @Override
            public void onResponse(GankDaily response) {
                mView.hideProgress();
                mView.onSuccess(response);
            }

            @Override
            public void onFailure(String t) {
                mView.onError(t);
            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
