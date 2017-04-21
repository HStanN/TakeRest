package com.hug.takerest.shots.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.shots.model.User;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserContract {

    public interface View extends BaseView{
        void onSuccess(User user);
        void showProgress();
        void hideProgress();
    }

    public interface Presenter extends BasePresenter {
        void start(int id);
    }
}
