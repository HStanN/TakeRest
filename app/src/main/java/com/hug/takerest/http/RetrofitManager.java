package com.hug.takerest.http;

import android.content.Context;

import com.beltaief.flowlayout.util.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.hug.takerest.R;
import com.hug.takerest.api.DoubanMovieAPI;
import com.hug.takerest.api.DribbbleAPI;
import com.hug.takerest.api.GankAPI;
import com.hug.takerest.constants.C;
import com.hug.takerest.gank.model.GankDaily;
import com.hug.takerest.gank.model.GankData;
import com.hug.takerest.movie.model.MovieBasic;
import com.hug.takerest.movie.model.MovieResponse;
import com.hug.takerest.shots.model.Comments;
import com.hug.takerest.shots.model.Project;
import com.hug.takerest.shots.model.Shot;
import com.hug.takerest.shots.model.User;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HStan on 2017/3/7.
 */

public class RetrofitManager {
    private static RetrofitManager retrofitManager;
    private DoubanMovieAPI movieAPI;
    private GankAPI gankAPI;
    private DribbbleAPI dribbbleAPI;
    private Context context;
    public static final int MOVIE_TYPE = 1000;
    public static final int GANK_TYPE = 1001;
    public static final int SHOT_TYPE = 1002;
    private static final long DEFAULT_TIMEOUT = 15;

    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build();

    abstract class TakeRestConsumer implements Consumer<Throwable> {
        public String error;

        @Override
        public void accept(Throwable e) throws Exception {
            if (e instanceof TimeoutException || e instanceof SocketTimeoutException
                    || e instanceof ConnectException) {
                error = context.getString(R.string.error_timeout);
            } else if (e instanceof JsonSyntaxException) {
                error = context.getString(R.string.error_json);
            } else if (e instanceof UnknownHostException) {
                if (!NetworkUtil.getConnectivityStatus(context)) {
                    error = context.getString(R.string.error_networknotwork);
                } else {
                    error = context.getString(R.string.error_servicenotwork);
                }
            } else {
                error = e.getMessage();
            }
        }
    }

    public interface OnRetrofitCallback<T> {
        void onResponse(T response);

        void onFailure(String e);
    }


    public static RetrofitManager newInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    /**
     * 初始化Retrofit,初始化Biz实例
     *
     * @param type 请求数据类型
     *             (MOVIE_TYPE=1000:电影
     *              GANK_TYPE =1001:gank
     *              SHOT_TYPE =1002:dribbble shot
     *             )
     */
    public void initRetrofit(Context context, int type) {
        this.context = context;

        switch (type) {
            case MOVIE_TYPE:
                Retrofit movie = new Retrofit.Builder()
                        .baseUrl(C.BASE_URL)
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                movieAPI = movie.create(DoubanMovieAPI.class);
                break;
            case GANK_TYPE:
                Retrofit gank = new Retrofit.Builder()
                        .baseUrl(C.GANK_URL)
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                gankAPI = gank.create(GankAPI.class);
                break;
            case SHOT_TYPE:
                Retrofit shot = new Retrofit.Builder()
                        .baseUrl(C.DRIBBBLE_URL)
                        .client(client)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                dribbbleAPI = shot.create(DribbbleAPI.class);
                break;
            default:
                break;
        }
    }

    /**
     * 获取正在上映的电影
     *
     * @param city  选择定位的城市
     */
    public Disposable getOnShow(final String city, final OnRetrofitCallback onRetrofitCallback) {
        return movieAPI.OnShow(city)
//                .flatMap() //可以用于拆分事件发送
//                .filter()  //可以用于过滤
//                .map()     //可以用于对数据某项进行修改
//                .toList()  //完成上述操作 重新整理成列表
                .subscribeOn(Schedulers.io())            //设置事件触发在io线程
                .observeOn(AndroidSchedulers.mainThread())//设置事件回调在主线程
                .subscribe(new Consumer<MovieResponse>() {
                    @Override
                    public void accept(MovieResponse movieResponse) throws Exception {
                        onRetrofitCallback.onResponse(movieResponse);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取电影信息
     *
     * @param id 电影id
     */
    public Disposable getMovieBasic(int id, final OnRetrofitCallback onRetrofitCallback) {
        return movieAPI.getMovieBasic(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<MovieBasic>() {
                    @Override
                    public void accept(MovieBasic movieBasic) throws Exception {
                        onRetrofitCallback.onResponse(movieBasic);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取gank每日推荐
     *
     * @param year  the year of current date
     * @param month the month of current date
     * @param day   the day of current date
     *
     */
    public Disposable getGankDaily(int year, int month, int day, final OnRetrofitCallback onRetrofitCallback) {
        return gankAPI.getDaily(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankDaily>() {
                    @Override
                    public void accept(GankDaily gankDaily) throws Exception {
                        onRetrofitCallback.onResponse(gankDaily);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 分类获取gank数据
     *
     * @param type  the type of data
     * @param count the count of response
     * @param page  the page of data
     */
    public Disposable getGankData(String type, int count, int page, final OnRetrofitCallback onRetrofitCallback) {
        return gankAPI.getGank(type, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GankData>() {
                    @Override
                    public void accept(GankData gankData) throws Exception {
                        onRetrofitCallback.onResponse(gankData);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取Dribbble shots数据
     */
    public Disposable getShots(final OnRetrofitCallback onRetrofitCallback) {
        return dribbbleAPI.getShots(C.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Shot>>() {
                    @Override
                    public void accept(List<Shot> shots) throws Exception {
                        onRetrofitCallback.onResponse(shots);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取单个shot数据
     *
     * @param id  The shots's id
     *
     */
    public Disposable getShot(int id,final OnRetrofitCallback onRetrofitCallback) {
        return dribbbleAPI.getShotById(id,C.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Shot>() {
                    @Override
                    public void accept(Shot shot) throws Exception {
                        onRetrofitCallback.onResponse(shot);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取shot评论list
     *
     * @param id shotid
     *
     * */
    public Disposable getComments(int id,final OnRetrofitCallback onRetrofitCallback){
        return dribbbleAPI.getComments(id,C.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Comments>>() {
                    @Override
                    public void accept(List<Comments> commentses) throws Exception {
                        onRetrofitCallback.onResponse(commentses);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取user信息
     *
     * @param id 用户id
     *
     * */
    public Disposable getUser(int id,final OnRetrofitCallback onRetrofitCallback){
        return dribbbleAPI.getUser(id,C.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        onRetrofitCallback.onResponse(user);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取user的shots信息
     * @param id   user id
     *
     * */
    public Disposable getUserShots(int id,final OnRetrofitCallback onRetrofitCallback){
        return dribbbleAPI.getUserShots(id,C.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Shot>>() {
                    @Override
                    public void accept(List<Shot> shots) throws Exception {
                        onRetrofitCallback.onResponse(shots);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

    /**
     * 获取user的projects信息
     *
     * @param id   user id
     *
     * */
    public Disposable getUserProjects(int id,final OnRetrofitCallback onRetrofitCallback){
        return dribbbleAPI.getUserProjects(id,C.TOKEN)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Project>>() {
                    @Override
                    public void accept(List<Project> projects) throws Exception {
                        onRetrofitCallback.onResponse(projects);
                    }
                }, new TakeRestConsumer() {
                    @Override
                    public void accept(Throwable e) throws Exception {
                        super.accept(e);
                        onRetrofitCallback.onFailure(error);
                    }
                });
    }

}
