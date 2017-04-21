package com.hug.takerest.shots.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.shots.model.Shot;

/**
 * Created by HStan on 2017/4/18.
 */

public class ShotContract {

    public interface View extends BaseView {
        void onSuccess(Shot shot);
    }

    public interface Presenter extends BasePresenter {

    }
}
