package com.hug.takerest.shots.model;

/**
 * Created by HStan on 2017/4/14.
 */

public class Project {

    /**
     * id : 3
     * name : Web Standards Sherpa
     * description : I did visual design and art direction for this project, working with the <a href="http://webstandards.org">Web Standards Project</a> and Microsoft.
     * shots_count : 4
     * created_at : 2011-04-14T03:43:47Z
     * updated_at : 2012-04-04T22:39:53Z
     */

    private int id;
    private String name;
    private String description;
    private int shots_count;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getShots_count() {
        return shots_count;
    }

    public void setShots_count(int shots_count) {
        this.shots_count = shots_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
