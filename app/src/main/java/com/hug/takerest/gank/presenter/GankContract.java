package com.hug.takerest.gank.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.gank.model.GankData;

/**
 * Created by HStan on 2017/3/30.
 */

public class GankContract {

    public interface View extends BaseView {
        void onSuccess(GankData gankData);
        void add(GankData gankData);
        void onShowProgress();
        void hideProgress();
    }

    public interface Presenter extends BasePresenter {
        void refresh();
        void start(String type,int count, int page);
        void add(int page);
    }
}
