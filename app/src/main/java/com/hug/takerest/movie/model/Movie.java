package com.hug.takerest.movie.model;

import java.util.List;

/**
 * Created by Hg on 2017/3/7.
 */

public class Movie {

    /**
     * rating : {"max":10,"average":8.5,"stars":"45","min":0}
     * genres : ["剧情","动作","科幻"]
     * title : 金刚狼3：殊死一战
     * casts : [{"alt":"https://movie.douban.com/celebrity/1010508/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/22036.jpg","large":"http://img3.doubanio.com/img/celebrity/large/22036.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/22036.jpg"},"name":"休·杰克曼","id":"1010508"},{"alt":"https://movie.douban.com/celebrity/1010540/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/50451.jpg","large":"http://img7.doubanio.com/img/celebrity/large/50451.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/50451.jpg"},"name":"帕特里克·斯图尔特","id":"1010540"},{"alt":"https://movie.douban.com/celebrity/1363476/","avatars":{"small":"http://img3.doubanio.com/img/celebrity/small/CCMOQr5bsGAcel_avatar_uploaded1476526279.97.jpg","large":"http://img3.doubanio.com/img/celebrity/large/CCMOQr5bsGAcel_avatar_uploaded1476526279.97.jpg","medium":"http://img3.doubanio.com/img/celebrity/medium/CCMOQr5bsGAcel_avatar_uploaded1476526279.97.jpg"},"name":"达芙妮·基恩","id":"1363476"}]
     * collect_count : 87749
     * original_title : Logan
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1036395/","avatars":{"small":"http://img7.doubanio.com/img/celebrity/small/11282.jpg","large":"http://img7.doubanio.com/img/celebrity/large/11282.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/11282.jpg"},"name":"詹姆斯·曼高德","id":"1036395"}]
     * year : 2017
     * images : {"small":"http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p2431980130.jpg","large":"http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2431980130.jpg","medium":"http://img7.doubanio.com/view/movie_poster_cover/spst/public/p2431980130.jpg"}
     * alt : https://movie.douban.com/subject/25765735/
     * id : 25765735
     */

    private Rating rating;
    private String title;
    private int collect_count;
    private String original_title;
    private String subtype;
    private String year;
    private Images images;
    private String alt;
    private int id;
    private List<String> genres;
    private List<Director> casts;
    private List<Director> directors;

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Director> getCasts() {
        return casts;
    }

    public void setCasts(List<Director> casts) {
        this.casts = casts;
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Director> directors) {
        this.directors = directors;
    }
}
