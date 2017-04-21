package com.hug.takerest.gank.model;

import java.util.List;

/**
 * Created by HStan on 2017/3/29.
 */

public class GankDaily extends GankResult{
    private List<String> category;
    private GankSortData results;

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public GankSortData getResults() {
        return results;
    }

    public void setResults(GankSortData results) {
        this.results = results;
    }
}
