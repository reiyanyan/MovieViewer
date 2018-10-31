package com.reiyan.movie.data;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieData implements Parcelable {

    String id, title, thumbnail, description, popularity, releaseDate, rate;

    public MovieData(){}

    public MovieData(String id, String title, String thumbnail, String description, String popularity, String releaseDate, String rate) {
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.description = description;
        this.popularity = popularity;
        this.releaseDate = releaseDate;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.thumbnail);
        dest.writeString(this.description);
        dest.writeString(this.popularity);
        dest.writeString(this.releaseDate);
        dest.writeString(this.rate);
    }

    protected MovieData(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.thumbnail = in.readString();
        this.description = in.readString();
        this.popularity = in.readString();
        this.releaseDate = in.readString();
        this.rate = in.readString();
    }

    public static final Parcelable.Creator<MovieData> CREATOR = new Parcelable.Creator<MovieData>() {
        @Override
        public MovieData createFromParcel(Parcel source) {
            return new MovieData(source);
        }

        @Override
        public MovieData[] newArray(int size) {
            return new MovieData[size];
        }
    };
}
