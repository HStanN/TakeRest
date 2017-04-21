package com.hug.takerest.gank.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HStan on 2017/3/29.
 */

public class GankSortData {
    @SerializedName("iOS")
    private List<Gank> ios;
    @SerializedName("Android")
    private List<Gank> android;
    @SerializedName("瞎推荐")
    private List<Gank> recommend;
    @SerializedName("福利")
    private List<Gank> meizi;
    @SerializedName("休息视频")
    private List<Gank> video;
    @SerializedName("拓展资源")
    private List<Gank> extraResource;
    @SerializedName("前端")
    private List<Gank> html;
    @SerializedName("App")
    private List<Gank> app;

    public List<Gank> getIos() {
        return ios;
    }

    public void setIos(List<Gank> ios) {
        this.ios = ios;
    }

    public List<Gank> getAndroid() {
        return android;
    }

    public void setAndroid(List<Gank> android) {
        this.android = android;
    }

    public List<Gank> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<Gank> recommend) {
        this.recommend = recommend;
    }

    public List<Gank> getMeizi() {
        return meizi;
    }

    public void setMeizi(List<Gank> meizi) {
        this.meizi = meizi;
    }

    public List<Gank> getVideo() {
        return video;
    }

    public void setVideo(List<Gank> video) {
        this.video = video;
    }

    public List<Gank> getExtraResource() {
        return extraResource;
    }

    public void setExtraResource(List<Gank> extraResource) {
        this.extraResource = extraResource;
    }

    public List<Gank> getHtml() {
        return html;
    }

    public void setHtml(List<Gank> html) {
        this.html = html;
    }

    public List<Gank> getApp() {
        return app;
    }

    public void setApp(List<Gank> app) {
        this.app = app;
    }
}
