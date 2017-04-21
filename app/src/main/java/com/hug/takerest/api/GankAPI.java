package com.hug.takerest.api;

import com.hug.takerest.gank.model.GankDaily;
import com.hug.takerest.gank.model.GankData;
import com.hug.takerest.gank.model.ImageInfo;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by HStan on 2017/3/29.
 */

public interface GankAPI {
    /**
     * 获取Gank每日推荐
     *
     * @param year  年
     * @param month 月
     * @param day   日
     */
    @GET("day/{year}/{month}/{day}")
    Observable<GankDaily> getDaily(
            @Path("year") int year,
            @Path("month") int month,
            @Path("day") int day);

    /**
     * 获取Gank分类数据
     *
     * @param type  类型
     * @param count 每次返回数据size
     * @param page  分页码
     */
    @GET("data/{type}/{count}/{page}")
    Observable<GankData> getGank(
            @Path("type") String type,
            @Path("count") int count,
            @Path("page") int page);

    /**
     * 获取图片信息
     *
     * */
    @Headers("Accept: application/json")
    @GET("{url}?imageInfo")
    Call<ImageInfo> getImageInfo(@Path("url") String url);
}
