package com.hug.takerest.movie.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.movie.model.Movie;
import com.hug.takerest.movie.model.MovieResponse;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/3/9.
 */

public class OnShownMoviePresenter implements OnShownMovieContract.Presenter {

    private RetrofitManager retrofit;

    private OnShownMovieContract.View mView;

    private String city;

    private Disposable disposable;

    public OnShownMoviePresenter(OnShownMovieContract.View view,String city){
        this.mView = view;
        this.city = city;
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
        disposable = retrofit.getOnShow(city,new RetrofitManager.OnRetrofitCallback<MovieResponse>() {
            @Override
            public void onResponse(MovieResponse response) {
                List<Movie> list = response.getSubjects();
                mView.hideProgress();
                mView.onSuccess(list);
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
