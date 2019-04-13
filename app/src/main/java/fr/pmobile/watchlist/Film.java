package fr.pmobile.watchlist;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Film implements Parcelable {
    @SerializedName("imageFile")
    private String mImageFile;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("info")
    private String mInfo;
    @SerializedName("releaseDate")
    private String mReleaseDate;
    @SerializedName("genre")
    private String mGenre;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("score")
    private String mScore;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("trailer")
    private String mTrailer;
    @SerializedName("linkdata")
    private String mLink;
    @SerializedName("bookmarked")
    public String mBookmarked="false";

    public Film(){

    }

    public Film(String mImageFile, String mTitle, String mInfo, String mReleaseDate, String mGenre, String mStatus, String mScore, String mDuration, String mTrailer, String mLink) {
        this.mImageFile = mImageFile;
        this.mTitle = mTitle;
        this.mInfo = mInfo;
        this.mReleaseDate = mReleaseDate;
        this.mGenre = mGenre;
        this.mStatus = mStatus;
        this.mScore = mScore;
        this.mDuration = mDuration;
        this.mTrailer = mTrailer;
        this.mLink = mLink;
    }

    public String getmImageFile() {
        return mImageFile;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmInfo() {
        return mInfo;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmGenre() {
        return mGenre;
    }

    public String getmStatus() {
        return mStatus;
    }

    public String getmScore() {
        return mScore;
    }

    public String getmDuration() {
        return mDuration;
    }

    public String getmTrailer() {
        return mTrailer;
    }

    public String getmLink() {
        return mLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mImageFile);
        dest.writeString(this.mTitle);
        dest.writeString(this.mInfo);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mGenre);
        dest.writeString(this.mStatus);
        dest.writeString(this.mScore);
        dest.writeString(this.mDuration);
        dest.writeString(this.mTrailer);
        dest.writeString(this.mLink);
        dest.writeString(this.mBookmarked);
    }

    protected Film(Parcel in){
        this.mImageFile = in.readString();
        this.mTitle = in.readString();
        this.mInfo = in.readString();
        this.mReleaseDate = in.readString();
        this.mGenre = in.readString();
        this.mStatus = in.readString();
        this.mScore= in.readString();
        this.mDuration = in.readString();
        this.mTrailer = in.readString();
        this.mLink = in.readString();
        this.mBookmarked = in.readString();
    }

    public static final Parcelable.Creator<Film> CREATOR = new Parcelable.Creator<Film>(){
        @Override
        public Film createFromParcel(Parcel source) {
            return new Film(source);
        }

        @Override
        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

}
