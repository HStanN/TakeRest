package com.hug.takerest.shots.presenter;

import com.hug.takerest.GoApp;
import com.hug.takerest.http.RetrofitManager;
import com.hug.takerest.shots.model.User;

import io.reactivex.disposables.Disposable;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserPresenter implements UserContract.Presenter {

    private UserContract.View mView;
    private RetrofitManager retrofit;

    private Disposable disposable;

    public UserPresenter(UserContract.View view){
        this.mView = view;
        retrofit = GoApp.retrofit;
    }
    @Override
    public void start(int id) {
        mView.showProgress();
        disposable = retrofit.getUser(id, new RetrofitManager.OnRetrofitCallback<User>() {
            @Override
            public void onResponse(User response) {
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
    public void start() {

    }

    @Override
    public void dispose() {
        if (disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
