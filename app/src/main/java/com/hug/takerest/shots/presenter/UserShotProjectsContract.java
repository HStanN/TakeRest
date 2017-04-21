package com.hug.takerest.shots.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.shots.model.Project;
import com.hug.takerest.shots.model.Shot;

import java.util.List;

/**
 * Created by HStan on 2017/4/14.
 */

public class UserShotProjectsContract {

    public interface View extends BaseView {
        void onShotSuccess(List<Shot> list);
        void onProjectSuccess(List<Project> list);
        void showProgress();
        void hideProgress();
        void showEmpty();
    }

    public interface Presenter extends BasePresenter {
        void getShots(int id);
        void getProjects(int id);
    }
}
