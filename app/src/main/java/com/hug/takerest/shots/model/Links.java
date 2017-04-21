package com.hug.takerest.shots.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HStan on 2017/4/11.
 */

public class Links implements Parcelable {
    /**
     * web : http://www.kabalka.com
     * twitter : https://twitter.com/JordosBeard
     */

    private String web;
    private String twitter;

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.web);
        dest.writeString(this.twitter);
    }

    public Links() {
    }

    protected Links(Parcel in) {
        this.web = in.readString();
        this.twitter = in.readString();
    }

    public static final Parcelable.Creator<Links> CREATOR = new Parcelable.Creator<Links>() {
        @Override
        public Links createFromParcel(Parcel source) {
            return new Links(source);
        }

        @Override
        public Links[] newArray(int size) {
            return new Links[size];
        }
    };
}
