package com.hug.takerest.shots.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.model.Project;
import com.hug.takerest.shots.model.Shot;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserShotProjectsPresenter implements UserShotProjectsContract.Presenter{
    private UserShotProjectsContract.View mView;
    private RetrofitManager retrofit;

    private int id;

    private Disposable disposable;

    public UserShotProjectsPresenter(UserShotProjectsContract.View view){
        this.mView = view;
        retrofit = GoApp.retrofit;
    }

    @Override
    public void getShots(int id) {
        mView.showProgress();
        retrofit.getUserShots(id, new RetrofitManager.OnRetrofitCallback<List<Shot>>() {
            @Override
            public void onResponse(List<Shot> response) {
                mView.hideProgress();
                if (response.size() == 0){
                    mView.showEmpty();
                }else{
                    mView.onShotSuccess(response);
                }
            }

            @Override
            public void onFailure(String e) {
                mView.onError(e);
            }
        });
    }

    @Override
    public void getProjects(int id) {
        mView.showProgress();
        disposable = retrofit.getUserProjects(id, new RetrofitManager.OnRetrofitCallback<List<Project>>() {
            @Override
            public void onResponse(List<Project> response) {
                mView.hideProgress();
                if (response.size() == 0){
                    mView.showEmpty();
                }else{
                    mView.onProjectSuccess(response);
                }
            }

            @Override
            public void onFailure(String e) {
                mView.onError(e);
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
