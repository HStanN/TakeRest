package com.hug.takerest.shots.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.model.Shot;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/4/18.
 */

public class ShotPresenter implements ShotContract.Presenter {

    private ShotContract.View mView;

    private RetrofitManager retrofit;

    private int mId;

    private Disposable disposable;

    public ShotPresenter(int id,ShotContract.View view){
        this.mView = view;
        this.mId = id;
        retrofit = GoApp.retrofit;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        disposable = retrofit.getShot(mId, new RetrofitManager.OnRetrofitCallback<Shot>() {
            @Override
            public void onResponse(Shot response) {
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
