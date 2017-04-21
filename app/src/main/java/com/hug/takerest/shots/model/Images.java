package com.hug.takerest.shots.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HStan on 2017/4/6.
 */

public class Images implements Parcelable {

    /**
     * hidpi : https://cdn.dribbble.com/users/51610/screenshots/3414303/cards_1.jpg
     * normal : https://cdn.dribbble.com/users/51610/screenshots/3414303/cards_1_1x.jpg
     * teaser : https://cdn.dribbble.com/users/51610/screenshots/3414303/cards_1_teaser.jpg
     */

    private String hidpi;
    private String normal;
    private String teaser;

    public String getHidpi() {
        return hidpi;
    }

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.hidpi);
        dest.writeString(this.normal);
        dest.writeString(this.teaser);
    }

    public Images() {
    }

    protected Images(Parcel in) {
        this.hidpi = in.readString();
        this.normal = in.readString();
        this.teaser = in.readString();
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
