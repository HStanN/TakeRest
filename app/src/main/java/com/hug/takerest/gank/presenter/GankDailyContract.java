package com.hug.takerest.gank.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.gank.model.GankDaily;

/**
 * Created by HStan on 2017/3/29.
 */

public class GankDailyContract {
    public interface View extends BaseView {
        void onSuccess(GankDaily gankDaily);
        void showProgress();
        void hideProgress();
    }

    public interface Presenter extends BasePresenter {
        void start(int year,int month,int day);
    }
}
