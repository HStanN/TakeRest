package com.hug.takerest.movie.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.movie.model.MovieBasic;

/**
 * Created by HStan on 2017/3/16.
 */

public class MovieBasicContract {

    public interface View extends BaseView {
        void onSuccess(MovieBasic movieBasic);
        void showProgress();
        void hideProgress();
    }

    public interface Presenter extends BasePresenter {
        void refresh();
        void collection();
    }
}
