package com.hug.takerest.gank.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.constants.C;
import com.hug.takerest.gank.model.GankData;
import com.hug.takerest.http.RetrofitManager;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/3/30.
 */

public class GankPresenter implements GankContract.Presenter {
    private GankContract.View mView;

    private RetrofitManager retrofit;

    private String mType;

    private Disposable disposable;

    public GankPresenter(GankContract.View view){
        this.mView = view;
        retrofit = GoApp.retrofit;
        mView.setPresenter(this);
    }

    @Override
    public void refresh() {
        start(mType, C.COUNT,1);
    }

    @Override
    public void start(String type, int count, int page) {
        this.mType = type;
        mView.onShowProgress();
        retrofit.getGankData(type, C.COUNT, page, new RetrofitManager.OnRetrofitCallback<GankData>() {
            @Override
            public void onResponse(GankData response) {
                mView.hideProgress();
                mView.onSuccess(response);
            }

            @Override
            public void onFailure(String t) {
                mView.hideProgress();
                mView.onError(t);
            }
        });
    }

    @Override
    public void add(int page) {
        mView.onShowProgress();
        disposable = retrofit.getGankData(mType, C.COUNT, page, new RetrofitManager.OnRetrofitCallback<GankData>() {
            @Override
            public void onResponse(GankData response) {
                mView.hideProgress();
                mView.add(response);
            }

            @Override
            public void onFailure(String t) {
                mView.hideProgress();
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
