package com.lawerance.scvision.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class gallery implements Parcelable {

    private  String type;
    private String time;
    private  String imageUrl;
    private  String valid;
    public gallery() {

    }

    public gallery(String type, String date, String url, String valid) {
        this.type = type;
        this.time = date;
        this.imageUrl = url;
        this.valid=valid;

    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getValid() {
        return valid;
    }

    public String getType() {
        return type;
    }


    public String getTime() {
        return time;
    }

    public  String getImageUrl(){return this.imageUrl;}

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected gallery(Parcel in) {
        type = in.readString();
        time = in.readString();
        imageUrl = in.readString();
        valid = in.readString();

    }

    public static final Creator<gallery> CREATOR = new Creator<gallery>() {
        @Override
        public gallery createFromParcel(Parcel in) {
            return new gallery(in);
        }

        @Override
        public gallery[] newArray(int size) {
            return new gallery[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(type);
        parcel.writeString(time);
        parcel.writeString(imageUrl);
        parcel.writeString(valid);

    }
}


