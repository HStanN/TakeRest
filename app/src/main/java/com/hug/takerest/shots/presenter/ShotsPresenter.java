package com.hug.takerest.shots.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.model.Shot;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/4/6.
 */

public class ShotsPresenter implements ShotsContract.Presenter {
    private ShotsContract.View mView;

    private RetrofitManager retrofit;

    private Disposable disposable;

    public ShotsPresenter(ShotsContract.View view){
        this.mView = view;
        retrofit = GoApp.retrofit;
        mView.setPresenter(this);
    }

    @Override
    public void refresh() {
        start();
    }

    @Override
    public void start() {
        mView.showProgress();
        disposable = retrofit.getShots(new RetrofitManager.OnRetrofitCallback<List<Shot>>() {
            @Override
            public void onResponse(List<Shot> response) {
                mView.hideProgress();
                mView.onSuccess(response);
            }

            @Override
            public void onFailure(String e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
