package com.hug.takerest.shots.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by HStan on 2017/4/6.
 */

public class Shot implements Parcelable {

    /**
     * id : 3414303
     * title : New Logo = New Cards
     * description : <p>Find me at Creative South so I can give all these freakin' thangs away :)</p>
     * width : 400
     * height : 300
     * images : {"hidpi":"https://cdn.dribbble.com/users/51610/screenshots/3414303/cards_1.jpg","normal":"https://cdn.dribbble.com/users/51610/screenshots/3414303/cards_1_1x.jpg","teaser":"https://cdn.dribbble.com/users/51610/screenshots/3414303/cards_1_teaser.jpg"}
     * views_count : 1914
     * likes_count : 157
     * comments_count : 13
     * attachments_count : 1
     * rebounds_count : 0
     * buckets_count : 4
     * created_at : 2017-04-05T18:30:05Z
     * updated_at : 2017-04-05T18:34:04Z
     * html_url : https://dribbble.com/shots/3414303-New-Logo-New-Cards
     * attachments_url : https://api.dribbble.com/v1/shots/3414303/attachments
     * buckets_url : https://api.dribbble.com/v1/shots/3414303/buckets
     * comments_url : https://api.dribbble.com/v1/shots/3414303/comments
     * likes_url : https://api.dribbble.com/v1/shots/3414303/likes
     * projects_url : https://api.dribbble.com/v1/shots/3414303/projects
     * rebounds_url : https://api.dribbble.com/v1/shots/3414303/rebounds
     * animated : false
     * tags : ["business cards","drawing","illustration","logo"]
     * user : {"id":51610,"name":"Jordan Kabalka","username":"JordanKabalka","html_url":"https://dribbble.com/JordanKabalka","avatar_url":"https://cdn.dribbble.com/users/51610/avatars/normal/a994d45489fd4c8396ab765badb07efc.jpg?1462293503","bio":"Graphic designer from Michigan. Currently working in the deep south. ","location":"Mobile, Alabama","links":{"web":"http://www.kabalka.com","twitter":"https://twitter.com/JordosBeard"},"buckets_count":0,"comments_received_count":51,"followers_count":569,"followings_count":847,"likes_count":5846,"likes_received_count":2542,"projects_count":4,"rebounds_received_count":2,"shots_count":49,"teams_count":0,"can_upload_shot":true,"type":"Player","pro":true,"buckets_url":"https://api.dribbble.com/v1/users/51610/buckets","followers_url":"https://api.dribbble.com/v1/users/51610/followers","following_url":"https://api.dribbble.com/v1/users/51610/following","likes_url":"https://api.dribbble.com/v1/users/51610/likes","projects_url":"https://api.dribbble.com/v1/users/51610/projects","shots_url":"https://api.dribbble.com/v1/users/51610/shots","teams_url":"https://api.dribbble.com/v1/users/51610/teams","created_at":"2011-08-09T06:25:26Z","updated_at":"2017-04-06T07:23:46Z"}
     * team : null
     */

    private int id;
    private String title;
    private String description;
    private int width;
    private int height;
    private Images images;
    private int views_count;
    private int likes_count;
    private int comments_count;
    private int attachments_count;
    private int rebounds_count;
    private int buckets_count;
    private String created_at;
    private String updated_at;
    private String html_url;
    private String attachments_url;
    private String buckets_url;
    private String comments_url;
    private String likes_url;
    private String projects_url;
    private String rebounds_url;
    private boolean animated;
    private User user;
    private @Nullable Team team;
    private List<String> tags;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttachments_count() {
        return attachments_count;
    }

    public void setAttachments_count(int attachments_count) {
        this.attachments_count = attachments_count;
    }

    public int getRebounds_count() {
        return rebounds_count;
    }

    public void setRebounds_count(int rebounds_count) {
        this.rebounds_count = rebounds_count;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
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

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAttachments_url() {
        return attachments_url;
    }

    public void setAttachments_url(String attachments_url) {
        this.attachments_url = attachments_url;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public void setProjects_url(String projects_url) {
        this.projects_url = projects_url;
    }

    public String getRebounds_url() {
        return rebounds_url;
    }

    public void setRebounds_url(String rebounds_url) {
        this.rebounds_url = rebounds_url;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeParcelable(this.images, flags);
        dest.writeInt(this.views_count);
        dest.writeInt(this.likes_count);
        dest.writeInt(this.comments_count);
        dest.writeInt(this.attachments_count);
        dest.writeInt(this.rebounds_count);
        dest.writeInt(this.buckets_count);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.html_url);
        dest.writeString(this.attachments_url);
        dest.writeString(this.buckets_url);
        dest.writeString(this.comments_url);
        dest.writeString(this.likes_url);
        dest.writeString(this.projects_url);
        dest.writeString(this.rebounds_url);
        dest.writeByte(this.animated ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.team, flags);
        dest.writeStringList(this.tags);
    }

    public Shot() {
    }

    protected Shot(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.description = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.images = in.readParcelable(Images.class.getClassLoader());
        this.views_count = in.readInt();
        this.likes_count = in.readInt();
        this.comments_count = in.readInt();
        this.attachments_count = in.readInt();
        this.rebounds_count = in.readInt();
        this.buckets_count = in.readInt();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.html_url = in.readString();
        this.attachments_url = in.readString();
        this.buckets_url = in.readString();
        this.comments_url = in.readString();
        this.likes_url = in.readString();
        this.projects_url = in.readString();
        this.rebounds_url = in.readString();
        this.animated = in.readByte() != 0;
        this.user = in.readParcelable(User.class.getClassLoader());
        this.team = in.readParcelable(Team.class.getClassLoader());
        this.tags = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Shot> CREATOR = new Parcelable.Creator<Shot>() {
        @Override
        public Shot createFromParcel(Parcel source) {
            return new Shot(source);
        }

        @Override
        public Shot[] newArray(int size) {
            return new Shot[size];
        }
    };
}
