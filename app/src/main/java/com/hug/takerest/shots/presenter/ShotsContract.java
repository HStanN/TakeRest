package com.hug.takerest.shots.presenter;


import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.shots.model.Shot;

import java.util.List;

/**
 * Created by HStan on 2017/4/6.
 */

public class ShotsContract {

    public interface View extends BaseView {
        void onSuccess(List<Shot> list);
        void showProgress();
        void hideProgress();
    }

    public interface Presenter extends BasePresenter {
        void refresh();
    }
}
