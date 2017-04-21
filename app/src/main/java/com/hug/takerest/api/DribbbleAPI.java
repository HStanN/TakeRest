package com.hug.takerest.api;

import com.hug.takerest.shots.model.Comments;
import com.hug.takerest.shots.model.Project;
import com.hug.takerest.shots.model.Shot;
import com.hug.takerest.shots.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by HStan on 2017/4/6.
 */

public interface DribbbleAPI {

    @GET ("/v1/shots")
    Observable<List<Shot>> getShots(@Query("access_token") String access_token);

    @GET("/v1/shots/{id}")
    Observable<Shot> getShotById(@Path("id") int id,
                                 @Query("access_token") String access_token);

    @GET("/v1/shots/{id}/comments")
    Observable <List<Comments>> getComments(@Path("id") int id,
                                            @Query("access_token") String access_token);

    @GET("/v1/users/{user}")
    Observable<User> getUser(@Path("user") int id,
                             @Query("access_token") String access_token);

    @GET("/v1/users/{user}/shots")
    Observable<List<Shot>> getUserShots(@Path("user") int id,
                                        @Query("access_token") String access_token);

    @GET("/v1/users/{user}/projects")
    Observable<List<Project>> getUserProjects(@Path("user") int id,
                                              @Query("access_token") String access_token);
}
