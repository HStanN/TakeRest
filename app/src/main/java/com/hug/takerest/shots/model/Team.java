package com.hug.takerest.shots.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HStan on 2017/4/11.
 */

public class Team implements Parcelable {

    /**
     * id : 39
     * name : Dribbble
     * username : dribbble
     * html_url : https://dribbble.com/dribbble
     * avatar_url : https://d13yacurqjgara.cloudfront.net/users/39/avatars/normal/apple-flat-precomposed.png?1388527574
     * bio : Show and tell for designers. This is Dribbble on Dribbble.
     * location : Salem, MA
     * links : {"web":"http://dribbble.com","twitter":"https://twitter.com/dribbble"}
     * buckets_count : 1
     * comments_received_count : 2037
     * followers_count : 25011
     * followings_count : 6120
     * likes_count : 44
     * likes_received_count : 15811
     * members_count : 7
     * projects_count : 4
     * rebounds_received_count : 416
     * shots_count : 91
     * can_upload_shot : true
     * type : Team
     * pro : false
     * buckets_url : https://dribbble.com/v1/users/39/buckets
     * followers_url : https://dribbble.com/v1/users/39/followers
     * following_url : https://dribbble.com/v1/users/39/following
     * likes_url : https://dribbble.com/v1/users/39/likes
     * members_url : https://dribbble.com/v1/teams/39/members
     * shots_url : https://dribbble.com/v1/users/39/shots
     * team_shots_url : https://dribbble.com/v1/users/39/teams
     * created_at : 2009-08-18T18:34:31Z
     * updated_at : 2014-02-14T22:32:11Z
     */

    private int id;
    private String name;
    private String username;
    private String html_url;
    private String avatar_url;
    private String bio;
    private String location;
    private Links links;
    private int buckets_count;
    private int comments_received_count;
    private int followers_count;
    private int followings_count;
    private int likes_count;
    private int likes_received_count;
    private int members_count;
    private int projects_count;
    private int rebounds_received_count;
    private int shots_count;
    private boolean can_upload_shot;
    private String type;
    private boolean pro;
    private String buckets_url;
    private String followers_url;
    private String following_url;
    private String likes_url;
    private String members_url;
    private String shots_url;
    private String team_shots_url;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
    }

    public int getComments_received_count() {
        return comments_received_count;
    }

    public void setComments_received_count(int comments_received_count) {
        this.comments_received_count = comments_received_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollowings_count() {
        return followings_count;
    }

    public void setFollowings_count(int followings_count) {
        this.followings_count = followings_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getLikes_received_count() {
        return likes_received_count;
    }

    public void setLikes_received_count(int likes_received_count) {
        this.likes_received_count = likes_received_count;
    }

    public int getMembers_count() {
        return members_count;
    }

    public void setMembers_count(int members_count) {
        this.members_count = members_count;
    }

    public int getProjects_count() {
        return projects_count;
    }

    public void setProjects_count(int projects_count) {
        this.projects_count = projects_count;
    }

    public int getRebounds_received_count() {
        return rebounds_received_count;
    }

    public void setRebounds_received_count(int rebounds_received_count) {
        this.rebounds_received_count = rebounds_received_count;
    }

    public int getShots_count() {
        return shots_count;
    }

    public void setShots_count(int shots_count) {
        this.shots_count = shots_count;
    }

    public boolean isCan_upload_shot() {
        return can_upload_shot;
    }

    public void setCan_upload_shot(boolean can_upload_shot) {
        this.can_upload_shot = can_upload_shot;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public String getMembers_url() {
        return members_url;
    }

    public void setMembers_url(String members_url) {
        this.members_url = members_url;
    }

    public String getShots_url() {
        return shots_url;
    }

    public void setShots_url(String shots_url) {
        this.shots_url = shots_url;
    }

    public String getTeam_shots_url() {
        return team_shots_url;
    }

    public void setTeam_shots_url(String team_shots_url) {
        this.team_shots_url = team_shots_url;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.html_url);
        dest.writeString(this.avatar_url);
        dest.writeString(this.bio);
        dest.writeString(this.location);
        dest.writeParcelable(this.links, flags);
        dest.writeInt(this.buckets_count);
        dest.writeInt(this.comments_received_count);
        dest.writeInt(this.followers_count);
        dest.writeInt(this.followings_count);
        dest.writeInt(this.likes_count);
        dest.writeInt(this.likes_received_count);
        dest.writeInt(this.members_count);
        dest.writeInt(this.projects_count);
        dest.writeInt(this.rebounds_received_count);
        dest.writeInt(this.shots_count);
        dest.writeByte(this.can_upload_shot ? (byte) 1 : (byte) 0);
        dest.writeString(this.type);
        dest.writeByte(this.pro ? (byte) 1 : (byte) 0);
        dest.writeString(this.buckets_url);
        dest.writeString(this.followers_url);
        dest.writeString(this.following_url);
        dest.writeString(this.likes_url);
        dest.writeString(this.members_url);
        dest.writeString(this.shots_url);
        dest.writeString(this.team_shots_url);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
    }

    public Team() {
    }

    protected Team(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.username = in.readString();
        this.html_url = in.readString();
        this.avatar_url = in.readString();
        this.bio = in.readString();
        this.location = in.readString();
        this.links = in.readParcelable(Links.class.getClassLoader());
        this.buckets_count = in.readInt();
        this.comments_received_count = in.readInt();
        this.followers_count = in.readInt();
        this.followings_count = in.readInt();
        this.likes_count = in.readInt();
        this.likes_received_count = in.readInt();
        this.members_count = in.readInt();
        this.projects_count = in.readInt();
        this.rebounds_received_count = in.readInt();
        this.shots_count = in.readInt();
        this.can_upload_shot = in.readByte() != 0;
        this.type = in.readString();
        this.pro = in.readByte() != 0;
        this.buckets_url = in.readString();
        this.followers_url = in.readString();
        this.following_url = in.readString();
        this.likes_url = in.readString();
        this.members_url = in.readString();
        this.shots_url = in.readString();
        this.team_shots_url = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
    }

    public static final Parcelable.Creator<Team> CREATOR = new Parcelable.Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel source) {
            return new Team(source);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
