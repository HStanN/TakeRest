package com.hug.takerest.movie.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.movie.model.MovieBasic;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/3/16.
 */

public class MovieBasicPresenter implements MovieBasicContract.Presenter {

    private RetrofitManager retrofit;

    private MovieBasicContract.View mView;

    private int id;

    private Disposable disposable;

    public MovieBasicPresenter(MovieBasicContract.View view,int id){
        this.mView = view;
        this.id = id;
        retrofit = GoApp.retrofit;
        mView.setPresenter(this);
    }

    @Override
    public void refresh() {
        start();
    }

    @Override
    public void collection() {

    }

    @Override
    public void start() {
        mView.showProgress();
        disposable = retrofit.getMovieBasic(id,new RetrofitManager.OnRetrofitCallback<MovieBasic>() {
            @Override
            public void onResponse(MovieBasic response) {
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
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
