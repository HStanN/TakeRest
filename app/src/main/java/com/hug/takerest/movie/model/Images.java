package com.hug.takerest.movie.model;

/**
 * Created by HStan on 2017/3/15.
 */
public class Images {
    /**
     * small : http://img7.doubanio.com/view/movie_poster_cover/ipst/public/p2431980130.jpg
     * large : http://img7.doubanio.com/view/movie_poster_cover/lpst/public/p2431980130.jpg
     * medium : http://img7.doubanio.com/view/movie_poster_cover/spst/public/p2431980130.jpg
     */

    private String small;
    private String large;
    private String medium;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}
