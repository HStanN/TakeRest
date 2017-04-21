package com.hug.takerest.movie.model;

import java.util.List;

/**
 * Created by HStan on 2017/3/7.
 */

public class MovieResponse{

    /**
     * title : 正在上映的电影-北京
     * count : 20
     * start : 0
     * total : 28
     * subjects : []
     */

    private String title;
    private int count;
    private int start;
    private int total;
    private List<Movie> subjects;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Movie> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Movie> subjects) {
        this.subjects = subjects;
    }
}
