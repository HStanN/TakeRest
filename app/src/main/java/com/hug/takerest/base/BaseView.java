package com.hug.takerest.base;

/**
 * Created by HStan on 2017/3/9.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    void onError(String t);
}
