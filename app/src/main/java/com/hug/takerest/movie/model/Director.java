package com.hug.takerest.movie.model;

/**
 * Created by HStan on 2017/3/15.
 */

public class Director {
    /**
     * alt : https://movie.douban.com/celebrity/1036395/
     * avatars : {"small":"http://img7.doubanio.com/img/celebrity/small/11282.jpg","large":"http://img7.doubanio.com/img/celebrity/large/11282.jpg","medium":"http://img7.doubanio.com/img/celebrity/medium/11282.jpg"}
     * name : 詹姆斯·曼高德
     * id : 1036395
     */

    private String alt;
    private Images avatars;
    private String name;
    private String id;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public Images getAvatars() {
        return avatars;
    }

    public void setAvatars(Images avatars) {
        this.avatars = avatars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
