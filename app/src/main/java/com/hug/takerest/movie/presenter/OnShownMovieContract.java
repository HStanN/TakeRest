package com.hug.takerest.movie.presenter;

import com.hug.takerest.base.BasePresenter;
import com.hug.takerest.base.BaseView;
import com.hug.takerest.movie.model.Movie;

import java.util.List;

/**
 * Created by HStan on 2017/3/9.
 *
 */

public class OnShownMovieContract {

    public interface Presenter extends BasePresenter {
        void refresh();
    }

    public interface View extends BaseView<Presenter> {
        void onSuccess(List<Movie> list);
        void showProgress();
        void hideProgress();
    }
}
