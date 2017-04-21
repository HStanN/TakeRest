package com.hug.takerest.api;

import com.hug.takerest.movie.model.Movie;
import com.hug.takerest.movie.model.MovieBasic;
import com.hug.takerest.movie.model.MovieResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HStan on 2017/3/6.
 */

public interface DoubanMovieAPI {
    //   http://api.douban.com
    /**
     * 正在热映的电影
     * @param city
     *
     * */
    @GET("/v2/movie/in_theaters")
    Observable<MovieResponse> OnShow(@Query("city") String city);

    /**
     * 即将上映的电影
     * @param start 分页码
     * @param count 单页数量
     *
     * */
    @GET("/v2/movie/coming_soon")
    Observable<Movie> ComingSoon(@Query("start") int start,
                           @Query("count") int count);

    /**
     * 电影条目信息
     * @param id 电影id
     *
     * */
    @GET ("/v2/movie/subject/{id}")
    Observable<MovieBasic> getMovieBasic(@Path("id") int id);
}
